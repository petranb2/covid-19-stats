/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.Choice;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.Country;
import models.Coviddata;
import service.AppDatabase;
import service.AppQueries;
import service.LoadCovidData;
import utils.Constants;

/**
 *
 * @author kalogeros
 */
public class CountryController {

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
     * @param deathsTable
     * @param confirmedTable
     * @param recoveredTable
     * @param countryName
     */
    public static void fillTables(JTable deathsTable, JTable confirmedTable, JTable recoveredTable, String countryName) {
        Country country = AppQueries.fetchCountryByName(countryName);
        renderTableData(deathsTable, country, Constants.DATA_KIND_DEATHS);
        renderTableData(confirmedTable, country, Constants.DATA_KIND_CONFIRMED);
        renderTableData(recoveredTable, country, Constants.DATA_KIND_RECOVERED);

    }

    /**
     *
     * @param deathsTable
     * @param confirmedTable
     * @param recoveredTable
     */
    public static void clearTables(JTable deathsTable, JTable confirmedTable, JTable recoveredTable) {
        clearTable(deathsTable);
        clearTable(confirmedTable);
        clearTable(recoveredTable);
    }

    /**
     *
     * @param table
     * @param country
     * @param dataKind
     */
    private static void renderTableData(JTable table, Country country, int dataKind) {
        List<Coviddata> fetchedCoviddata = AppQueries.fetchCoviddata(country, dataKind);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (Coviddata coviddata : fetchedCoviddata) {
            model.addRow(new Object[]{coviddata.getDatakind(), coviddata.getQty(), coviddata.getProodqty(), coviddata.getTrndate().toString()});

        }
    }

    /**
     *
     * @param table
     */
    private static void clearTable(JTable table) {
        table.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Data Kind", "QTY", "PROODQTY", "TRNDATE"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });

    }
}
