package fr.univrennes1.istic.wikipediamatrix.Parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParser implements Parser {

    @Override
    public String[][] elementToTable(Element table) {
        /* Initialisation */
        List<List<String>> table_list = new ArrayList<List<String>>();
        int columns_number = 0;
        Elements elements_rows = table.select("tr");

        /* Parcours de toutes les lignes du tableau */
        for (Element element_row : elements_rows) {

            /* On récupère les cellules th et td */
            Elements cells = element_row.select("th,td");

            List<String> columns_list = new ArrayList<String>();

            /* Récupération du contenu de chaque colonne */
            for (Element cell : cells) {
                columns_list.add(cell.text());
            }
            
            table_list.add(columns_list);

            if (columns_list.size() > columns_number) {
                columns_number = columns_list.size();
            }
        }

        /* Initialisation du nombre de lignes et de colonnes */
        int rows_number = table_list.size();
        
        String[][] table_output = new String[rows_number][columns_number];
        
        for (int i = 0; i < rows_number; i++) {
            List<String> row = table_list.get(i);
            for (int j = 0; j< row.size(); j++) {
                table_output[i][j] = row.get(j);
            }
        }

        return table_output;
    }

    @Override
    public ArrayList<String[][]> elementsToTables(Elements tables) {
        ArrayList<String[][]> table_outputs = new ArrayList<String[][]>();

        /* Parcours de toutes les tables */
        for (Element table : tables) {
            String[][] table_list = this.elementToTable(table);
            table_outputs.add(table_list);
        }
        return table_outputs;
    }
    
}
