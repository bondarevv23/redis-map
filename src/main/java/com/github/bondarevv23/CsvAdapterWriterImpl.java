package com.github.bondarevv23;

import com.github.bondarevv23.exception.CsvNullValueFieldException;

import java.io.IOException;
import java.io.Writer;

class CsvAdapterWriterImpl implements CsvAdapterWriter {
    private static final String EMPTY_FIELD = "NULL";
    private static final String QUOTATION = "\"";
    private static final String QUOTATIONS = "\"\"";

    private final Writer writer;
    private final String delimiter;
    private final boolean strict;

    CsvAdapterWriterImpl(Writer writer, String delimiter, boolean strict) {
        this.writer = writer;
        this.delimiter = delimiter;
        this.strict = strict;
    }

    @Override
    public void write(String string) throws IOException {
        writer.write(shield(string));
    }

    @Override
    public void delimiter() throws IOException {
        writer.write(delimiter);
    }

    private String shield(String string) {
        if (string != null) {
            String shieldedString = String.join(QUOTATIONS, string.split(QUOTATION));
            if (!string.contains(delimiter)) {
                return shieldedString;
            }
            return String.format("\"%s\"", shieldedString);
        }
        if (!strict) {
            return EMPTY_FIELD;
        }
        throw new CsvNullValueFieldException("Attempt to write a field with a null value in strict mode");
    }
}
