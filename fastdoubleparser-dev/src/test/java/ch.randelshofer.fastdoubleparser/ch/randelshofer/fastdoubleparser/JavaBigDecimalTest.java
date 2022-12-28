/*
 * @(#)JavaBigDecimalFromByteArrayTest.java
 * Copyright © 2022 Werner Randelshofer, Switzerland. MIT License.
 */
package ch.randelshofer.fastdoubleparser;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static ch.randelshofer.fastdoubleparser.VirtualCharSequence.toByteArray;
import static ch.randelshofer.fastdoubleparser.VirtualCharSequence.toCharArray;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class JavaBigDecimalTest extends AbstractBigDecimalParserTest {


    @TestFactory
    public Stream<DynamicTest> dynamicTestsJavaBigDecimalParser_parallelParseBigDecimal_byteArray() {
        return createRegularTestData().stream()
                .filter(t -> t.charOffset() == 0 && t.charLength() == t.input().length())
                .map(t -> dynamicTest(t.title(),
                        () -> test(t, u -> JavaBigDecimalParser.parallelParseBigDecimal(
                                toByteArray(u.input())
                        ))));

    }

    @TestFactory
    public Stream<DynamicTest> dynamicTestsJavaBigDecimalParser_parallelParseBigDecimal_byteArray_int_int() {
        return createRegularTestData().stream()
                .map(t -> dynamicTest(t.title(),
                        () -> test(t, u -> JavaBigDecimalParser.parallelParseBigDecimal(
                                toByteArray(u.input()),
                                u.byteOffset(), u.byteLength()))));

    }

    @TestFactory
    public Stream<DynamicTest> dynamicTestsJavaBigDecimalParser_parseBigDecimal_byteArray() {
        return createRegularTestData().stream()
                .filter(t -> t.charOffset() == 0 && t.charLength() == t.input().length())
                .map(t -> dynamicTest(t.title(),
                        () -> test(t, u -> JavaBigDecimalParser.parseBigDecimal(
                                toByteArray(u.input())
                        ))));

    }

    @TestFactory
    public Stream<DynamicTest> dynamicTestsJavaBigDecimalParser_parseBigDecimal_byteArray_int_int() {
        return createRegularTestData().stream()
                .map(t -> dynamicTest(t.title(),
                        () -> test(t, u -> JavaBigDecimalParser.parseBigDecimal(
                                toByteArray(u.input()),
                                u.byteOffset(), u.byteLength()))));

    }


    @TestFactory
    public Stream<DynamicTest> dynamicTestsJavaBigDecimalParser_parallelParseBigDecimal_charArray() {
        return createRegularTestData().stream()
                .filter(t -> t.charOffset() == 0 && t.charLength() == t.input().length())
                .map(t -> dynamicTest(t.title(),
                        () -> test(t, u -> JavaBigDecimalParser.parallelParseBigDecimal(
                                toCharArray(u.input())
                        ))));

    }


    @TestFactory
    public Stream<DynamicTest> dynamicTestsJavaBigDecimalParser_parallelParseBigDecimal_charArray_int_int() {
        return createRegularTestData().stream()
                .map(t -> dynamicTest(t.title(),
                        () -> test(t, u -> JavaBigDecimalParser.parallelParseBigDecimal(
                                toCharArray(u.input()),
                                u.charOffset(), u.charLength()))));
    }

    @TestFactory
    public Stream<DynamicTest> dynamicTestsJavaBigDecimalParser_parseBigDecimal_charArray() {
        return createRegularTestData().stream()
                .filter(t -> t.byteOffset() == 0 && t.byteLength() == t.input().length())
                .map(t -> dynamicTest(t.title(),
                        () -> test(t, u -> JavaBigDecimalParser.parseBigDecimal(
                                toCharArray(u.input())
                        ))));

    }

    @TestFactory
    public Stream<DynamicTest> dynamicTestsJavaBigDecimalParser_parseBigDecimal_charArray_int_int() {
        return createRegularTestData().stream()
                .map(t -> dynamicTest(t.title(),
                        () -> test(t, u -> JavaBigDecimalParser.parseBigDecimal(
                                toCharArray(u.input()),
                                u.charOffset(), u.charLength()))));
    }


    @TestFactory
    public Stream<DynamicTest> dynamicTestsJavaBigDecimalParser_parallelParseBigDecimal_CharSequence() {
        return createRegularTestData().stream()
                .filter(t -> t.charOffset() == 0 && t.charLength() == t.input().length())
                .map(t -> dynamicTest(t.title(),
                        () -> test(t, u -> JavaBigDecimalParser.parallelParseBigDecimal(
                                u.input()
                        ))));

    }

    @TestFactory
    public Stream<DynamicTest> dynamicTestsJavaBigDecimalParser_parallelParseBigDecimal_CharSequence_int_int() {
        return createRegularTestData().stream()
                .map(t -> dynamicTest(t.title(),
                        () -> test(t, u -> JavaBigDecimalParser.parallelParseBigDecimal(
                                u.input(),
                                u.charOffset(), u.charLength()))));
    }

    @TestFactory
    public Stream<DynamicTest> dynamicTestsJavaBigDecimalParser_parseBigDecimal_CharSequence() {
        return createRegularTestData().stream()
                .filter(t -> t.byteOffset() == 0 && t.byteLength() == t.input().length())
                .map(t -> dynamicTest(t.title(),
                        () -> test(t, u -> JavaBigDecimalParser.parseBigDecimal(
                                u.input().toString()
                        ))));

    }

    @TestFactory
    public Stream<DynamicTest> dynamicTestsJavaBigDecimalParser_parseBigDecimal_CharSequence_int_int() {
        return createRegularTestData().stream()
                .map(t -> dynamicTest(t.title(),
                        () -> test(t, u -> JavaBigDecimalParser.parseBigDecimal(
                                u.input(),
                                u.charOffset(), u.charLength()))));
    }

}
