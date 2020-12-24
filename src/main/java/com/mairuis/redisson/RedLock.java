package com.mairuis.redisson;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * https://github.com/redisson/redisson/wiki/8.-%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81%E5%92%8C%E5%90%8C%E6%AD%A5%E5%99%A8
 * @author Mairuis
 * @since 2020/12/24
 */
public class RedLock {

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setConnectionPoolSize(10)
                .setTcpNoDelay(true)
                .setKeepAlive(true);
        RedissonClient redisson = Redisson.create(config);
        RLock test = redisson.getLock("testLock");
    }

}
