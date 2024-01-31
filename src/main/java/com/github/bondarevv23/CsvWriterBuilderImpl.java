package com.github.bondarevv23;

import java.util.HashMap;
import java.util.Map;

class CsvWriterBuilderImpl implements CsvWriterBuilder {
    private static final String DEFAULT_DELIMITER = ",";

    boolean header = false;
    boolean strict = false;
    String delimiter = DEFAULT_DELIMITER;
    String lineSeparator = System.lineSeparator();
    final Map<Class<?>, CsvAdapter<?>> adapters = new HashMap<>();

    @Override
    public CsvWriterBuilder useHeader(boolean useHeader) {
        this.header = useHeader;
        return this;
    }

    @Override
    public CsvWriterBuilder strict(boolean strict) {
        this.strict = strict;
        return this;
    }

    @Override
    public CsvWriterBuilder setDelimiter(String delimiter) {
        this.delimiter = delimiter;
        return this;
    }

    @Override
    public CsvWriterBuilder setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
        return this;
    }

    @Override
    public <T> CsvWriterBuilder addCsvAdapter(Class<T> typeToken, CsvAdapter<T> typeAdapter) {
        this.adapters.put(typeToken, typeAdapter);
        return this;
    }

    @Override
    public CsvWriter build() {
        return new CsvWriterImpl(header, strict, delimiter, lineSeparator, adapters);
    }
}
