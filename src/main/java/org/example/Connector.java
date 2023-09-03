package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Connector
{

    private String location;

    private final String API_KEY = "8e543484d0eacb36acd5b111290b809f";
    private Units unit = Units.standard;

    public Connector(String location, Units unit)
    {
        this.location = location;
        this.unit = unit;
    }
    public Connector()
    {

    }


    public BufferedReader getData()
    {
        return getData(this.location, this.unit);
    }
    private static final Logger logger = LogManager.getLogger(Connector.class);

    public BufferedReader getData(String location, Units unit)
    {
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + API_KEY + "&units=" + unit.toString() + "&lang=pl";
        URL url = null;
        BufferedReader rd = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            logger.error("MalformedURLException!\n: " + e.getMessage());
        }
        URLConnection conn = null;
        try {
            conn = url.openConnection();
            rd = new BufferedReader((new InputStreamReader((conn.getInputStream()))));
        } catch (IOException e) {
            logger.error("Couldnt open conection!\n: " + e.getMessage());
        }

        return rd;
    }
}
