package rest.api.test;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class ResponseHandler {

    TerminalHandler terminalHandler = new TerminalHandler();

    public void printResponse(String response, int numberOfDays) {
        printLocation(parseResponse(response));
        printCurrentWeather(parseResponse(response));
        printFullForecast(parseResponse(response), numberOfDays);
    }

    private NodeList parseResponse(String string) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(string)));
            Element root = doc.getDocumentElement();
            return root.getChildNodes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void printLocation(NodeList nodes) {
        Node location = nodes.item(0);
        NodeList nodeList = location.getChildNodes();

        if (nodeList.item(0).getNodeName().equals("name") &&
                nodeList.item(1).getNodeName().equals("region") &&
                nodeList.item(2).getNodeName().equals("country")) {

            String city = nodeList.item(0).getTextContent();
            String province = nodeList.item(1).getTextContent();
            String country = nodeList.item(2).getTextContent();

            terminalHandler.printIntoTerminal("Location: " + city + ", " + province + ", " + country);
        } else {
            System.out.println("Error with parsing response occurred.");
        }
    }

    private void printCurrentWeather(NodeList nodes) {
        Node currentWeather = nodes.item(1);
        NodeList nodeList = currentWeather.getChildNodes();

        if (nodeList.item(2).getNodeName().equals("temp_c") &&
                nodeList.item(6).getNodeName().equals("wind_mph") &&
                nodeList.item(9).getNodeName().equals("wind_dir") &&
                nodeList.item(14).getNodeName().equals("humidity") &&
                nodeList.item(16).getNodeName().equals("feelslike_c")) {

            String temperature = nodeList.item(2).getTextContent();
            String conditions = nodeList.item(5).getFirstChild().getTextContent();
            String windSpeed = nodeList.item(6).getTextContent();
            String windDirection = nodeList.item(9).getTextContent();
            String humidity = nodeList.item(14).getTextContent();
            String feelsLike = nodeList.item(16).getTextContent();

            terminalHandler.printIntoTerminal("CURRENT WEATHER");
            terminalHandler.printIntoTerminal("Current temperature: " + temperature + "C");
            terminalHandler.printIntoTerminal("Feels like: " + feelsLike + "C");
            terminalHandler.printIntoTerminal("Conditions: " + conditions);
            terminalHandler.printIntoTerminal("Wind speed: " + windSpeed + "mph");
            terminalHandler.printIntoTerminal("Wind direction: " + windDirection);
            terminalHandler.printIntoTerminal("Humidity: " + humidity);
        } else {
            System.out.println("Error with parsing response occurred.");
        }
    }

    private void printFullForecast(NodeList nodes, int numberOfDays) {
        Node forecasts = nodes.item(2);
        NodeList forecastday = forecasts.getChildNodes();

        terminalHandler.printIntoTerminal(numberOfDays + "-DAYS FORECAST");

        for (int i = 0; i < numberOfDays; i++) {
            printOneDayForecast(forecastday, i);
        }
    }

    private void printOneDayForecast(NodeList nodes, int d) {
        Node day = nodes.item(d);
        if (
                day.getChildNodes().item(0).getNodeName().equals("date") &&
                        day.getChildNodes().item(2).getChildNodes().item(0).getNodeName().equals("maxtemp_c") &&
                        day.getChildNodes().item(2).getChildNodes().item(2).getNodeName().equals("mintemp_c") &&
                        day.getChildNodes().item(2).getChildNodes().item(6).getNodeName().equals("maxwind_mph") &&
                        day.getChildNodes().item(2).getChildNodes().item(12).getNodeName().equals("avghumidity") &&
                        day.getChildNodes().item(2).getChildNodes().item(13).getChildNodes().item(0).getNodeName().equals("text")
                ) {

            String date = day.getChildNodes().item(0).getTextContent();
            String maxTemp = day.getChildNodes().item(2).getChildNodes().item(0).getTextContent();
            String minTemp = day.getChildNodes().item(2).getChildNodes().item(2).getTextContent();
            String maxWindTemp = day.getChildNodes().item(2).getChildNodes().item(6).getTextContent();
            String averageHumidity = day.getChildNodes().item(2).getChildNodes().item(12).getTextContent();
            String conditions = day.getChildNodes().item(2).getChildNodes().item(13).getChildNodes().item(0).getTextContent();

            terminalHandler.printIntoTerminal("Date: " + date);
            terminalHandler.printIntoTerminal("Maximum temperature: " + maxTemp + "C");
            terminalHandler.printIntoTerminal("Minimum temperature: " + minTemp + "C");
            terminalHandler.printIntoTerminal("Maximum wind: " + maxWindTemp + "mph");
            terminalHandler.printIntoTerminal("Average humidity: " + averageHumidity);
            terminalHandler.printIntoTerminal("Conditions: " + conditions);

        } else {
            System.out.println("Error with parsing response occurred.");
        }
    }
}
