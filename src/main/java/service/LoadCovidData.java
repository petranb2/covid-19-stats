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
import javax.persistence.EntityManager;
import javax.persistence.Query;
import models.Country;
import models.Coviddata;
import utils.Constants;
import static utils.IsNumeric.isNumeric;

/**
* Τμήμα ΗΛΕ 46
* @author Κουλιανός Πέτρος 119076
* @author Κρουκλόβα Όλγα 94996
* @author Μάλαμας Σάββας 119131
*/
public class LoadCovidData {

    private final boolean deaths;
    private final boolean recovered;
    private final boolean confirmed;
    private final boolean countriesOnly;

    /**
     *
     * @param deaths true | false
     * @param recovered true | false
     * @param confirmed true | false
     * @param countriesOnly true | false
     */
    public LoadCovidData(boolean deaths, boolean recovered, boolean confirmed, boolean countriesOnly) {
        this.deaths = deaths;
        this.recovered = recovered;
        this.confirmed = confirmed;
        this.countriesOnly = countriesOnly;
    }

    /**
     *
     */
    public void startLoadData() {
        Thread thread = new Thread() {
            public void run() {
                if (deaths) {
                    loadData(Constants.DEATHS_URL, Constants.PROPERTY_JSON_DEATHS, Constants.DATA_KIND_DEATHS, countriesOnly);
                }
                if (recovered) {
                    loadData(Constants.RECOVERED_URL, Constants.PROPERTY_JSON_RECOVERED, Constants.DATA_KIND_RECOVERED, countriesOnly);
                }
                if (confirmed) {
                    loadData(Constants.CONFIRMED_URL, Constants.PROPERTY_JSON_CONFIRMED, Constants.DATA_KIND_CONFIRMED, countriesOnly);
                }
            }
        };
        System.out.println("Thread Start From startLoadData");
        thread.start();
        try {
            thread.join();
            System.out.println("Thread Ends From startLoadData");
        } catch (InterruptedException ex) {
            Logger.getLogger(LoadCovidData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param URL
     * @param dataProperty
     * @param dataKind
     * @param countriesOnly
     */
    private void loadData(String URL, String dataProperty, short dataKind, boolean countriesOnly) {
        Thread thread = new Thread() {
            public void run() {
                JsonParser parser = new JsonParser();
                ApiClient apiClient = new ApiClient(URL);
                String json = apiClient.fetch();
                if (json == null) {
                    System.out.println("Null response from REST API ");
                    return;
                }
                JsonElement jsonTree = parser.parse(json);
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                JsonElement data = jsonObject.get(dataProperty);

                saveData(data.getAsJsonArray(), dataKind, countriesOnly);
            }
        };
        System.out.println("Thread Start From " + URL);
        thread.start();
        try {
            thread.join();
            System.out.println("Thread Ends From " + URL);
        } catch (InterruptedException ex) {
            Logger.getLogger(LoadCovidData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param confirmedArray
     * @param dataKind
     * @param countriesOnly
     */
    private void saveData(JsonArray confirmedArray, short dataKind, boolean countriesOnly) {
        EntityManager em = AppDatabase.getAppEntityManager();
        em.getTransaction().begin();
        int counter = 0;
        for (JsonElement country : confirmedArray) {
            counter++;
            JsonObject propsArray = country.getAsJsonObject();
            Country newCountry = new Country();
            int Proodqty = 0;
            boolean isCountrySaved = false;

//                        System.out.println("Country start ------------:");
            for (String key : propsArray.keySet()) {

                Object value = country.getAsJsonObject().get(key);
                if (key.equals("Province/State")) {
                    continue;
                }
                if (key.equals("Country/Region")) {
                    newCountry.setName(value.toString().replace("\"", ""));
                    continue;
                }
                if (key.equals("Lat")) {
                    if (isNumeric(value.toString())) {
                        newCountry.setLat(Double.valueOf(value.toString()));
                        continue;
                    }
                    continue;
                }
                if (key.equals("Long")) {
                    if (isNumeric(value.toString())) {
                        newCountry.setLong1(Double.valueOf(value.toString()));
                        Query namedQuery = em.createNamedQuery("Country.findByName");
                        namedQuery.setParameter("name", newCountry.getName());
                        List<Country> fetchedCountry = (List<Country>) namedQuery.getResultList();
                        // Check if country is already in db
                        if (fetchedCountry.size() >= 1) {
                            isCountrySaved = true;
                            System.out.println("Country is in db");
                            continue;
                        }
                        // Country is not in db must be saved
                        em.persist(newCountry);
                        continue;
                    }

                    continue;
                }
                if (countriesOnly) {
                    break;
                }
                Coviddata covidData = new Coviddata();
                // If country is in db fetch it and set it to the new covidData
                if (isCountrySaved == true) {
                    Query namedQuery = em.createNamedQuery("Country.findByName");
                    namedQuery.setParameter("name", newCountry.getName());
                    Country fetchedCountry = (Country) namedQuery.getSingleResult();
                    covidData.setCountry(fetchedCountry);
                } else {
                    covidData.setCountry(newCountry);
                }

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

                Query namedQuery2 = em.createNamedQuery("Coviddata.findByCountryAndDataKindByDateRange");
                namedQuery2.setParameter("country", covidData.getCountry());
                namedQuery2.setParameter("datakind", covidData.getDatakind());
                namedQuery2.setParameter("fromDate", covidData.getTrndate());
                namedQuery2.setParameter("toDate", covidData.getTrndate());
                List<Coviddata> fetchedCoviddata = (List<Coviddata>) namedQuery2.getResultList();
                // Check if covidData is already in db
                if (fetchedCoviddata.size() >= 1) {
                    System.out.println("Coviddata is in db");
                    continue;
                }
                em.persist(covidData);
            }
            if (counter == 5) {
                em.flush();
                em.clear();
                System.out.println("Batch insert ------------:");
                counter = 0;
                break;
            }
        }

        em.getTransaction()
                .commit();
    }
}
