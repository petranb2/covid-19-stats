/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
* Τμήμα ΗΛΕ 46
* @author Κουλιανός Πέτρος 119076
* @author Κρουκλόβα Όλγα 94996
* @author Μάλαμας Σάββας 119131
*/
public class AppDatabase {

    private static final String COVID_DB = "covid-db";

    public static EntityManager getAppEntityManager() {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory(COVID_DB);
        return emf.createEntityManager();
    }
}
