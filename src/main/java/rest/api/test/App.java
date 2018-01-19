package rest.api.test;

public class App {
    public static void main(String[] args) {

        RequestSender requestSender = new RequestSender();
        ResponseHandler responseHandler = new ResponseHandler();
        TerminalHandler terminalHandler = new TerminalHandler();

        String location = terminalHandler.readLocation();
        int numberOfDays = terminalHandler.readNumberOfDays();

        responseHandler.printResponse(requestSender.sendRequest(location, numberOfDays), numberOfDays);
    }
}