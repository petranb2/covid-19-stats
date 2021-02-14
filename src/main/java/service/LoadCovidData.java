/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import models.Country;
import models.Coviddata;

/**
 *
 * @author kalogeros
 */
public class LoadCovidData {

    private boolean deaths;
    private boolean recovered;
    private boolean confirmed;
    private final short DATA_KIND_DEATHS = 1;
    private final short DATA_KIND_RECOVERED = 2;
    private final short DATA_KIND_CONFIRMED = 3;
    private final String PROPERTY_JSON_DEATHS = "deaths";
    private final String PROPERTY_JSON_RECOVERED = "recovered";
    private final String PROPERTY_JSON_CONFIRMED = "confirmed";

    public LoadCovidData(boolean deaths, boolean recovered, boolean confirmed) {
        this.deaths = deaths;
        this.recovered = recovered;
        this.confirmed = confirmed;
    }

    public void startLoadData() {
        if (deaths) {
            loadData(ApiClient.DEATHS_URL, PROPERTY_JSON_DEATHS, DATA_KIND_DEATHS);
        }
        if (recovered) {
            loadData(ApiClient.RECOVERED_URL, PROPERTY_JSON_RECOVERED, DATA_KIND_RECOVERED);
        }
        if (confirmed) {
            loadData(ApiClient.CONFIRMED_URL, PROPERTY_JSON_CONFIRMED, DATA_KIND_CONFIRMED);
        }
    }

    private void loadData(String URL, String dataProperty, short dataKind) {
        Thread thread = new Thread() {
            public void run() {
                System.out.println("Thread Running");
                JsonParser parser = new JsonParser();
                ApiClient apiClient = new ApiClient(URL);
                String json = apiClient.fetch();
                JsonElement jsonTree = parser.parse(json);

                JsonObject jsonObject = jsonTree.getAsJsonObject();
                JsonElement data = jsonObject.get(dataProperty);
                saveData(data.getAsJsonArray(), dataKind);
            }
        };

        thread.start();
        try {
            thread.join();
            System.out.println("Thread Ends From " + URL);
        } catch (InterruptedException ex) {
            Logger.getLogger(LoadCovidData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private static final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    private void saveData(JsonArray confirmedArray, short dataKind) {
        EntityManager em; // δημιουργώ μια μεταβλητή entity manager
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("covid-db");
        em = emf.createEntityManager();
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
                        Query namedQuery = em.createNamedQuery("Country.findByName");
                        namedQuery.setParameter("name", newCountry.getName());
                        boolean isCountrySaved = false;
                        try {
                            List<Country> fetchedCountry = (List<Country>) namedQuery.getResultList();
                            if (fetchedCountry.size() >= 1) {
                                isCountrySaved = true;
                                System.out.println("Country is in db");
                                break;
                            }

                        } catch (NoResultException ex) {
                            System.out.println("Country is not in db");
                        } catch (NonUniqueResultException ex) {
                            System.out.println("Country dublicate in db");
                            isCountrySaved = true;
                        }

                        if (isCountrySaved == false) {
                            em.persist(newCountry);
                        }

//                                        em.flush();
                        continue;
                    }
                    continue;
                }

                Coviddata covidData = new Coviddata();
                covidData.setCountry(newCountry);
                covidData.setDatakind(dataKind);
                Date date1;
                try {
                    date1 = new SimpleDateFormat("MM/dd/yy").parse(key);
                    covidData.setTrndate(date1);
                } catch (ParseException ex) {
                    Logger.getLogger(LoadCovidData.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (isNumeric(value.toString())) {
                    covidData.setQty(Integer.parseInt(value.toString()));
                    Proodqty += covidData.getQty();
                } else {
                    covidData.setQty(0);
                }
                covidData.setProodqty(Proodqty + covidData.getQty());

                Query namedQuery2 = em.createNamedQuery("Coviddata.findByCountryAndDataKindAndTrndate");
                namedQuery2.setParameter("datakind", covidData.getDatakind());
                namedQuery2.setParameter("trndate", covidData.getTrndate());
                namedQuery2.setParameter("country", covidData.getCountry());
                List<Coviddata> fetchedCoviddata = (List<Coviddata>) namedQuery2.getResultList();
                if (fetchedCoviddata.size() >= 1) {
                    System.out.println("Coviddata is in db");
                    break;
                }
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
