package com.mairuis.zookeeper.gizmos.config;

import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;

/**
 * @author Mairuis
 * @since 2020/6/27
 */
public class PlayConfig {

    public static void main(String[] args) throws Exception {
        final String path = "/config";
        final TestingServer testingServer = new TestingServer(2181, true);
        final CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory
                .builder()
                .connectString(testingServer.getConnectString())
                .retryPolicy(new ExponentialBackoffRetry(1000, 3));
        final ConfigPublisher configPublisher = new ConfigPublisher(path, builder.build());
        configPublisher.start();
        configPublisher.publish(new ConfigData(233, "super"));
        new Client(path, builder.build()).start();
        new Client(path, builder.build()).start();
        new Client(path, builder.build()).start();

        configPublisher.publish(new ConfigData(666, "surprise"));
    }

}
