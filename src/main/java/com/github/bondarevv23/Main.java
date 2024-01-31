package com.github.bondarevv23;

import com.github.bondarevv23.annotation.ColumnName;
import com.github.bondarevv23.annotation.DefaultValue;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class Main {
    static class A {
        @DefaultValue("default a")
        @ColumnName("AAA")
        private String a;
        private Integer b;

        public A(String a, Integer b) {
            this.a = a;
            this.b = b;
        }
    }

    public static void main(String[] args) throws IOException {
        StringWriter writer = new StringWriter();
        List<Object> list = List.of(new A(null, 1), new A(null, null));
        CsvWriter csv = CsvWriter.builder()
                .addCsvAdapter(A.class, (a, w) -> {
                    w.write(a.a);
                    w.delimiter();
                    w.write( "\"" + 1000 + ", !");
                })
                .build();
        csv.write(list, writer);
        System.out.print(writer);
    }
}
