package main.com.company.services;

import main.com.company.interfaces.FieldValueGenerator;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class CronFieldValueGenerator implements FieldValueGenerator {

    private static final String POSITIVE_NUMERIC_REGEXP = "[0-9]+";

    private Integer minValue;
    private Integer maxValue;
    private Pattern positiveNumPattern;


    public CronFieldValueGenerator(Integer minValue, Integer maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;

        this.positiveNumPattern = Pattern.compile(POSITIVE_NUMERIC_REGEXP);
    }

    @Override
    public String generateValues(String input) {
        Set<Integer> set = new HashSet<>();

        //split expression by commas to separate individual expressions
        String[] expressions = input.split(",", -1);
        for (String expression : expressions) {
            //if expression is a number - add it to the set
            if (positiveNumPattern.matcher(expression).matches()) {
                set.add(Integer.valueOf(expression));
            } else {
                //if expression is not a number - split it as an interval
                int end = maxValue;
                int delta = 1;

                String[] interval = expression.split("/", -1);

                if (interval.length == 2) {
                    delta = Integer.valueOf(interval[1]);
                }

                //split left part of interval expression as a range
                String[] range = interval[0].split("-", -1);

                //first part of the interval is a start point
                int start = Integer.valueOf(range[0]);
                //second part of the interval is an end point if present
                if (range.length == 2) {
                    end = Integer.valueOf(range[1]);
                }

                for (int i = start; i <= end; i += delta) {
                    set.add(i);
                }
            }
        }

        return set.stream().map(String::valueOf).collect(Collectors.joining(","));
    }
}
