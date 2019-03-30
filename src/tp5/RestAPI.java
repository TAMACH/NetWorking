/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp5;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;
import javax.net.ssl.HttpsURLConnection;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Maroine
 */
public class RestAPI {

    public static String SNCF_API_URL = "https://api.sncf.com/v1/coverage/sncf";
    private String token = "82ee4aa2-ecf2-4844-b9a1-0bcbff7a285e";

    public RestAPI() throws Exception {

        //System.out.println(get("places?q={Créteil}"));
        System.out.println(getGares("Créteil"));

    }

    public JsonElement get(String s) throws IOException {
        SNCF_API_URL += "/" + s;
        URL url = new URL(SNCF_API_URL);
        //System.out.println(URLEncoder.encode(SNCF_API_URL, "UTF-8"));
        HttpsURLConnection httpsUC = (HttpsURLConnection) url.openConnection();

        String codedToken = "Basic " + Base64.getEncoder().encodeToString(token.getBytes()) + ": ";
        httpsUC.setRequestProperty("Authorization", codedToken);

        InputStream is = httpsUC.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        JsonParser parser = new JsonParser();
        JsonElement el = parser.parse(isr);
        return el;
    }

    public Map<String, String> getGares(String id) throws Exception {
        Map<String, String> map = new HashMap<>();
        get("places?q=" + id)
                .getAsJsonObject().get("places")
                .getAsJsonArray().forEach(el -> {
                    JsonObject o = el.getAsJsonObject();
                    if (o.get("embedded_type").getAsString().equals("stop_area")) {
                        map.put(o.get("id").getAsString(), o.get("name").getAsString());
                    }
                });
        return map;
    }

    public static void main(String[] args) throws Exception {
        new RestAPI();
    }
}
