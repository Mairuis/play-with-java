package com.mairuis;

import com.mairuis.algorithm.analysis.Watch;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 测试mmap
 * @author Mairuis
 * @since 2021/3/1
 */
public class DiskTest {

    public static void main(String[] args) throws IOException {
        FileChannel open = FileChannel.open(Paths.get("E:/test-file"),StandardOpenOption.READ,StandardOpenOption.WRITE);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024 * 1024);
        Watch watch = new Watch();
        watch.begin();
//        for (int i = 0; i < 1024; i++) {
//            open.write(byteBuffer);
//            open.force(false);
//        }
//        open.close();
        MappedByteBuffer map = open.map(FileChannel.MapMode.READ_WRITE, 0, Integer.MAX_VALUE);
        for (int i = 0; i < 1024; i++) {
            map.put(byteBuffer);
        }
        System.out.println(watch.end());
    }
}
