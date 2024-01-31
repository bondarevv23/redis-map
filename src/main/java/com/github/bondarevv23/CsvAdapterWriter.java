package com.github.bondarevv23;

import java.io.IOException;

public interface CsvAdapterWriter {
    void write(String string) throws IOException;

    void delimiter() throws IOException;
}
