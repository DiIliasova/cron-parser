package test;

import main.com.company.services.CronFieldValueGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

class CronFieldValueGeneratorTest {


    @Test
    void rangeTest(){
        CronFieldValueGenerator minuteField = new CronFieldValueGenerator(0, 59);
        String expected = IntStream.rangeClosed(0, 15).boxed().map(String::valueOf).collect(Collectors.joining(","));
        String actual = minuteField.generateValues("0-15");

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void intervalTest(){
        CronFieldValueGenerator minuteField = new CronFieldValueGenerator(0, 59);
        String expected = IntStream.rangeClosed(0, 59).filter(i -> i%2 == 0).boxed().map(String::valueOf).collect(Collectors.joining(","));
        String actual = minuteField.generateValues("0/2");

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void intervalWithRangeTest(){
        CronFieldValueGenerator minuteField = new CronFieldValueGenerator(0, 59);
        String expected = IntStream.rangeClosed(0, 29).filter(i -> i%2 == 0).boxed().map(String::valueOf).collect(Collectors.joining(","));
        String actual = minuteField.generateValues("0-29/2");

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void intervalWithRangeWithListTest(){
        CronFieldValueGenerator minuteField = new CronFieldValueGenerator(0, 59);
        String expected = IntStream.rangeClosed(0, 29).filter(i -> i%2 == 0 || i == 1).boxed().map(String::valueOf).collect(Collectors.joining(","));

        String actual = minuteField.generateValues("0-29/2,1");
        Assertions.assertEquals(actual, expected);

        actual = minuteField.generateValues("1,0-29/2");
        Assertions.assertEquals(actual, expected);
    }
}
