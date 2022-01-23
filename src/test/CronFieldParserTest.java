package test;

import main.com.company.exceptions.CronValidationException;
import main.com.company.services.CronFieldParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CronFieldParserTest {

    @Test
    void allMinutesTest() throws CronValidationException {
        CronFieldParser minuteValidator = new CronFieldParser(0, 59);
        String expected = "0-59";
        String actual = minuteValidator.validate("*");

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void allMinutesAndOneTest() throws CronValidationException {
        CronFieldParser minuteValidator = new CronFieldParser(0, 59);
        String expected = "0-59,1";
        String actual = minuteValidator.validate("*,1");

        Assertions.assertEquals(actual, expected);
    }

    @Test
    void invalidSpaceTest() {
        CronFieldParser minuteValidator = new CronFieldParser(0, 59);

        Assertions.assertThrows(CronValidationException.class, () -> minuteValidator.validate("0, 1"));
    }

    @Test
    void moreThanMaxInRangeTest(){
        CronFieldParser minuteValidator = new CronFieldParser(0, 59);

        Assertions.assertThrows(CronValidationException.class, () -> minuteValidator.validate("0-65"));
        Assertions.assertThrows(CronValidationException.class, () -> minuteValidator.validate("65-68"));
    }


    @Test
    void startAfterEndInRangeTest(){
        CronFieldParser minuteValidator = new CronFieldParser(0, 59);

        Assertions.assertThrows(CronValidationException.class, () -> minuteValidator.validate("50-45"));
    }

    @Test
    void lessThanMinTest(){
        CronFieldParser dayValidator = new CronFieldParser(1, 31);

        Assertions.assertThrows(CronValidationException.class, () -> dayValidator.validate("0"));
    }

    @Test
    void invalidCharactersTest(){
        CronFieldParser dayValidator = new CronFieldParser(1, 31);

        //TODO add cases
        Assertions.assertThrows(CronValidationException.class, () -> dayValidator.validate("1,$"));
        Assertions.assertThrows(CronValidationException.class, () -> dayValidator.validate("*!"));
        Assertions.assertThrows(CronValidationException.class, () -> dayValidator.validate("1.2"));
        Assertions.assertThrows(CronValidationException.class, () -> dayValidator.validate("1-A"));
    }

    @Test
    void extraCharacterTest(){
        CronFieldParser dayValidator = new CronFieldParser(1, 31);

        //TODO add cases
        Assertions.assertThrows(CronValidationException.class, () -> dayValidator.validate("1,,2"));
        Assertions.assertThrows(CronValidationException.class, () -> dayValidator.validate("1,"));
        Assertions.assertThrows(CronValidationException.class, () -> dayValidator.validate("1-2-3"));
        Assertions.assertThrows(CronValidationException.class, () -> dayValidator.validate("1--3"));
        Assertions.assertThrows(CronValidationException.class, () -> dayValidator.validate("1-3/2/3"));
        Assertions.assertThrows(CronValidationException.class, () -> dayValidator.validate("1-3//2"));
        Assertions.assertThrows(CronValidationException.class, () -> dayValidator.validate("**"));
    }


}
