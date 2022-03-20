package fr.univrennes1.istic.wikipediamatrix.Serializer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.NotImplementedException;

public abstract class Serializer {

    private Path folder_path;

    private final String pattern = "[\\~#%&*{}/:<>?|\"]";
    private final String replacement = "";

    public Serializer(String folder_path) {
        /* Obtention du path à partir du chemin */
        this.folder_path = Paths.get(folder_path);
    }

    public Path getFilePath(String file_name) {
        /* Suppression des caractères non valides */
        String correct_file_name = file_name.replaceAll(pattern, replacement);
        
        /* Récupération du chemin d'accès du projet */
        Path project_path = Paths.get("").toAbsolutePath();

        /* Construction du path du fichier */
        Path file_path = Paths.get(project_path.toString(), this.folder_path.toString(), correct_file_name);

        return file_path;
    }

    public void saveFile(String[][] string_table, String file_name) throws IOException{
		throw new NotImplementedException("saveFile : This method is not implemented yet !");
    }

    public String mkFileName(String url, int n) {
		throw new NotImplementedException("mkFileName : This method is not implemented yet !");
	}
    
}
