package com.mehdisarf;

import org.junit.jupiter.api.Test;

import static com.mehdisarf.StringUtil.isName;
import static com.mehdisarf.StringUtil.isNationalId;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void isName_WHEN_nameIsValid_THEN_returnsTrue() {
        assertTrue(isName("mehdi"));
        assertTrue(isName("mehdi-sarf"));
        assertTrue(isName("mehdi sarf"));
        assertTrue(isName("mehdi alireza-sarf"));
        assertTrue(isName("mehdi-alireza sarf"));
        assertTrue(isName("mehdi alireza sarf"));
    }

    @Test
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

    @Test
    void isNationalId_WHEN_nationalIdIsValid_THEN_returnTrue() {
        assertTrue(isNationalId("5300020788"));
        assertTrue(isNationalId("0520258649")); // Unique Test Point e abc=001 ro test mikone.
        assertTrue(isNationalId("0017440701")); // Unique Test Point e abc=110 ro test mikone.
        assertTrue(isNationalId("0010010041")); // Unique Test Point e abc=110 ro test mikone.
    }

    @Test
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
}