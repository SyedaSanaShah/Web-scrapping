package webscrapping;
import com.opencsv.CSVReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CSVReadBasedMethods {

    //Method  to get 10 story headlines----->
    public static void getStoryHeadLines(String url, String label) {
        try {
            Document doc = Jsoup.connect(url).timeout(6000).get();
            Elements div = doc.select("ol.e713knt2");
            for (Element e : div.select("li")) {
                Elements tt = e.select("div.bbc-1k5dca0");
                String text = tt.select("a.bbc-1ydn2hb").text();
                Assignment_WebScrapping.linksHolder.add(label + "," + text);
            }
        } catch (IOException ex) {
            Logger.getLogger(Assignment_WebScrapping.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Method to transfer data from ArrayList to CSV file---->
    public static void storeStoryContentIntoCSVFile(String filePath) {
        FileWriter file = null;
        try {
            file = new FileWriter(filePath);
            BufferedWriter writer = new BufferedWriter(file);
            for (int j = 0; Assignment_WebScrapping.dataHolder.size() > j; j++) {
                writer.append(Assignment_WebScrapping.dataHolder.get(j));
            }
            writer.close();
            file.close();
        } catch (IOException ex) {
            Logger.getLogger(Assignment_WebScrapping.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(Assignment_WebScrapping.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Method to count unique words in CSV- Dataset--->
    public static int readCSVFileToCountUniqueWords(String filePath) {
        try {
            Scanner scan = new Scanner(new File(filePath));
            scan.useDelimiter(" ");
            while (scan.hasNext()) {
                Assignment_WebScrapping.counter.add(scan.next());
            }
            scan.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Assignment_WebScrapping.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return Assignment_WebScrapping.counter.size();
    }

    //Method to count no: of stories for each category in CSV- Dataset--->
    public static int[] countNoOfStoriesForEachCategory(String filePath) {
        try {
            Scanner scan = new Scanner(new File(filePath));
            scan.useDelimiter(",");
            while (scan.hasNext()) {
                String s=scan.next();  
                if (s.contains("Pakistan")) {
                    Assignment_WebScrapping.count1++;
                } else if (s.contains("AasPas")) {
                    Assignment_WebScrapping.count2++;
                } else if (s.contains("World")) {
                    Assignment_WebScrapping.count3++;
                } else if (s.contains("Khail")) {
                    Assignment_WebScrapping.count4++;
                } else if (s.contains("FunFunkar")) {
                    Assignment_WebScrapping.count5++;
                }
            }
            scan.close();
        } catch (IOException ex) {
            Logger.getLogger(Assignment_WebScrapping.class.getName()).log(Level.SEVERE, null, ex);
        }
        int[] array = {Assignment_WebScrapping.count1,
            Assignment_WebScrapping.count2,
            Assignment_WebScrapping.count3,
            Assignment_WebScrapping.count4,
            Assignment_WebScrapping.count5};
        return array;
    }

    //Method to get maximum length sory--->
    public static int getMaximumLengthStory(String filePath) {
        int maxLengthStory = 0;
        try {
            Scanner scan = new Scanner(new File(filePath));
            scan.useDelimiter(",");
            while (scan.hasNext()) {
                String s = scan.next();
                int count = s.split("[ \n\t\r.,;:!?(){}]").length;
                if (count > maxLengthStory) {
                    maxLengthStory = count;
                }
            }
            scan.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Assignment_WebScrapping.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return maxLengthStory;
    }

    
    //Method to find frequency of words from CSV-file--->
    public static void topWordsWithHighFrequency(String filePath) {
        try {
            Scanner scan = new Scanner(new File(filePath));
            scan.useDelimiter(",");
            while (scan.hasNext()) {
                String s = scan.next();
                String str[]=s.split(" ");
                for(int i=0; str.length-1>i; i++){
                    if(Assignment_WebScrapping.hashmap.containsKey(str[i])){
                        Assignment_WebScrapping.hashmap.put(str[i],Assignment_WebScrapping.hashmap.get(str[i])+1);
                    }
                    else{
                        Assignment_WebScrapping.hashmap.put(str[i],1);
                    }
                }
            }
            scan.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Assignment_WebScrapping.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
