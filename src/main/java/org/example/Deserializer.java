package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

public class Deserializer
{
    private float temp;
    private float feelsLike;
    private int windSpeed;
    private String desc;
    private int pressure;
    private int humidity;
    private static String location = "Kraków";

    private String sky;


    public void setLocation(String val)
    {
        this.location = val;
    }

    public String getLocation()
    {
        return location;
    }
    public String getSky() { return sky; }
    public float getTemp()
    {
        return this.temp;
    }
    public float getHumidity()
    {
        return this.humidity;
    }
    public float getFeelsLike()
    {
        return this.feelsLike;
    }
    private static final Logger logger = LogManager.getLogger(Deserializer.class);
    public String json2string()
    {
        StringBuilder result = new StringBuilder();
        String line = "ERROR";
        BufferedReader connector = new Connector(this.location, Units.metric).getData();
        while(true)
        {
            try {
                if ((line = connector.readLine()) == null) break;
            } catch (IOException e) {
                logger.error("Couldnt read line!");
            }
            finally{
                result.append(line);
            }
        }
        try {
            connector.close();
        } catch (IOException e) {
            logger.error("Couldnt close connection!\n" + e);
        }
        return result.toString();
    }

    public void updateFields()
    {
        String jsonStr = json2string();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);

            JSONObject o = (JSONObject) jsonObject.get("main");

            this.temp = o.getFloat("temp");
            this.feelsLike = o.getFloat("feels_like");
            this.humidity = o.getInt("humidity");
            this.desc = jsonObject.getString("name");

            JSONArray arr = (JSONArray) jsonObject.get("weather");
            this.sky = arr.getJSONObject(0).getString("main");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public Deserializer()
    {
        updateFields();
    }
}
