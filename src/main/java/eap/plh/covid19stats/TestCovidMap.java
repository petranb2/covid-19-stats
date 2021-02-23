/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eap.plh.covid19stats;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author kalogeros
 */
public class TestCovidMap {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        File input;
        input = new File("src\\main\\resources\\CovidMap.html");
        Document doc = Jsoup.parse(input, "UTF-8");
        Elements scriptElements = doc.getElementsByTag("script");

        for (Element element : scriptElements) {
            for (DataNode node : element.dataNodes()) {
                String script;
                script = "var locations = [\n"
                        + "['Greece, κρούσματα=123, διάφορα=256', 39.0742, 21.8243, 1],\n"
                        + "['Georgia', 42.3154, 43.3569, 1],\n"
                        + "['France',46.2276,2.2137,1]\n"
                        + "];";
                script += node.getWholeData();
                node.setWholeData(script);
                System.out.println(node.getWholeData());

            }
            System.out.println("-------------------");
        }
        try {
            FileWriter myWriter = new FileWriter("src\\main\\resources\\CovidMap-data.html");
            myWriter.write(doc.html());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        // TODO code application logic here
        String webpage = "src\\main\\resources\\CovidMap-data.html";
        try {
            Runtime.getRuntime().exec("cmd /c start " + webpage);
        } catch (IOException ex) {
            Logger.getLogger(TestCovidMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
