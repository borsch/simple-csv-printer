package com.github.borsch.simplecsv;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class CsvPrinterTest {

    private final CsvPrinter<User> USER_PRINTER = new CsvPrinter<>(Arrays.asList(
        CsvPrintColumn.column("First Name", User::getFirstName),
        CsvPrintColumn.column("Last Name", User::getLastName),
        CsvPrintColumn.column("Age", User::getAge, String::valueOf),
        CsvPrintColumn.column("Weight", user -> String.valueOf(user.getWeight())),
        CsvPrintColumn.column("Last log-in time", user -> user.getLastLoginTime() == null ? "-" : user.getLastLoginTime().toString())
    ));

    @Test
    void shouldGenerateCsv() {
        final List<User> users = Arrays.asList(
            new User("John", "Smith", 31, 82.1f, LocalDateTime.of(2030, 4, 5, 11, 2, 1)),
            new User("Billy", "Smith", 43, 76f, null)
        );

        assertThat(USER_PRINTER.print(users))
            .hasLineCount(3)
            .containsSubsequence(
                "First Name,Last Name,Age,Weight,Last log-in time",
                "John,Smith,31,82.1,2030-04-05T11:02:01",
                "Billy,Smith,43,76.0,-"
            );
    }

}
