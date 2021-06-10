package com.mairuis.jvm.array;

import com.mairuis.jvm.util.Utils;
import sun.misc.Unsafe;

/**
 * @author Mairuis
 * @since 2021/6/9
 */
public class UnsafeAccessArray {

    public static class U {
        private static final int BASE_OFFSET;
        private static final int SCALE;
        private static final Unsafe UNSAFE = Utils.getUnsafe();

        private final Object[] array;

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

        public U(int size) {
            this.array = new Object[size];
            for (int i = 0; i < array.length; i++) {
                array[i] = String.valueOf(i);
            }
        }

        public Object elementAt(int index) {
            return UNSAFE.getObject(array, BASE_OFFSET + (index << REF_ELEMENT_SHIFT));
        }
    }

    public static void main(String[] args) {
        U u = new U(16);

        long marked = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            for (int j = 0; j < u.array.length; j++) {
                u.elementAt(j);
            }
        }
        System.out.println(System.currentTimeMillis() - marked + "ms");
    }
}
