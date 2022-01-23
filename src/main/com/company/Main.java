package main.com.company;

import main.com.company.exceptions.CronValidationException;
import main.com.company.services.CronParser;

public class Main {

    //TODO add Printer as separate interface to support console, file output etc.
    public static void main(String[] args) {
        StringBuilder result;
        try {
            result = (new CronParser()).parse(args[0]);
            System.out.print(result.toString());
        } catch (CronValidationException e) {
            // TODO add logger
            //e.printStackTrace();
            System.out.println("execution error");
            System.out.println(e.getMessage());
        }
    }
}
