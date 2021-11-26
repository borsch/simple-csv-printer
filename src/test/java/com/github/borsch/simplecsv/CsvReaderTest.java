package com.github.borsch.simplecsv;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class CsvReaderTest {

    private final CsvReader<User.Builder, User> USER_READER = new CsvReader<>(
        User::builder,
        User.Builder::build,
        Arrays.asList(
            CsvReadColumn.setter("First Name", User.Builder::firstName),
            CsvReadColumn.setter("Last Name", User.Builder::lastName),
            CsvReadColumn.setter("Age", User.Builder::age, Integer::parseInt),
            CsvReadColumn.setter("Weight", User.Builder::weight, Float::parseFloat),
            CsvReadColumn.setter("Last log-in time", User.Builder::lastLoginTime, value -> value.equals("-") ? null : LocalDateTime.parse(value))
        ));

    @Test
    void shouldParseCsv() throws IOException {
        try (final Reader reader = new InputStreamReader(CsvReaderTest.class.getResourceAsStream("/CsvReaderTest/test.csv"))) {
            assertThat(USER_READER.read(reader))
                .containsExactly(
                    new User("John", "Smith", 31, 82.1f, LocalDateTime.of(2030, 4, 5, 11, 2, 1)),
                    new User("Billy", "Smith", 43, 76f, null)
                );
        }
    }

}
