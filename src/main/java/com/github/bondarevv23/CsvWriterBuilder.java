package com.github.bondarevv23;

public interface CsvWriterBuilder {
    CsvWriterBuilder useHeader(boolean useHeader);

    CsvWriterBuilder strict(boolean strict);

    CsvWriterBuilder setDelimiter(String delimiter);

    CsvWriterBuilder setLineSeparator(String lineSeparator);

    <T> CsvWriterBuilder addCsvAdapter(Class<T> typeToken, CsvAdapter<T> typeAdapter);

    CsvWriter build();
}
