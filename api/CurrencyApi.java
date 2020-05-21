package excercises.programs.currencyConverter.api;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.gson.*;

public class CurrencyApi {
    final private String apiBase = "https://api.exchangeratesapi.io/latest";

    public CurrencyApi()
    {

    }

    public HashMap<String, Double> makeRequest( String base, String toCurrency ) throws IOException
    {
        // create the HashMap and the base url for the api call
        // default method GET
        HashMap<String, Double> responseMap = new HashMap<String, Double>();
        URL request = new URL( this.apiBase + "?base=" + base + "&symbols=" + toCurrency );


        // Open connection to the api url and start Buffer Reader
        // with new Input Stream Reader from conn->inputStream
        URLConnection conn = request.openConnection();
        BufferedReader input = new BufferedReader(
                new InputStreamReader(
                        conn.getInputStream()
                )
        );


        // installed googles gson to be able to parse
        // apis json response and later put in into the hashmap
        JsonElement root = new JsonParser().parse(input);


        // get the values from the json object and save them
        // as a double value into the toCurrencyValue
        // variables
        double toCurrencyValue = root.getAsJsonObject()
                .get("rates")
                .getAsJsonObject()
                .get(toCurrency)
                .getAsDouble();


        // put values into the resonse Map and return the
        // response map
        responseMap.put(base, 1.0);
        responseMap.put(toCurrency, toCurrencyValue);

        return responseMap;
    }

    public static void main(String[] args)
    {
        // test
        CurrencyApi req = new CurrencyApi();

        try {
            System.out.println(req.makeRequest("EUR", "USD"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
