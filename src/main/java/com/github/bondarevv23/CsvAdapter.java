package com.github.bondarevv23;

import java.io.IOException;

public interface CsvAdapter<T> {
    void writeRow(T obj, CsvAdapterWriter writer) throws IOException;

    default void writeHeader(CsvAdapterWriter writer) throws IOException { }
}
