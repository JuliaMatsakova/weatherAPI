package rest.api.test;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

public class TerminalHandler {

    TextIO textIO = TextIoFactory.getTextIO();

    public int readNumberOfDays() {
        int days = textIO.newIntInputReader().withMinVal(1)
                .read("Enter number of days of weather forecast (value ranges from 1 to 10):");
        return days;
    }

    public String readLocation() {
        String location = textIO.newStringInputReader().withDefaultValue("Location")
                .read("Enter US Zipcode, UK Postcode, Canada Postalcode or city name:");
        return location;
    }

    public void printIntoTerminal(String string) {
        TextTerminal terminal = textIO.getTextTerminal();
        terminal.println(string);
    }
}