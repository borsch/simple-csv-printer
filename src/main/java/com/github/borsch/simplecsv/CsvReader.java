package com.github.borsch.simplecsv;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;

public class CsvReader<BUILDER, OBJECT> {

    private final List<CsvReadColumn<BUILDER>> columns;
    private final CSVFormat format;
    /**
     * This supplier is used to create new object for each CSV record
     */
    private final Supplier<BUILDER> newObjectSupplier;
    /**
     * This function is for builder patter support. If following class exists then builder can used in following way
     * This will give you support for immutable objects
     * <pre>
     *     class MyClass {
     *         ... fields ...
     *
     *         public static Builder builder() {
     *             return new Builder();
     *         }
     *
     *         static class Builder {
     *             .. builder methods ..
     *             public MyClass build() {
     *                 return new MyClass(this);
     *             }
     *         }
     *     }
     *
     *     <b>USAGE: </b>
     *     new CsvReader&lt;&gt;(..columns.., MyClass::builder, MyClass.Builder::build)
     * </pre>
     */
    private final Function<BUILDER, OBJECT> finisher;

    public CsvReader(
        final Supplier<BUILDER> newObjectSupplier,
        final Function<BUILDER, OBJECT> finisher,
        final List<CsvReadColumn<BUILDER>> columns
    ) {
        this(
            newObjectSupplier,
            finisher,
            columns,
            CSVFormat.DEFAULT
                .withQuote('"')
                .withHeader()
                .withRecordSeparator("\n")
        );
    }

    public CsvReader(
        final Supplier<BUILDER> newObjectSupplier,
        final Function<BUILDER, OBJECT> finisher,
        final List<CsvReadColumn<BUILDER>> columns,
        final CSVFormat format
    ) {
        this.columns = columns;
        this.newObjectSupplier = newObjectSupplier;
        this.finisher = finisher;
        this.format = format;
    }

    public Stream<OBJECT> read(final Reader reader) throws IOException {
        return Stream.of(format.parse(reader).getRecords())
            .flatMap(Collection::stream)
            .map(record -> {
                final BUILDER builder = newObjectSupplier.get();

                for(CsvReadColumn<BUILDER> csvColumn : columns) {
                    String value = csvColumn.getColumnValueSelector().apply(record);
                    csvColumn.getSetter().accept(builder, value);
                }

                return builder;
            })
            .map(finisher);
    }

}
