package com.github.borsch.simplecsv;

import java.util.function.Function;

public class CsvPrintColumn<T> {

    private final String columnHeader;
    private final Function<T, String> extractor;

    private CsvPrintColumn(final String columnHeader, final Function<T, String> extractor) {
        this.columnHeader = columnHeader;
        this.extractor = extractor;
    }

    public static <T> CsvPrintColumn<T> column(final String columnHeader, final Function<T, String> extractor) {
        return new CsvPrintColumn<>(columnHeader, extractor);
    }

    public static <T, K> CsvPrintColumn<T> column(final String columnHeader, final Function<T, K> extractor, final Function<K, String> formatter) {
        return column(columnHeader, extractor.andThen(formatter));
    }

    public Function<T, String> getExtractor() {
        return extractor;
    }

    public String getColumnHeader() {
        return columnHeader;
    }

    @Override
    public String toString() {
        return "CsvPrintColumn[" + columnHeader + "]";
    }
}

