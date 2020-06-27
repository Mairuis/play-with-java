package com.mairuis.zookeeper.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mairuis
 * @since 2020/6/27
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@Log
public class Client {
    private volatile ConfigData configData;

    private final String path;
    private final CuratorFramework client;
    private final CuratorWatcher updateWatcher = event -> {
        if (event.getType() == Watcher.Event.EventType.NodeDataChanged) {
            final byte[] bytes = getClient().getData().usingWatcher(getUpdateWatcher()).forPath(event.getPath());
            final ConfigData old = getConfigData();
            setConfigData(ConfigData.deserialize(bytes));
            LOGGER.info("发生更新事件 " + old + " -> " + configData);
        }
    };

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayConfig.class);


    public Client(String path, CuratorFramework client) {
        this.path = path;
        this.client = client;
    }

    public void start() throws Exception {
        this.getClient().start();

        final byte[] bytes = getClient().getData().usingWatcher(getUpdateWatcher()).forPath(getPath());
        this.setConfigData(ConfigData.deserialize(bytes));
        LOGGER.info("初始化配置 " + configData);
    }
}