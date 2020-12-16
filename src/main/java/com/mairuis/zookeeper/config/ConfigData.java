package com.mairuis.zookeeper.config;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.DefaultIdStrategy;
import io.protostuff.runtime.IdStrategy;
import io.protostuff.runtime.RuntimeSchema;
import lombok.Value;

/**
 * @author Mairuis
 * @since 2020/6/27
 */
@Value
public class ConfigData {
    int port;
    String ip;

    private static final DefaultIdStrategy STRATEGY = new DefaultIdStrategy(
            IdStrategy.DEFAULT_FLAGS
                    | IdStrategy.MORPH_COLLECTION_INTERFACES
                    | IdStrategy.MORPH_MAP_INTERFACES
                    | IdStrategy.MORPH_NON_FINAL_POJOS);
    private static final Schema<ConfigData> SCHEMA = RuntimeSchema.getSchema(ConfigData.class, STRATEGY);


    public byte[] serialize() {
        return serialize(this);
    }

    public static byte[] serialize(ConfigData configData) {
        LinkedBuffer buffer = LinkedBuffer.allocate(512);
        try {
            return ProtostuffIOUtil.toByteArray(configData, SCHEMA, buffer);
        } finally {
            buffer.clear();
        }
    }

    public static ConfigData deserialize(byte[] bytes) {
        final ConfigData message = SCHEMA.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, message, SCHEMA);
        return message;
    }
}
