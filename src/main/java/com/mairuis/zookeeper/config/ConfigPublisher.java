package com.mairuis.zookeeper.config;

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
        if (this.getClient().checkExists().forPath(getPath()) == null) {
            this.getClient().create().creatingParentContainersIfNeeded().forPath(path, configData.serialize());
        } else {
            this.getClient().setData().forPath(path, configData.serialize());
        }
    }

}
