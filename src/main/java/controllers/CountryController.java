/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import models.Country;
import models.Coviddata;
import service.AppQueries;
import utils.Constants;

/**
 *
 * @author kalogeros
 */
public class CountryController {

    /**
     *
     * @return 
     */
    public static String[] getCountryNames() {
        List<Country> countries = AppQueries.fetchCountries();
        String[] names = new String[countries.size()];
        
        for (int i = 0; i < names.length; i++) {
            names[i] = countries.get(i).getName();
        }
        return names;
    }

    /**
     *
     * @param deathsTable
     * @param confirmedTable
     * @param recoveredTable
     * @param countryName
     * @param fromDate
     * @param toDate
     */
    public static void fillTables(JTable deathsTable, JTable confirmedTable, JTable recoveredTable, String countryName, Date fromDate, Date toDate) {
        Country country = AppQueries.fetchCountryByName(countryName);
        renderTableData(deathsTable, country, Constants.DATA_KIND_DEATHS, fromDate, toDate);
        renderTableData(confirmedTable, country, Constants.DATA_KIND_CONFIRMED, fromDate, toDate);
        renderTableData(recoveredTable, country, Constants.DATA_KIND_RECOVERED, fromDate, toDate);

    }

    /**
     *
     * @param table
     * @param country
     * @param dataKind
     */
    private static void renderTableData(JTable table, Country country, int dataKind, Date fromDate, Date toDate) {
        List<Coviddata> fetchedCoviddata = AppQueries.fetchCoviddata(country, dataKind, fromDate, toDate);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (Coviddata coviddata : fetchedCoviddata) {
            model.addRow(new Object[]{coviddata.getDatakind(), coviddata.getQty(), coviddata.getProodqty(), coviddata.getTrndate().toString()});

        }
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
