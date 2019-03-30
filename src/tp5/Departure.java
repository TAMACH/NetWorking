/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5;

import com.google.gson.JsonElement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Maroine
 */
public class Departure {

    String name;
    Date time;
    SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

    public Departure(JsonElement e) {
        name = e.getAsJsonObject().get("route").getAsJsonObject().get("name").getAsString();
        time = new Date(e.getAsJsonObject().get("stop_date_time").getAsJsonObject().get("departure_date_time").getAsString());
    }

    public String toString() {
        return "(" + name + ", " + time.toString() + ")";
    }
}
