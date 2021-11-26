package com.github.borsch.simplecsv;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CsvPrinter<T> {

    private final List<CsvPrintColumn<T>> columns;
    private final CSVFormat format;

    public CsvPrinter(final List<CsvPrintColumn<T>> columns) {
        this(
            columns,
            CSVFormat.DEFAULT
                .withQuote('"')
                .withRecordSeparator("\n")
        );
    }

    public CsvPrinter(final List<CsvPrintColumn<T>> columns, final CSVFormat format) {
        this.columns = columns;
        this.format = format;
    }

    public String print(final List<T> items) {
        final StringWriter writer = new StringWriter();

        try (final CSVPrinter printer = new CSVPrinter(writer, format)) {
            printer.printRecord(getHeaders());
            printContent(items, printer);

            return writer.toString();
        } catch (final IOException ex) {
            throw new RuntimeException("Can't print items to CSV", ex);
        }
    }

    private List<String> getHeaders() {
        return columns.stream()
            .map(CsvPrintColumn::getColumnHeader)
            .collect(Collectors.toList());
    }

    private void printContent(final List<T> items, final CSVPrinter printer) {
        try {
            for (final T item : items) {
                printer.printRecord(
                    columns.stream()
                        .map(column -> column.getExtractor().apply(item))
                        .collect(Collectors.toList())
                );
            }
        } catch (final Exception ex) {
            throw new RuntimeException("Failed to write record to CSV", ex);
        }
    }

}

