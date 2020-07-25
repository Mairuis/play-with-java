package com.mairuis.zookeeper.gizmos.play;

import lombok.Getter;
import lombok.extern.java.Log;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Mairuis
 * @since 2020/7/1
 */
@Log
@Getter
public class PlaySequenceNode {
    private final String container;
    private final String node;
    private final CuratorFramework client;
    private final CuratorFramework probe;
    private final CuratorWatcher watcher = event -> {
        synchronized (this.getClient()) {
            if (this.getClient().getState() == CuratorFrameworkState.STARTED && event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                final List<String> children = this.getClient().getChildren().usingWatcher(getWatcher()).forPath(getContainer());

                LOGGER.info("新数据 {}", children);
            }
        }
    };
    private static final Logger LOGGER = LoggerFactory.getLogger(PlaySequenceNode.class);

    public PlaySequenceNode(String container, String node, CuratorFramework client, CuratorFramework probe) {
        this.container = container;
        this.node = node;
        this.client = client;
        this.probe = probe;
    }

    public void createNode() throws Exception {
        final String node = this.getClient().create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(getContainer() + getNode());
        LOGGER.info("增加节点 {}", node);
    }

    public void play() throws Exception {
        this.getClient().start();
        this.getClient().createContainers(getContainer());
        final List<String> children = this.getClient().getChildren().usingWatcher(getWatcher()).forPath(getContainer());
        LOGGER.info("初始化 {}", children);

        this.createNode();
        this.createNode();
        this.createNode();
        synchronized (this.getClient()) {
            this.getClient().close();
        }

        this.getProbe().start();
        final List<String> children2 = this.getProbe().getChildren().usingWatcher(getWatcher()).forPath(getContainer());
        LOGGER.info("重启后 {}", children2);
        this.getProbe().close();
    }
}
