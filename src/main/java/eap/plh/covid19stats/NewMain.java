/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eap.plh.covid19stats;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import models.Country;
import view.ArxikoMenu;

/**
 *
 * @author kalogeros
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // TODO code application logic here
        EntityManager em; // δημιουργώ μια μεταβλητή entity manager
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("covid-db");
        em = emf.createEntityManager();
        Country country = new Country();
        //country.setCountry(5);
        country.setLat(2.0);
        country.setLong1(2.0);
        country.setName("petros");
        em.getTransaction().begin();
        em.persist(country);
        em.getTransaction().commit();
        
        ArxikoMenu mainFrame = new ArxikoMenu();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

}
