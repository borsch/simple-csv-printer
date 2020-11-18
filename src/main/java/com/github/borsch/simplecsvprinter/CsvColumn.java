package com.github.borsch.simplecsvprinter;

import java.util.function.Function;

public class CsvColumn<T> {

    private final String columnHeader;
    private final Function<T, String> extractor;

    public CsvColumn(final String columnHeader, final Function<T, String> extractor) {
        this.columnHeader = columnHeader;
        this.extractor = extractor;
    }

    public Function<T, String> getExtractor() {
        return extractor;
    }

    public String getColumnHeader() {
        return columnHeader;
    }

    @Override
    public String toString() {
        return "CsvColumn[" + columnHeader + "]";
    }
}

