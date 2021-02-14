/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eap.plh.covid19stats;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import models.Country;
import models.Coviddata;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author kalogeros
 */
public class NewMain2 {

    private static final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String urlToCall = "https://covid2019-api.herokuapp.com/timeseries/confirmed";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(urlToCall).build();
        EntityManager em; // δημιουργώ μια μεταβλητή entity manager
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("covid-db");
        em = emf.createEntityManager();
        try (Response response = client.newCall(request).execute()) {
            JsonParser parser = new JsonParser();

            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();
                JsonElement jsonTree = parser.parse(json);

                JsonObject jsonObject = jsonTree.getAsJsonObject();
                JsonElement confirmed = jsonObject.get("confirmed");

                System.out.println("CONFIRMED OBJECT------------:");

                if (confirmed.isJsonArray()) {
                    System.out.println("confirmed is array------------:");
                    JsonArray confirmedArray = confirmed.getAsJsonArray();

                    em.getTransaction().begin();
                    int counter = 0;
                    for (JsonElement country : confirmedArray) {
                        counter++;
                        JsonObject propsArray = country.getAsJsonObject();
                        Country newCountry = new Country();
                        int Proodqty = 0;

//                        System.out.println("Country start ------------:");
                        for (String key : propsArray.keySet()) {

                            Object value = country.getAsJsonObject().get(key);
                            if (key.equals("Province/State")) {
//                                System.out.println(value.toString());
                                continue;
                            }
                            if (key.equals("Country/Region")) {
                                newCountry.setName(value.toString());
//                                System.out.println(value.toString());
                                continue;
                            }
                            if (key.equals("Lat")) {
                                if (isNumeric(value.toString())) {
                                    newCountry.setLat(Double.valueOf(value.toString()));
//                                    System.out.println(value.toString());
                                    continue;
                                }
                                continue;
                            }
                            if (key.equals("Long")) {
                                if (isNumeric(value.toString())) {
                                    newCountry.setLong1(Double.valueOf(value.toString()));
//                                    System.out.println(value.toString());
                                    em.persist(newCountry);
//                                        em.flush();
                                    continue;
                                }
                                continue;
                            }

                            Coviddata covidData = new Coviddata();
                            covidData.setCountry(newCountry);
                            covidData.setDatakind(Short.parseShort("1"));
                            Date date1;
                            try {
                                date1 = new SimpleDateFormat("MM/dd/yy").parse(key);
                                covidData.setTrndate(date1);
                            } catch (ParseException ex) {
                                Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            if (isNumeric(value.toString())) {
                                covidData.setQty(Integer.parseInt(value.toString()));
                                Proodqty += covidData.getQty();
                            } else {
                                covidData.setQty(0);
                            }
                            covidData.setProodqty(Proodqty + covidData.getQty());

                            em.persist(covidData);

//                              em.flush();
//                            System.out.println("prop : " + key);
//                            System.out.println("value : " + value.toString());
                        }
                        if (counter >= 50) {
                            em.flush();
                            em.clear();
                            System.out.println("Batch insert ------------:");
                            counter = 0;
                        }
                    }
                    em.getTransaction().commit();
                }

            }
        }

    }

}
