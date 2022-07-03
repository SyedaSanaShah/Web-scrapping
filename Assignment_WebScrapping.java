package webscrapping;

import com.opencsv.CSVReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Assignment_WebScrapping {

    static int count1 = 0;
    static int count2 = 0;
    static int count3 = 0;
    static int count4 = 0;
    static int count5 = 0;
    static ArrayList<String> dataHolder = null;
    private static String linksClassName = "bbc-uk8dsi";
    private static String divParaClassName = "bbc-4wucq3";
    static HashSet<String> counter = new HashSet<String>();
    static ArrayList<String> linksHolder = new ArrayList<String>();
    static ArrayList<String> categoriesList = new ArrayList<String>();
    static HashMap<String, Integer> hashmap = new HashMap<String, Integer>();
    static ArrayList<String> categoriesNames = new ArrayList<String>() {
        {
            add("Pakistan");
            add("AasPas");
            add("World");
            add("Khail");
            add("FunFunkar");
        }
    };

    // visit categories= Pakistan, AasPas, World, Khail and FunFankaar---->
    public static void visitCategories(Document doc) {
        int i = 0;
        Elements body = doc.select("div.bbc-1oql3dm");
        for (Element e : body.select("li")) {
            String link = e.select("a.bbc-puhg0e").attr("href");
            if (i != 0) {
                categoriesList.add("https://www.bbc.com" + link);
            }
            if (i == 5) {
                break;
            }
            i++;
        }
    }

    //Method to print ArrayList data--->
    public static void getLinksArrayList(ArrayList list) {
        for (int i = 0; list.size() > i; i++) {
            System.out.println(list.get(i));
        }
    }

    //Method to print HashMap data--->
    public static void getLinksHashMap(HashMap<String, Integer> list) {
       Iterator<String> itr = hashmap.keySet().iterator();
       int i=-1;
        while (itr.hasNext()) {
            System.out.println(itr.next());
            i++;
            if(i==10)break;
        }
    }

    public static void main(String[] args) throws IOException {
        CSVReadBasedMethods obj = new CSVReadBasedMethods();
        String url = "https://www.bbc.com/urdu";
        dataHolder = new ArrayList<String>();
        String filePath = "E:\\BSCS_III\\Data_Structures_And_Algorithms\\Assignments\\webscrapping-urdu.csv";
        Document doc = Jsoup.connect(url).timeout(6000).get();

        visitCategories(doc);
        int count = 0;
        for (int i = 0; categoriesList.size() > i; i++) {
            count = 0;
            int pageNumber = 1;
            String addPageNumber = "";
            for (int j = 0; j == 0 && count != 100;) {
                Document c = Jsoup.connect(categoriesList.get(i) + addPageNumber).timeout(6000).get();
                Elements links = c.getElementsByClass(linksClassName);
                for (Element e : links) {
                    count++;
                    String str = e.attr("href");
                    Document ds = Jsoup.connect(str).timeout(6000).get();
                    Elements d = ds.getElementsByClass(divParaClassName);
                    String text = d.text();
                    dataHolder.add(categoriesNames.get(i) + "," + text);
                    if (count == 100) {
                        //System.out.println("Story reached 100--->");
                        break;
                    }
                }
                pageNumber++;
                addPageNumber = "?page=";
                addPageNumber = addPageNumber + pageNumber;
            }
        }

        //store data  into CSV file---->
        obj.storeStoryContentIntoCSVFile(filePath);

        //Number of unique words---->
        int uniqueWords = obj.readCSVFileToCountUniqueWords(filePath);
        System.out.println("Number of unique words in CSV file are= " + uniqueWords);

        //Number of stories for each category--->
        int[] array = obj.countNoOfStoriesForEachCategory(filePath);
        for (int i = 0; categoriesNames.size() > i; i++) {
            System.out.println("Number of stories for " + categoriesNames.get(i) + " = " + array[i]);
        }

        //Maximum length story---->
        int maxLengthStory = obj.getMaximumLengthStory(filePath);
        System.out.println("Maximum story length= " + maxLengthStory);

        //story headlines---->
        obj.getStoryHeadLines("https://www.bbc.com/urdu/pakistan-61985207", "Headline");
        getLinksArrayList(linksHolder);

        //top  10  words in terms of frequency---->
        obj.topWordsWithHighFrequency(filePath);
        getLinksHashMap(hashmap);
    }
}
