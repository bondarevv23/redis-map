package com.github.bondarevv23;

import com.github.bondarevv23.exception.CsvIOException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public interface CsvWriter {
    void write(List<Object> list, Writer writer) throws IOException;

    default void writeToFile(List<Object> list, Path path) {
        writeToFile(list, path.toFile());
    }

    default void writeToFile(List<Object> list, File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            write(list, bw);
        } catch (IOException exception) {
            throw new CsvIOException(exception);
        }
    }

    default void writeToFile(List<Object> list, String fileName) {
        writeToFile(list, Path.of(fileName));
    }

    public static CsvWriterBuilder builder() {
        return new CsvWriterBuilderImpl();
    }
}
