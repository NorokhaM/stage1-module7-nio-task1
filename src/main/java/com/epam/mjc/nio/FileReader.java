package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {
    public Profile getDataFromFile(File file) {
        try (RandomAccessFile accessFile = new RandomAccessFile(file, "r");
             FileChannel inChannel = accessFile.getChannel()) {
            String[] lines = new String[4];
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (inChannel.read(buffer) != -1) {
                buffer.flip();
                for (int i = 0; i < 4; i++) {
                    char ch;
                    while ((ch = (char) buffer.get()) != '\n') {
                        lines[i] += ch;
                    }
                }
                buffer.clear();
            }
            String regex = ": ";
            String name = lines[0].trim().split(regex)[1];
            Integer age = Integer.parseInt(lines[1].trim().split(regex)[1]);
            String email = lines[2].trim().split(regex)[1];
            Long phoneNumber = Long.parseLong(lines[3].trim().split(regex)[1]);

            return new Profile(name, age, email, phoneNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Profile();
    }
}
