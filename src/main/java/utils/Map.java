/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.google.gson.JsonArray;
import eap.plh.covid19stats.TestCovidMap;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.ListModel;
import models.Country;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import service.AppQueries;

/**
 *
 * @author kalogeros
 */
public class Map {

    /**
     *
     * @param mainCountryName
     * @param startDate
     * @param endDate
     * @return
     */
    public static JsonArray buildLocationJSON(String mainCountryName, Date startDate, Date endDate) {
        JsonArray rootJSONArray = new JsonArray();
        JsonArray mainCountryJSON = buildCountryJSONArray(mainCountryName, startDate, endDate);
        rootJSONArray.add(mainCountryJSON);
        return rootJSONArray;
    }

    /**
     *
     * @param mainCountryName
     * @param selectedCountriesList
     * @param startDate
     * @param endDate
     * @return
     */
    public static JsonArray buildLocationJSON(String mainCountryName, JList<String> selectedCountriesList, Date startDate, Date endDate) {
        ListModel<String> model = selectedCountriesList.getModel();
        JsonArray rootJSONArray = new JsonArray();
        JsonArray mainCountryJSON = buildCountryJSONArray(mainCountryName, startDate, endDate);
        rootJSONArray.add(mainCountryJSON);
        for (int i = 0; i < model.getSize(); i++) {
            String countryName = model.getElementAt(i);
            JsonArray countryJSON = buildCountryJSONArray(countryName, startDate, endDate);
            rootJSONArray.add(countryJSON);
        }
        return rootJSONArray;
    }

    /**
     *
     * @param countryName
     * @param startDate
     * @param endDate
     * @return
     */
    private static JsonArray buildCountryJSONArray(String countryName, Date startDate, Date endDate) {
        Country mainCountry = AppQueries.fetchCountryByName(countryName);
        Integer deathCases = AppQueries.fetchSumCoviddata(mainCountry, Constants.DATA_KIND_DEATHS, startDate, endDate);
        Integer confirmedCases = AppQueries.fetchSumCoviddata(mainCountry, Constants.DATA_KIND_CONFIRMED, startDate, endDate);
        Integer recoveredCases = AppQueries.fetchSumCoviddata(mainCountry, Constants.DATA_KIND_RECOVERED, startDate, endDate);
        JsonArray mainCountryJSONArray = new JsonArray();
        mainCountryJSONArray.add(mainCountry.getName()
                + " deathCases:" + deathCases.toString()
                + " confirmedCases:" + confirmedCases.toString()
                + " recoveredCases:" + recoveredCases.toString()
        );
        mainCountryJSONArray.add(mainCountry.getLat());
        mainCountryJSONArray.add(mainCountry.getLong1());
        mainCountryJSONArray.add(1);
        return mainCountryJSONArray;
    }

    /**
     *
     * @param HTML
     * @param locationArray
     * @return
     * @throws IOException
     */
    public static Document editHTMLScript(File HTML, JsonArray locationArray) throws IOException {
        Document doc = Jsoup.parse(HTML, "UTF-8");
        Elements scriptElements = doc.getElementsByTag("script");

        scriptElements.stream().map((element) -> {
            element.dataNodes().stream().map((node) -> {
                String script;
                script = "var locations = " + locationArray.toString();
                script += node.getWholeData();
                node.setWholeData(script);
                return node;
            }).forEachOrdered((node) -> {
                System.out.println(node.getWholeData());
            });
            return element;
        }).forEachOrdered((_item) -> {
            System.out.println("-------------------");
        });
        return doc;
    }

    /**
     *
     * @param path
     */
    public static void showHTML(String path) {
        try {
            // FOR WINDOWS
            Runtime.getRuntime().exec("cmd /c start " + path);
            // FOR MAC OS
            //Runtime.getRuntime().exec(new String[]{"/usr/bin/open", "-a", "/Applications/Google Chrome.app", webpage});
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param path
     * @return
     */
    public static File openFile(String path) {
        File input;
        input = new File(path);
        return input;
    }

    /**
     *
     * @param path
     * @param HTML
     */
    public static void saveFile(String path, String HTML) {
        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(HTML);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
