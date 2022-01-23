package main.com.company.services;

import javafx.util.Pair;
import main.com.company.exceptions.CronValidationException;

import java.util.ArrayList;
import java.util.List;

public class CronParser {

    private List<Pair<String, FieldReader>> markup;
    private static final int LENGTH = 6;

    public CronParser() {
        markup = new ArrayList<>();

        //TODO all hourly, monthly parsers and value generators have to be signletons
        FieldReader minuteReader = new FieldReader(new CronFieldParser(0, 59), new CronFieldValueGenerator(0, 59), new CronFieldFormatter());
        FieldReader hourReader = new FieldReader(new CronFieldParser(0, 23), new CronFieldValueGenerator(0, 23), new CronFieldFormatter());
        FieldReader domReader = new FieldReader(new CronFieldParser(1, 31), new CronFieldValueGenerator(1, 31), new CronFieldFormatter());
        FieldReader monthReader = new FieldReader(new CronFieldParser(1, 12), new CronFieldValueGenerator(1, 12), new CronFieldFormatter());
        FieldReader dowReader = new FieldReader(new CronFieldParser(0, 6), new CronFieldValueGenerator(0, 6), new CronFieldFormatter());
        FieldReader commandReader = new FieldReader(s -> s, s -> s, new CronFieldFormatter());
        markup.add(new Pair<>("minute", minuteReader));
        markup.add(new Pair<>("hour", hourReader));
        markup.add(new Pair<>("day of month", domReader));
        markup.add(new Pair<>("month", monthReader));
        markup.add(new Pair<>("day of week", dowReader));
        markup.add(new Pair<>("command", commandReader));
    }


    public StringBuilder parse(String line) throws CronValidationException {
        String[] arguments = line.split("\\s+");
        if (arguments.length != LENGTH) {
            throw new CronValidationException("invalid number of arguments");
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < markup.size(); i++) {
            Pair<String, FieldReader> lineNameToReader = markup.get(i);

            sb.append(lineNameToReader.getValue().read(lineNameToReader.getKey(), arguments[i]));
            sb.append("\n");
        }

        return sb;
    }
}
