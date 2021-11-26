package com.github.borsch.simplecsv;

import java.util.function.BiConsumer;
import java.util.function.Function;

import org.apache.commons.csv.CSVRecord;

public class CsvReadColumn<T> {

    /**
     * Function that will be used to assign column value to appropriate
     * object field using object method
     */
    private final BiConsumer<T, String> setter;
    /**
     * Internal function that will be used to get value from CSV record by column name or by column index
     */
    private final Function<CSVRecord, String> columnValueSelector;

    private CsvReadColumn(final BiConsumer<T, String> setter, final Function<CSVRecord, String> columnValueSelector) {
        this.setter = setter;
        this.columnValueSelector = columnValueSelector;
    }

    public static <T> CsvReadColumn<T> setter(final String columnHeader,final BiConsumer<T, String> setter) {
        return new CsvReadColumn<>(setter, record -> record.get(columnHeader));
    }

    public static <T, K> CsvReadColumn<T> setter(final String columnHeader, final BiConsumer<T, K> setter, final Function<String, K> parser) {
        return setter(columnHeader, (obj, value) -> {
            K parsedValue = parser.apply(value);
            setter.accept(obj, parsedValue);
        });
    }

    public static <T> CsvReadColumn<T> setter(final int columnIndex, final BiConsumer<T, String> setter) {
        return new CsvReadColumn<>(setter, record -> record.get(columnIndex));
    }

    public static <T, K> CsvReadColumn<T> setter(final int columnIndex, final BiConsumer<T, K> setter, final Function<String, K> parser) {
        return setter(columnIndex, (obj, value) -> {
            K parsedValue = parser.apply(value);
            setter.accept(obj, parsedValue);
        });
    }

    BiConsumer<T, String> getSetter() {
        return setter;
    }

    Function<CSVRecord, String> getColumnValueSelector() {
        return columnValueSelector;
    }
}

