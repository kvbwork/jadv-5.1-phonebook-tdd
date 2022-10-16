package kvbdev;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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

}
