package rest.api.test;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class RequestSender {

    private String URL = "http://api.apixu.com/v1/forecast.xml?key=897aacd1cd8b4732869173244180101&q=";

    public String sendRequest(String postalCode, int numberOfDays) {

        CloseableHttpResponse request = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(URL + postalCode + "&days=" + numberOfDays);
        try {
            request = httpclient.execute(httpget);
            return getResponse(request);
        } catch (java.io.IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                request.close();
            } catch (java.io.IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return "";
    }

    private String getResponse(CloseableHttpResponse request) {
        try {
            return EntityUtils.toString(request.getEntity(), "UTF-8");
        } catch (java.io.IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}