package main.com.company.services;

import main.com.company.interfaces.FieldFormatter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;


public class CronFieldFormatter implements FieldFormatter {

    private static final int NAME_COLUMN_WIDTH = 14;

    @Override
    //TODO instead of comma separated value string pass List<String>
    public String format(String name, String values) {
        StringBuilder sb = new StringBuilder();

        sb.append(appendToWidth(name));

        String result = Arrays.stream(values.split(","))
                .sorted(Comparator.comparing(Integer::valueOf))
                .collect(Collectors.joining(" "));

        sb.append(result);

        return sb.toString();
    }


    private StringBuilder appendToWidth(String s) {
        int i = NAME_COLUMN_WIDTH - s.length();
        StringBuilder sb = new StringBuilder(s);
        while (i > 0) {
            sb.append(" ");
            i--;
        }
        return sb;
    }
}
