package main.com.company.services;

import main.com.company.exceptions.CronValidationException;
import main.com.company.interfaces.FieldValueGenerator;
import main.com.company.interfaces.FieldParser;

public class FieldReader {

    private FieldParser validator;
    private FieldValueGenerator parser;
    private CronFieldFormatter formatter;

    FieldReader(FieldParser validator, FieldValueGenerator parser, CronFieldFormatter formatter) {
        this.validator = validator;
        this.parser = parser;
        this.formatter = formatter;
    }

    public String read(String fieldName, String input) throws CronValidationException {
        String validatedInput = validator.validate(input);
        String values = parser.generateValues(validatedInput);
        return formatter.format(fieldName, values);
    }
}
