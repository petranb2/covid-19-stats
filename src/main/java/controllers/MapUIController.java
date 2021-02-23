/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.gson.JsonArray;
import eap.plh.covid19stats.TestCovidMap;
import java.awt.Choice;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;
import models.Country;
import models.Coviddata;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.AppQueries;
import utils.Constants;

/**
 *
 * @author kalogeros
 */
public class MapUIController {

    /**
     *
     * @param selectedCountriesList
     */
    public static void showMap(String mainCountryName, JList<String> selectedCountriesList) throws IOException {
        ListModel<String> model = selectedCountriesList.getModel();
        JsonArray rootJSONArray = new JsonArray();
        // add main country to the root JSON array
        Country mainCountry = AppQueries.fetchCountryByName(mainCountryName);
        Integer deathCases = AppQueries.fetchSumCoviddata(mainCountry, Constants.DATA_KIND_DEATHS);
        Integer confirmedCases = AppQueries.fetchSumCoviddata(mainCountry, Constants.DATA_KIND_CONFIRMED);
        Integer recoveredCases = AppQueries.fetchSumCoviddata(mainCountry, Constants.DATA_KIND_RECOVERED);
        JsonArray mainCountryJSONArray = new JsonArray();
        mainCountryJSONArray.add(mainCountry.getName()
                + " deathCases:" + deathCases.toString()
                + " confirmedCases:" + confirmedCases.toString()
                + " recoveredCases:" + recoveredCases.toString()
        );
        mainCountryJSONArray.add(mainCountry.getLat());
        mainCountryJSONArray.add(mainCountry.getLong1());
        mainCountryJSONArray.add(1);
        rootJSONArray.add(mainCountryJSONArray);
        //
        for (int i = 0; i < model.getSize(); i++) {
            String countryName = model.getElementAt(i);
            Country country = AppQueries.fetchCountryByName(countryName);
            deathCases = AppQueries.fetchSumCoviddata(country, Constants.DATA_KIND_DEATHS);
            confirmedCases = AppQueries.fetchSumCoviddata(country, Constants.DATA_KIND_CONFIRMED);
            recoveredCases = AppQueries.fetchSumCoviddata(country, Constants.DATA_KIND_RECOVERED);
            JsonArray jsonArray = new JsonArray();
            jsonArray.add(country.getName()
                    + " deathCases:" + deathCases.toString()
                    + " confirmedCases:" + confirmedCases.toString()
                    + " recoveredCases:" + recoveredCases.toString()
            );
            jsonArray.add(country.getLat());
            jsonArray.add(country.getLong1());
            jsonArray.add(1);
            rootJSONArray.add(jsonArray);
        }
        rootJSONArray.toString();
        System.out.println(rootJSONArray.toString());
        File input;
        input = new File("src\\main\\resources\\CovidMap.html");
        Document doc = Jsoup.parse(input, "UTF-8");
        Elements scriptElements = doc.getElementsByTag("script");

        scriptElements.stream().map((element) -> {
            element.dataNodes().stream().map((node) -> {
                String script;
                script = "var locations = " + rootJSONArray.toString();
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
        try {
            FileWriter myWriter = new FileWriter("src\\main\\resources\\CovidMap-data.html");
            myWriter.write(doc.html());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        // TODO code application logic here
        String webpage = "src\\main\\resources\\CovidMap-data.html";
        try {
            Runtime.getRuntime().exec("cmd /c start " + webpage);
        } catch (IOException ex) {
            Logger.getLogger(TestCovidMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param dropDown
     */
    public static void fillDropDown(Choice dropDown) {
        List<Country> fetchedCountry = AppQueries.fetchCountries();
        dropDown.add("");
        fetchedCountry.forEach((country) -> {
            dropDown.add(country.getName());
        });
    }

    /**
     *
     * @param list
     */
    public static void empltyList(JList<String> list) {
        DefaultListModel listModel = new DefaultListModel();
        list.setModel(listModel);
    }

    /**
     *
     * @param list
     */
    public static void removeSelectedItemsFromList(JList<String> list) {
        ListModel<String> model = list.getModel();
        DefaultListModel listModel = new DefaultListModel();
        for (int i = 0; i < model.getSize(); i++) {
            if (!list.getSelectedValuesList().contains(model.getElementAt(i))) {
                listModel.addElement(model.getElementAt(i));
            }
        }
        list.setModel(listModel);
    }

    /**
     *
     * @param countryName
     * @param countriesList
     */
    public static void fillCountriesList(String countryName, JList<String> countriesList) {
        DefaultListModel listModel = new DefaultListModel();
        List<Country> countries = AppQueries.fetchCountriesExclude(countryName);
        countries.forEach((country) -> {
            listModel.addElement(country.getName());
        });
        countriesList.setModel(listModel);
    }

    public static void addCountriesToSelectedList() {

    }

    public static void removeCountriesToSelectedList() {

    }
}
