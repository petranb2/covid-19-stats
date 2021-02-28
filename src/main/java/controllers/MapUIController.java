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
import java.util.Date;
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
import utils.Map;

/**
 *
 * @author kalogeros
 */
public class MapUIController {

    /**
     *
     * @param mainCountryName
     * @param selectedCountriesList
     * @param startDate
     * @param endDate
     * @throws IOException
     */
    public static void showMap(String mainCountryName, JList<String> selectedCountriesList, Date startDate, Date endDate) throws IOException {
        JsonArray rootJSONArray;
        rootJSONArray = Map.buildLocationJSON(mainCountryName, selectedCountriesList, startDate, endDate);
        File templateHTML;
        templateHTML = Map.openFile(Constants.TEMPLATE_MAP_HTML);
        Document HTMLDocument = Map.editHTMLScript(templateHTML, rootJSONArray);
        Map.saveFile(Constants.HTML_MAP_WITH_DATA, HTMLDocument.toString());
        Map.showHTML(Constants.HTML_MAP_WITH_DATA);
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
