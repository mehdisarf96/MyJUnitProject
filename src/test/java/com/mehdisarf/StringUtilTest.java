package com.mehdisarf;

import com.mehdisarf.model.Employee;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static com.mehdisarf.StringUtil.isName;
import static com.mehdisarf.StringUtil.isNationalId;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilTest {

    @Test
    @Disabled
    void isName_WHEN_nameIsValid_THEN_returnsTrue() {
        assertTrue(isName("mehdi"));
        assertTrue(isName("mehdi-sarf"));
        assertTrue(isName("mehdi sarf"));
        assertTrue(isName("mehdi alireza-sarf"));
        assertTrue(isName("mehdi-alireza sarf"));
        assertTrue(isName("mehdi alireza sarf"));
    }

    @Test
    @Disabled
    void firstRefactoredIsName_WHEN_nameIsValid_THEN_returnsTrue() {
        List<String> parameters = Arrays.asList("mehdi", "mehdi-sarf", "mehdi sarf", "mehdi alireza-sarf", "mehdi-alireza sarf");
        for (String param : parameters)
            assertTrue(isName(param));
    }

    @Test
    @Disabled
    void secondRefactoredIsName_WHEN_nameIsValid_THEN_returnsTrue() {
        List<String> parameters = Arrays.asList("mehdi", "mehdi-sarf", "mehdi sarf", "mehdi alireza-sarf", "mehdi-alireza sarf");
        parameters.stream()
                .forEach(param -> assertTrue(isName(param)));
    }

    @Tag("mehdiTest")
    @ParameterizedTest
    @ValueSource(strings = {"mehdi", "mehdi-sarf", "mehdi sarf", "mehdi alireza-sarf", "mehdi-alireza sarf"})
    void parameterizedIsName_WHEN_nameIsValid_THEN_returnsTrue(String parameter) {
        isName(parameter);
    }

    @Test
    @Disabled
    void isName_WHEN_nameIsNotValid_THEN_returnsFalse() {
        assertFalse(isName(null));
        assertFalse(isName(""));
        assertFalse(isName("mehdi4"));
        assertFalse(isName("mehdi "));
        assertFalse(isName("-mehdi"));
        assertFalse(isName(" mehdi"));
        assertFalse(isName("Mehdi"));
        assertFalse(isName("mehdi  sa"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "mehdi4", "mehdi ", "-mehdi", " mehdi", "Mehdi", "mehdi  sa"})
    @NullSource
    void parameterizedIsName_WHEN_nameIsNotValid_THEN_returnsFalse(String parameter) {
        assertFalse(isName(parameter));
    }

    @ParameterizedTest
    @ValueSource(strings = {"mehdi4", "mehdi ", "-mehdi", " mehdi", "Mehdi", "mehdi  sa"})
    @NullSource
    @EmptySource
    void secondParameterizedIsName_WHEN_nameIsNotValid_THEN_returnsFalse(String parameter) {
        assertFalse(isName(parameter));
    }

    @Test
    @Disabled
    void isNationalId_WHEN_nationalIdIsValid_THEN_returnTrue() {
        assertTrue(isNationalId("5300020788"));
        assertTrue(isNationalId("0520258649")); // Unique Test Point e abc=001 ro test mikone.
        assertTrue(isNationalId("0017440701")); // Unique Test Point e abc=110 ro test mikone.
        assertTrue(isNationalId("0010010041")); // Unique Test Point e abc=110 ro test mikone.
    }

    @ParameterizedTest
    @ValueSource(strings = {"5300020788", "0520258649", "0017440701", "0010010041"})
    void parameterizedIsNationalId_WHEN_nationalIdIsValid_THEN_returnTrue(String parameter) {
        assertTrue(isNationalId(parameter));
    }

    @Test
    @Disabled
    void isNationalId_WHEN_nationalIdIsNotValid_THEN_returnFalse() {
        assertFalse(isNationalId(null));
        assertFalse(isNationalId(""));
        assertFalse(isNationalId("123"));
        assertFalse(isNationalId("salam12541"));
        assertFalse(isNationalId("012345678912345"));
        assertFalse(isNationalId("5300020784")); // Unique Test Point e abc=000 ro test mikone.
        assertFalse(isNationalId("123456789a"));
        assertFalse(isNationalId("5300020788h"));
        assertFalse(isNationalId("1234567890"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "salam12541", "012345678912345", "5300020784", "123456789a", "5300020788h", "1234567890"})
    @NullAndEmptySource
    void parameterizedIsNationalId_WHEN_nationalIdIsNotValid_THEN_returnFalse(String param) {
        assertFalse(isNationalId(param));
    }

    @ParameterizedTest
    @EnumSource(value = Month.class, names = ".+BER", mode = EnumSource.Mode.MATCH_ANY)
    void test(Month month) {
    }

    @ParameterizedTest
    @EnumSource(value = Month.class, names = ".+BER", mode = EnumSource.Mode.MATCH_ANY)
    @NullSource
    void test2(Month month) {
    }

    @ParameterizedTest
    @NullSource
    void test3(Employee employee) { // kolan faqat hamin null ro mituni be onvane argument ersal koni faqat.
                                    // qeyre null nemishe.
    }
}