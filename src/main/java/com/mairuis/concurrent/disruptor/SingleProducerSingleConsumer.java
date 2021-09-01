package com.mairuis.concurrent.disruptor;

import com.mairuis.jvm.util.Utils;
import sun.misc.Unsafe;

import java.sql.PreparedStatement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * 简易版吗，仅供学习参考
 *
 * @author Mairuis
 * @since 2021/6/16
 */
public class SingleProducerSingleConsumer {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);


    public static class RingBuffer<T> {
        private static final int BASE_OFFSET;
        private static final int SCALE;
        private static final Unsafe UNSAFE = Utils.getUnsafe();
        private static final int REF_ELEMENT_SHIFT;

        static {
            BASE_OFFSET = UNSAFE.arrayBaseOffset(Object[].class);
            SCALE = UNSAFE.arrayIndexScale(Object[].class);
            if (4 == SCALE) {
                REF_ELEMENT_SHIFT = 2;
            } else if (8 == SCALE) {
                REF_ELEMENT_SHIFT = 3;
            } else {
                throw new IllegalStateException("Unknown pointer size");
            }
        }

        private final Object[] buffer;
        private final int indexMask;
        private final Sequence sequence = new Sequence();

        public RingBuffer(int capacity, Supplier<T> slotFactory) {
            this.buffer = new Object[capacity];
            this.indexMask = capacity - 1;
            for (int i = 0; i < capacity; i++) {
                this.buffer[i] = slotFactory.get();
            }
        }

        public T get(int index) {
            return (T) UNSAFE.getObject(buffer, BASE_OFFSET + ((index & indexMask) << REF_ELEMENT_SHIFT));
        }
    }

    public static class Consumer {


    }

    public static class DataPointer {
        public String data;
    }

    public static void main(String[] args) {
        final RingBuffer<DataPointer> ringBuffer = new RingBuffer<>(4, DataPointer::new);
        for (int i = 0; i < 32; i++) {
            final DataPointer dataPointer = ringBuffer.get(i);
            dataPointer.data = String.valueOf(i);
        }
    }

}
