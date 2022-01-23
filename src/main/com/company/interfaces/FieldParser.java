package main.com.company.interfaces;

import main.com.company.exceptions.CronValidationException;

/**
 * Validates and decodes expression to format from-to/interval (TODO)
 */
public interface FieldParser {
    String validate(String line) throws CronValidationException;
}
