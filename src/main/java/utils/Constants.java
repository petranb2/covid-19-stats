/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author kalogeros
 */
public class Constants {

    public static final String COVID_DB = "covid-db";
    public static final boolean DEATHS = true;
    public static final boolean RECOVERED = true;
    public static final boolean CONFIRMED = true;
    public static final boolean COUTRIES_ONLY = true;
    public static final short DATA_KIND_DEATHS = 1;
    public static final short DATA_KIND_RECOVERED = 2;
    public static final short DATA_KIND_CONFIRMED = 3;
    public static final String PROPERTY_JSON_DEATHS = "deaths";
    public static final String PROPERTY_JSON_RECOVERED = "recovered";
    public static final String PROPERTY_JSON_CONFIRMED = "confirmed";
    public static final String CONFIRMED_URL = "https://covid2019-api.herokuapp.com/timeseries/confirmed";
    public static final String DEATHS_URL = "https://covid2019-api.herokuapp.com/timeseries/deaths";
    public static final String RECOVERED_URL = "https://covid2019-api.herokuapp.com/timeseries/recovered";
    public static final String TEMPLATE_MAP_HTML = "src\\main\\resources\\CovidMap.html";
    public static final String HTML_MAP_WITH_DATA = "src\\main\\resources\\CovidMap-data.html";
}
