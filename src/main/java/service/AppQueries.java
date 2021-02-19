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
     * @param trnDate
     * @return
     */
    public static List<Coviddata> fetchCoviddata(Country country, int dataKind, Date trnDate) {
        EntityManager em = AppDatabase.getAppEntityManager();
        Query namedQuery2 = em.createNamedQuery("Coviddata.findByCountryAndDataKindAndTrndate");
        namedQuery2.setParameter("datakind", dataKind);
        namedQuery2.setParameter("trndate", trnDate);
        namedQuery2.setParameter("country", country);
        List<Coviddata> fetchedCoviddata = (List<Coviddata>) namedQuery2.getResultList();
        return fetchedCoviddata;
    }
}
