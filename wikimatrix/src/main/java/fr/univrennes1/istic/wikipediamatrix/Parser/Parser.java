package fr.univrennes1.istic.wikipediamatrix.Parser;

import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface Parser {
    
    String[][] elementToTable(Element table);
    
    List<String[][]> elementsToTables(Elements tables);
}
