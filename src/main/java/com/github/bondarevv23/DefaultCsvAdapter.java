package com.github.bondarevv23;

import com.github.bondarevv23.annotation.ColumnName;
import com.github.bondarevv23.annotation.DefaultValue;
import com.github.bondarevv23.exception.CsvNullValueFieldException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

public class DefaultCsvAdapter<T> implements CsvAdapter<T> {
    private final Class<T> typeToken;

    DefaultCsvAdapter(Class<T> typeToken) {
        this.typeToken = typeToken;
    }

    @Override
    public void writeRow(T obj, CsvAdapterWriter writer) throws IOException {
        writeSeparatedByDelimiter(
                writer,
                Arrays.stream(typeToken.getDeclaredFields()).map(field -> fieldToString(field, obj)).toList()
        );
    }

    @Override
    public void writeHeader(CsvAdapterWriter writer) throws IOException {
        writeSeparatedByDelimiter(
                writer,
                Arrays.stream(typeToken.getDeclaredFields()).map(this::getFieldName).toList()
        );
    }

    private void writeSeparatedByDelimiter(CsvAdapterWriter writer, Collection<String> strings) throws IOException {
        Iterator<String> it = strings.iterator();
        if (!it.hasNext()) {
            return;
        }
        writer.write(it.next());
        while(it.hasNext()) {
            writer.delimiter();
            writer.write(it.next());
        }
    }

    private String getFieldName(Field field) {
        ColumnName columnName = field.getAnnotation(ColumnName.class);
        return Optional.ofNullable(columnName).map(ColumnName::value).orElseGet(field::getName);
    }

    private String fieldToString(Field field, T obj) {
        field.setAccessible(true);
        try {
            return field.get(obj).toString();
        } catch (NullPointerException exception) {
            DefaultValue defaultValue = field.getAnnotation(DefaultValue.class);
            return Optional.ofNullable(defaultValue).map(DefaultValue::value).orElse(null);
        } catch (IllegalAccessException exception) {
            throw new RuntimeException("inappropriate types", exception);
        }
    }
}
