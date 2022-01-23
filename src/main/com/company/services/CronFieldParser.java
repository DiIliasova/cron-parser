package main.com.company.services;

import main.com.company.exceptions.CronValidationException;
import main.com.company.interfaces.FieldParser;

import java.util.regex.Pattern;

//TODO parser acts as validator & decoder. Ideally it should transform each expression to from-to/interval
// then valueGenerator will not know about min and max value and will be more lightweight

public class CronFieldParser implements FieldParser {

    private static final String POSITIVE_NUMERIC_REGEXP = "[0-9]+";
    private Integer minValue;
    private Integer maxValue;
    private Pattern positiveNumPattern;

    public CronFieldParser(Integer minValue, Integer maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;

        this.positiveNumPattern = Pattern.compile(POSITIVE_NUMERIC_REGEXP);
    }

    @Override
    public String validate(String input) throws CronValidationException {
        if (input.equals("")) {
            throw new CronValidationException("expression is missing");
        }

        String simplifiedInput = replaceSpecialCharacters(input);

        //split expression by commas to separate individual expressions
        String[] expressions = simplifiedInput.split(",", -1);

        for (String expression : expressions) {
            if (expression.isEmpty()) {
                throw new CronValidationException("invalid ',' usage");
            }

            //if expression is a number - validate that it is in range minValue-maxValue
            if (positiveNumPattern.matcher(expression).matches()) {
                validateIntegerInRange(expression);
            } else {
                //if expression is not a number - split it as an interval
                String[] interval = expression.split("/", -1);
                //validate we dont have multiple '/'
                checkArraySize(interval);

                //validate value after '/' (delta) is a positive integer. Could be more than maxValue
                if (interval.length == 2) {
                    validatePositiveInteger(interval[1]);
                }

                //split left part of interval expression as a range
                String[] range = interval[0].split("-", -1);
                //validate we dont have multiple '-'
                checkArraySize(range);

                //validate start of the interval is a positive integer in range
                validatePositiveIntegerInRange(range[0]);
                if (range.length == 2) {
                    //validate end of the interval is a positive integer in range
                    validatePositiveIntegerInRange(range[1]);
                    if (Integer.valueOf(range[0]) > Integer.valueOf(range[1])) {
                        throw new CronValidationException("start of interval after end");
                    }
                }
            }
        }

        return simplifiedInput;
    }

    private void checkArraySize(String[] arr) throws CronValidationException {
        if (arr.length > 2) {
            throw new CronValidationException("invalid '-' or '/' operator");
        }
    }

    private void validatePositiveIntegerInRange(String value) throws CronValidationException {
        validatePositiveInteger(value);
        validateIntegerInRange(value);
    }

    private void validatePositiveInteger(String value) throws CronValidationException {
        if (!positiveNumPattern.matcher(value).matches()) {
            throw new CronValidationException("invalid expression");
        }
    }

    private void validateIntegerInRange(String value) throws CronValidationException {
        Integer i = Integer.valueOf(value);
        if (i < minValue || i > maxValue) {
            throw new CronValidationException("value out of range");
        }
    }

    //could be extended if we have additional rules
    private String replaceSpecialCharacters(String string) {
        return string.replace("*", minValue + "-" + maxValue);
    }
}
