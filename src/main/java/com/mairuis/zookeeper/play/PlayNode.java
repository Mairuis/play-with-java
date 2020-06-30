package com.mairuis.zookeeper.play;

import lombok.extern.java.Log;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Mairuis
 * @since 2020/6/30
 */
@Log
public class PlayNode {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayNode.class);

    public static void main(String[] args) throws Exception {
        TestingServer testingServer = new TestingServer(2181, true);
        final String connectString = testingServer.getConnectString();
        final CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory
                .builder()
                .connectString(connectString)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3));
        new PlaySequenceNode("/root", "/node", builder.build(), builder.build()).play();
    }
}
