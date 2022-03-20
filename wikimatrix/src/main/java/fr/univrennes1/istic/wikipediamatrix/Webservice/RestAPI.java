package fr.univrennes1.istic.wikipediamatrix.Webservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.univrennes1.istic.wikipediamatrix.App;
import fr.univrennes1.istic.wikipediamatrix.Extractor.Extractor;
import fr.univrennes1.istic.wikipediamatrix.Extractor.HTMLExtractor;
import fr.univrennes1.istic.wikipediamatrix.Parser.HTMLParser;
import fr.univrennes1.istic.wikipediamatrix.Parser.Parser;

@RestController
public class RestAPI {

    private final HashMap<String, String> content = new HashMap<String, String>();

    /* Récupération de toutes les tables à partir d'un nom de page wikipedia */
	@GetMapping("/get_tables_from_wikipedia")
	public Greeting greeting(@RequestParam(defaultValue = "") String page_name) {
        /* Vérifie si le nom de la page est fourni en entrée */
        if (page_name.equals("")) {
            return new Greeting(400, "Le format de la requête n'est pas valide !", content, null);
        } else {
            // Initialisation base URL
            String BASE_WIKIPEDIA_URL = "https://en.wikipedia.org/wiki/";
            
            try {
                // Initialisation
                String wurl = BASE_WIKIPEDIA_URL + page_name; 
                Extractor extractor = new HTMLExtractor();
                Document doc = null;

                // Récupération du document
                try  {
                    doc = extractor.getDocumentFromURL(wurl);
                } catch (IOException e) {
                    return new Greeting(404, "La page " + page_name + " ne semble pas exister !", content, null);
                }

                // Récupération de toutes les tables
                Elements tables = null;

                tables = extractor.getTablesFromDoc(doc);
                if (tables.isEmpty()) {
                    return new Greeting(200, "Aucune table n'a été trouvé !", content, null);
                }
                
                // Conversion Tables to List
                Parser convertor = new HTMLParser();
                List<String[][]> string_tables = convertor.elementsToTables(tables);
                return new Greeting(200, "Les tables ont étés correctement formatées !", content, string_tables);
            } catch (Exception e) {
                App.LOGGER.error(e.getMessage());
                return new Greeting(500, "Une erreure est survenue lors du traitement de la requête ! Veuillez renter ultèrieurement ou contacter un administrateur.", content, null);
            }


        }
	}
}
