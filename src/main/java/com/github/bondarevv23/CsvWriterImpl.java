package com.github.bondarevv23;


import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class CsvWriterImpl implements CsvWriter {
    final boolean header;
    final boolean strict;
    final String delimiter;
    final String lineSeparator;
    final Map<Class<?>, CsvAdapter<?>> adapters;

    CsvWriterImpl(
            boolean header,
            boolean strict,
            String delimiter,
            String lineSeparator,
            Map<Class<?>, CsvAdapter<?>> adapters) {
        this.header = header;
        this.strict = strict;
        this.delimiter = delimiter;
        this.lineSeparator = lineSeparator;
        this.adapters = adapters;
    }

    @Override
    public void write(List<Object> list, Writer writer) throws IOException {
        if (list.isEmpty()) {
            return;
        }
        writeImpl(list.get(0).getClass(), list, writer);
    }

    @SuppressWarnings("unchecked")
    private <T> void writeImpl(Class<T> typeToken, List<Object> list, Writer writer) throws IOException {
        CsvAdapter<T> csvAdapter = Optional.ofNullable((CsvAdapter<T>) adapters.get(typeToken))
                .orElseGet(() -> new DefaultCsvAdapter<>(typeToken));
        CsvAdapterWriter adapterWriter = new CsvAdapterWriterImpl(writer, delimiter, strict);
        if (header) {
            csvAdapter.writeHeader(adapterWriter);
            writer.write(lineSeparator);
        }
        for (Object obj : list) {
            csvAdapter.writeRow((T) obj, adapterWriter);
            writer.write(lineSeparator);
        }
    }
}
