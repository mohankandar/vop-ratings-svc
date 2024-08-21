package com.wynd.vop.ratingssvc.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StringUtilTest {

    @Before
    public void setUp() {
    }

    @Test
    public void testGetMask() {
        // Setup
        String testString = "TestString";
        char testMaskChar = 'x';
        int testNumViewableChars = 5;
        String expectedString = "xxxxxtring";

        // Execute Test
        String output = StringUtil.getMask(testString, testMaskChar, testNumViewableChars);

        // Verifications
        assertEquals(expectedString, output);
    }

    @Test
    public void testGetMask_NewChar() {
        // Setup
        String testString = "TestString";
        char testMaskChar = 'y';
        int testNumViewableChars = 5;
        String expectedString = "yyyyytring";

        // Execute Test
        String output = StringUtil.getMask(testString, testMaskChar, testNumViewableChars);

        // Verifications
        assertEquals(expectedString, output);
    }

    @Test
    public void testGetMask_Empty() {
        // Setup
        String testString = "";
        char testMaskChar = 'x';
        int testNumViewableChars = 5;
        String expectedString = "";

        // Execute Test
        String output = StringUtil.getMask(testString, testMaskChar, testNumViewableChars);

        // Verifications
        assertEquals(expectedString, output);
    }

    @Test
    public void testGetMask_Null() {
        // Setup
        String testString = null;
        char testMaskChar = 'x';
        int testNumViewableChars = 5;
        String expectedString = null;

        // Execute Test
        String output = StringUtil.getMask(testString, testMaskChar, testNumViewableChars);

        // Verifications
        assertEquals(expectedString, output);
    }

    @Test
    public void testGetMask_NumMismatch_Equal() {
        // Setup
        String testString = "TestString";
        char testMaskChar = 'x';
        int testNumViewableChars = 10;
        String expectedString = "TestString";

        // Execute Test
        String output = StringUtil.getMask(testString, testMaskChar, testNumViewableChars);

        // Verifications
        assertEquals(expectedString, output);
    }

    @Test
    public void testGetMask_NumMismatch_Greater() {
        // Setup
        String testString = "TestString";
        char testMaskChar = 'x';
        int testNumViewableChars = 12345;
        String expectedString = "TestString";

        // Execute Test
        String output = StringUtil.getMask(testString, testMaskChar, testNumViewableChars);

        // Verifications
        assertEquals(expectedString, output);
    }

    @Test
    public void testGetMask_NumMismatch_LTZero() {
        // Setup
        String testString = "TestString";
        char testMaskChar = 'x';
        int testNumViewableChars = -1;
        String expectedString = "xxxxxxxxxx";

        // Execute Test
        String output = StringUtil.getMask(testString, testMaskChar, testNumViewableChars);

        // Verifications
        assertEquals(expectedString, output);
    }

    @Test
    public void testGetMask4() {
        // Setup
        String testString = "TestString";
        String expectedString = "xxxxxxring";

        // Execute Test
        String output = StringUtil.getMask4(testString);

        // Verifications
        assertEquals(expectedString, output);
    }

    @Test
    public void testIsNotNullAndNotEmpty_True() {
        // Setup
        String testString = "TestString";

        // Execute Test
        Boolean output = StringUtil.isNotNullAndNotEmpty(testString);

        // Verifications
        assertTrue(output);
    }

    @Test
    public void testIsNotNullAndNotEmpty_Null() {
        // Setup
        String testString = null;

        // Execute Test
        Boolean output = StringUtil.isNotNullAndNotEmpty(testString);

        // Verifications
        assertFalse(output);
    }

    @Test
    public void testIsNotNullAndNotEmpty_Empty() {
        // Setup
        String testString = "";

        // Execute Test
        Boolean output = StringUtil.isNotNullAndNotEmpty(testString);

        // Verifications
        assertFalse(output);
    }

    @Test
    public void testObjectToXMLString() {
        // Setup
        List<String> testObject = new ArrayList<String>();
        testObject.add("testString");
        String expectedOutput = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<arrayList/>\n";

        // Execute Test
        String output = StringUtil.objectToXMLString(testObject);

        // Verifications
        assertEquals(expectedOutput, output);
    }
}
