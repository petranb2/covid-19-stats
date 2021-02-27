/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import models.Country;

/**
 *
 * @author kalogeros
 */
public class DeleteCovidData {

    /**
     *
     */
    public void truncateCovidData() {
        EntityManager em = AppDatabase.getAppEntityManager();
        em.getTransaction().begin();
        Query q = em.createNativeQuery("DELETE FROM ROOT.COVIDDATA");
        int executeUpdate = q.executeUpdate();
        em.getTransaction().commit();
        System.out.println("executeUpdate :" + executeUpdate);
    }

    /**
     *
     * @param country
     */
    public void truncateCovidData(Country country) {
        EntityManager em = AppDatabase.getAppEntityManager();
        em.getTransaction().begin();
        Query q = em.createNativeQuery("DELETE FROM ROOT.COVIDDATA WHERE ROOT.COVIDDATA.COUNTRY = ?");
        q.setParameter(1, country.getCountry());
        int executeUpdate = q.executeUpdate();
        em.getTransaction().commit();
        System.out.println("executeUpdate :" + executeUpdate);
    }

    /**
     *
     */
    public void truncateCountries() {
        truncateCovidData();
        EntityManager em = AppDatabase.getAppEntityManager();
        em.getTransaction().begin();
        Query q = em.createNativeQuery("DELETE FROM ROOT.COUNTRY");
        int executeUpdate = q.executeUpdate();
        em.getTransaction().commit();
        System.out.println("executeUpdate :" + executeUpdate);
    }

    /**
     *
     * @param country
     */
    public void truncateCountry(Country country) {
        truncateCovidData();
        EntityManager em = AppDatabase.getAppEntityManager();
        em.getTransaction().begin();
        Query q = em.createNativeQuery("DELETE FROM ROOT.COUNTRY WHERE ROOT.COUNTRY.COUNTRY = :countryPK");
        q.setParameter("countryPK", country.getCountry());
        int executeUpdate = q.executeUpdate();
        em.getTransaction().commit();
        System.out.println("executeUpdate :" + executeUpdate);
    }
}
