/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import eap.plh.covid19stats.TestCovidMap;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kalogeros
 */
public class Map {

    public static void showHTML(String path) {
        try {
            Runtime.getRuntime().exec("cmd /c start " + path);
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static File openFile(String path) {
        File input;
        input = new File(path);
        return input;
    }

    public static void saveFile(String path, String HTML) {
        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(HTML);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
