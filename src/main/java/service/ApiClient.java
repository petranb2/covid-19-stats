/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author kalogeros
 */
public class ApiClient {

    /**
     *
     */
    public static final String CONFIRMED_URL = "https://covid2019-api.herokuapp.com/timeseries/confirmed";
    public static final String DEATHS_URL = "https://covid2019-api.herokuapp.com/timeseries/deaths";
    public static final String RECOVERED_URL = "https://covid2019-api.herokuapp.com/timeseries/recovered";
    private final OkHttpClient client;
    private final Request request;

    public ApiClient(String URL) {
        this.client = new OkHttpClient();
        this.request = new Request.Builder().url(URL).build();

    }

    public String fetch() {
        String jsonResponse = null;
        try (Response response = client.newCall(request).execute()) {

            if (response.isSuccessful() && response.body() != null) {
                jsonResponse = response.body().string();
            }
        } catch (IOException ex) {
            Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return jsonResponse;
        }
    }
}
