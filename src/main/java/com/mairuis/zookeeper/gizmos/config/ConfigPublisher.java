package com.mairuis.zookeeper.gizmos.config;

import lombok.Getter;
import org.apache.curator.framework.CuratorFramework;

/**
 * @author Mairuis
 * @since 2020/6/27
 */
@Getter
public class ConfigPublisher {
    private final String path;
    private final CuratorFramework client;

    public ConfigPublisher(String path, CuratorFramework client) {
        this.path = path;
        this.client = client;
    }

    public void start() {
        this.getClient().start();
    }

    public void publish(ConfigData configData) throws Exception {
        CuratorFramework client = this.getClient();
        if (client.checkExists().forPath(getPath()) == null) {
            client.create().creatingParentContainersIfNeeded().forPath(path, configData.serialize());
        } else {
            client.setData().forPath(path, configData.serialize());
        }
    }

}
