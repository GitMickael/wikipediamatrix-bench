package fr.univrennes1.istic.wikipediamatrix;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import fr.univrennes1.istic.wikipediamatrix.Extractor.Extractor;
import fr.univrennes1.istic.wikipediamatrix.Extractor.HTMLExtractor;
import fr.univrennes1.istic.wikipediamatrix.Parser.HTMLParser;
import fr.univrennes1.istic.wikipediamatrix.Parser.Parser;
import fr.univrennes1.istic.wikipediamatrix.Serializer.listToCSV;
import fr.univrennes1.istic.wikipediamatrix.Serializer.Serializer;

public class BenchTest {
	
	/*
	*  the challenge is to extract as many relevant tables as possible
	*  and save them into CSV files  
	*  from the 300+ Wikipedia URLs given
	*  see below for more details
	**/
	@Test
	public void testBenchExtractors() throws Exception {
		
		/******************/
		/* Initialisation */
		/*******************/
		// URL de base
		String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";

		// Folder de sortie
		String outputDirHtml = "output" + File.separator + "html" + File.separator;
		assertTrue(new File(outputDirHtml).isDirectory());
		
		// Fichier d'entrée
		File file = new File("inputdata" + File.separator + "wikiurls.txt");

		// Initialisation des variables
		BufferedReader br = new BufferedReader(new FileReader(file));
	    String url;
	    int nurl = 0;

		/************************/
		/* Boucle sur les pages */
		/************************/

	    while ((url = br.readLine()) != null) {
		   // Exception non prévue
		   try {
			/* Initialisation */
			String wurl = BASE_WIKIPEDIA_URL + url; 
			Extractor extractor = new HTMLExtractor();

			/* Récupération des data depuis un url */
			Document doc = null;
			try  {
				doc = extractor.getDocumentFromURL(wurl);

			} catch (IOException e) {
				String message = "Unable to connect to the URL: " + wurl;
				App.LOGGER.error(message);
				continue;
			}

			/* Extraction des tables depuis un document */
			
			// Initialisation
			Elements tables = null;

			tables = extractor.getTablesFromDoc(doc);

			// Vérification si aucune table trouvée
            if (tables.isEmpty()) {
				nurl++;
				continue;
			}

			/* Convertion des tables en List<String[][]> */
			Parser convertor = new HTMLParser();
			List<String[][]> string_tables = convertor.elementsToTables(tables);

			/* Sérialisation en CSV */
			// Initialisation
			Serializer serializer = new listToCSV("output/html");

			// Parcours de toutes les tables
			for (int i = 0; i<string_tables.size(); i++) {
				// Création du nom du fichier
				String csvFileName = serializer.mkFileName(url, i + 1);
				
				// Récupération de la table courante
				String[][] string_table = string_tables.get(i);
				
				// Sauvegarde du fichier.
				try {
					serializer.saveFile(string_table, csvFileName);
				} catch (IOException e) {
					App.LOGGER.error(e.getMessage());
					nurl--;
				}
			}
			
			/* Validation de l'intégréité des csv */
			try {
				/* Parcourssur toutes les tables */
				for (int i = 0; i<string_tables.size(); i++) {
					serializer = new listToCSV("output/html");
					String csvFileName = serializer.mkFileName(url, i + 1);
					Path csv_path = serializer.getFilePath(csvFileName);
					Reader in = new FileReader(csv_path.toString());
					Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
				}
			} catch (IOException e) {
				App.LOGGER.error(e.getMessage());
				continue;
			}
			
			nurl++;
		   } catch (Exception e) {
			   App.LOGGER.error("Exception non gérée : "+e.getMessage());
		   }
	    }
	    
	    br.close();

		/* Vérification que l'intégralité des fichier est bien passé */
	    assertEquals(nurl, 302);
		
		/* Check pour le fichier https://en.wikipedia.org/wiki/Comparison_of_digital_SLRs */
		/* Nous le faison ici car nous avons essayé d'ordonner les tests sans succès :(
		Ainsi, même si on estime que ce n'est pas une bonne pratique, on a choisit cette alternative
		pour éviter de créer des réplications de code */

		// Initialisation des variables
		int row_counter = 0;
        int column_counter = 0;

		// Lecture du fichier et compte des lignes
        try {
			// Initialisation et lecture du fichier
            Serializer serializer = new listToCSV("output/html");
            Path csv_path = serializer.getFilePath("Comparison_of_digital_SLRs-1.csv");
            Reader in = new FileReader(csv_path.toString());
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
			
			// Parcours des lignes pour compter lignes et colonnes.
            for (CSVRecord record : records) {
                if (record.size() != column_counter) {
                    assertEquals(column_counter, 0);
                    column_counter = record.size();
                }
                row_counter++;
            }
        } catch (IOException e) {
            App.LOGGER.error(e.getMessage());
            assertTrue(false);
        }

        assertEquals(row_counter, 74);
        assertEquals(column_counter, 22);
	}

}
