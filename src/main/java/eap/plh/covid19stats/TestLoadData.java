/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eap.plh.covid19stats;

import service.DeleteCovidData;
import service.LoadCovidData;
import utils.Constants;

/**
 *
 * @author kalogeros
 */
public class TestLoadData {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        LoadCovidData loadData = new LoadCovidData(Constants.DEATHS, Constants.CONFIRMED, Constants.RECOVERED, Constants.COUTRIES_ONLY);
        loadData.startLoadData();
        DeleteCovidData deleteCovidData = new DeleteCovidData();
        deleteCovidData.truncateCovidData();
        deleteCovidData.truncateCountries();

    }

}
