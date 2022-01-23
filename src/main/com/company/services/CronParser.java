package main.com.company.services;

import main.com.company.exceptions.CronValidationException;

import java.util.LinkedHashMap;
import java.util.Map;

public class CronParser {

    private LinkedHashMap<String, FieldReader> markup;
    private static final int LENGTH = 6;

    public CronParser() {
        markup = new LinkedHashMap<>();

        //TODO all hourly, monthly parsers and value generators have to be signletons
        FieldReader minuteReader = new FieldReader(new CronFieldParser(0, 59), new CronFieldValueGenerator(0, 59), new CronFieldFormatter());
        FieldReader hourReader = new FieldReader(new CronFieldParser(0, 23), new CronFieldValueGenerator(0, 23), new CronFieldFormatter());
        FieldReader domReader = new FieldReader(new CronFieldParser(1, 31), new CronFieldValueGenerator(1, 31), new CronFieldFormatter());
        FieldReader monthReader = new FieldReader(new CronFieldParser(1, 12), new CronFieldValueGenerator(1, 12), new CronFieldFormatter());
        FieldReader dowReader = new FieldReader(new CronFieldParser(0, 6), new CronFieldValueGenerator(0, 6), new CronFieldFormatter());
        FieldReader commandReader = new FieldReader(s -> s, s -> s, new CronFieldFormatter());
        markup.put("minute", minuteReader);
        markup.put("hour", hourReader);
        markup.put("day of month", domReader);
        markup.put("month", monthReader);
        markup.put("day of week", dowReader);
        markup.put("command", commandReader);
    }


    public StringBuilder parse(String line) throws CronValidationException {
        String[] arguments = line.split("\\s+");
        if (arguments.length != LENGTH) {
            throw new CronValidationException("invalid number of arguments");
        }

        StringBuilder sb = new StringBuilder();

        int i = 0;
        for (Map.Entry<String, FieldReader> entry : markup.entrySet()) {
            sb.append(entry.getValue().read(entry.getKey(), arguments[i]));
            sb.append("\n");
            i++;
        }

        return sb;
    }
}
