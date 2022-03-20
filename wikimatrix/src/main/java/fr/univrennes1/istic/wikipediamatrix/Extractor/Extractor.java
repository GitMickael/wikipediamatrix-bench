package fr.univrennes1.istic.wikipediamatrix.Extractor;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public interface Extractor {
    Document getDocumentFromURL(String url) throws Exception;

    Elements getTablesFromDoc(Document doc);
}
