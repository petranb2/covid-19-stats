/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import models.Country;
import models.Coviddata;

/**
 *
 * @author kalogeros
 */
public class AppQueries {

    /**
     *
     * @param countryName
     * @return
     */
    public static Country fetchCountryByName(String countryName) {
        EntityManager em = AppDatabase.getAppEntityManager();
        Query namedQuery = em.createNamedQuery("Country.findByName");
        namedQuery.setParameter("name", countryName);
        Country fetchedCountry = (Country) namedQuery.getSingleResult();
        return fetchedCountry;
    }

    /**
     *
     * @return
     */
    public static List<Country> fetchCountries() {
        EntityManager em = AppDatabase.getAppEntityManager();
        Query namedQuery = em.createNamedQuery("Country.findAll");
        List<Country> fetchedCountries = (List<Country>) namedQuery.getResultList();
        return fetchedCountries;
    }

    /**
     *
     * @return
     */
    public static List<Country> fetchCountriesExclude(String countryName) {
        EntityManager em = AppDatabase.getAppEntityManager();
        Query namedQuery = em.createNamedQuery("Country.excludeCountry");
        namedQuery.setParameter("name", countryName);
        List<Country> fetchedCountries = (List<Country>) namedQuery.getResultList();
        return fetchedCountries;
    }

    /**
     *
     * @param country
     * @return
     */
    public static Coviddata fetchCoviddata(Country country) {
        EntityManager em = AppDatabase.getAppEntityManager();
        Query namedQuery = em.createNamedQuery("Coviddata.findByCountry");
        namedQuery.setParameter("country", country);
        Coviddata fetchedCoviddata = (Coviddata) namedQuery.getSingleResult();
        return fetchedCoviddata;
    }

    /**
     *
     * @param country
     * @param dataKind
     * @return
     */
    public static List<Coviddata> fetchCoviddata(Country country, int dataKind) {
        EntityManager em = AppDatabase.getAppEntityManager();
        Query namedQuery2 = em.createNamedQuery("Coviddata.findByCountryAndDataKind");
        namedQuery2.setParameter("country", country);
        namedQuery2.setParameter("datakind", dataKind);
        List<Coviddata> fetchedCoviddata = (List<Coviddata>) namedQuery2.getResultList();
        return fetchedCoviddata;
    }

    /**
     *
     * @param country
     * @param dataKind
     * @param fromDate
     * @param toDate
     * @return
     */
    public static List<Coviddata> fetchCoviddata(Country country, int dataKind, Date fromDate, Date toDate) {
        EntityManager em = AppDatabase.getAppEntityManager();
        Query namedQuery2 = em.createNamedQuery("Coviddata.findByCountryAndDataKindByDateRange");
        namedQuery2.setParameter("country", country);
        namedQuery2.setParameter("datakind", dataKind);
        namedQuery2.setParameter("fromDate", fromDate);
        namedQuery2.setParameter("toDate", toDate);
        List<Coviddata> fetchedCoviddata = (List<Coviddata>) namedQuery2.getResultList();
        return fetchedCoviddata;
    }

    /**
     *
     * @param country
     * @param dataKind
     * @return
     */
    public static Integer fetchSumCoviddata(Country country, int dataKind) {
        EntityManager em = AppDatabase.getAppEntityManager();
        Query namedQuery2 = em.createNativeQuery("SELECT SUM(cd.QTY) as total  FROM ROOT.COVIDDATA cd where cd.COUNTRY=? and cd.DATAKIND=?");
        namedQuery2.setParameter(1, country.getCountry());
        namedQuery2.setParameter(2, dataKind);
        Integer totalCases = (Integer) namedQuery2.getSingleResult();
        if (totalCases == null) {
            return -1;
        }
        return totalCases;
    }
}
