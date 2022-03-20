package fr.univrennes1.istic.wikipediamatrix.Extractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HTMLExtractor implements Extractor {

    @Override
    public Document getDocumentFromURL(String url) throws Exception {
        // Initialisation
        Document doc = null;

        // Récupération des données
        doc = Jsoup.connect(url).get();
        return doc;
    }

    @Override
    public Elements getTablesFromDoc(Document doc) {
        // Sélection des tables
        Elements tables = doc.select(".wikitable");

        return tables;
    }
    
}
