package org.example.benchmark;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ResultWriter {

    public static void write(String fileName, long duration) {
        String path = String.format("src/test/resources/results/%s.csv", fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.append(String.format(",%s", duration));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
