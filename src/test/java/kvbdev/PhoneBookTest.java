package kvbdev;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.PrintStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

public class PhoneBookTest {
    private PhoneBook sut;

    @BeforeEach
    void setUp() {
        sut = new PhoneBook();
    }

    @AfterEach
    void tearDown() {
        sut = null;
    }

    @Test
    void add_success() {
        sut.add("USER1", "111");
        sut.add("USER2", "222");
        int contactsCount = sut.add("USER3", "333");
        int expectedCount = 3;
        assertThat(contactsCount, is(expectedCount));
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    void add_empty_name_failure(String name) {
        String phone = "111";
        int expectedCount = 0;
        int contactsCount = sut.add(name, phone);
        assertThat(contactsCount, is(expectedCount));
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    void add_empty_phone_failure(String phone) {
        String name = "USER1";
        int expectedCount = 0;
        int contactsCount = sut.add(name, phone);
        assertThat(contactsCount, is(expectedCount));
    }

    @Test
    void add_skip_double_success() {
        sut.add("USER1", "111");
        sut.add("USER1", "111");
        int contactsCount = sut.add("USER1", "222");
        int expectedCount = 1;
        assertThat(contactsCount, is(expectedCount));
    }

    @Test
    void findByNumber_success() {
        String name = "USER1";
        String phone = "111";
        sut.add(name, phone);
        assertThat(sut.findByNumber(phone), equalTo(name));
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    @ValueSource(strings = {"111"})
    void findByNumber_failure(String phone) {
        String expectedName = "";
        assertThat(sut.findByNumber(phone), equalTo(expectedName));
    }

    @Test
    void findByName_success() {
        String name = "USER1";
        String phone = "111";
        sut.add(name, phone);
        assertThat(sut.findByName(name), equalTo(phone));
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    @ValueSource(strings = {"USER1"})
    void findByName_failure(String name) {
        String expectedPhoneNumber = "";
        assertThat(sut.findByName(name), equalTo(expectedPhoneNumber));
    }

    @Test
    void printAllNames_anyOrder_success() {
        List<String> names = List.of("EEE", "DDD", "CCC", "BBB", "AAA");
        names.forEach(name -> sut.add(name, "111"));
        final int namesCount = 5;

        PrintStream systemOut = System.out;
        PrintStream outMock = Mockito.spy(System.out);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);

        System.setOut(outMock);
        try {
            sut.printAllNames();
        } finally {
            System.setOut(systemOut);
        }

        Mockito.verify(outMock, times(namesCount)).println(stringCaptor.capture());
        assertThat(stringCaptor.getAllValues(), containsInAnyOrder(names.toArray(String[]::new)));
    }

    @Test
    void printAllNames_sorted_success() {
        String[] expectedNames = {"AAA", "BBB", "CCC", "DDD", "EEE"};
        final int namesCount = 5;

        List<String> names = List.of("EEE", "DDD", "CCC", "BBB", "AAA");
        names.forEach(name -> sut.add(name, "111"));

        PrintStream systemOut = System.out;
        PrintStream outMock = Mockito.spy(System.out);
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);

        System.setOut(outMock);
        try {
            sut.printAllNames();
        } finally {
            System.setOut(systemOut);
        }

        Mockito.verify(outMock, times(namesCount)).println(stringCaptor.capture());
        assertThat(stringCaptor.getAllValues(), contains(expectedNames));
    }

    @Test
    void printAllNames_empty_success() {
        PrintStream systemOut = System.out;
        PrintStream outMock = Mockito.spy(System.out);

        System.setOut(outMock);
        try {
            sut.printAllNames();
        } finally {
            System.setOut(systemOut);
        }

        Mockito.verify(outMock, never()).print(anyString());
    }
}
