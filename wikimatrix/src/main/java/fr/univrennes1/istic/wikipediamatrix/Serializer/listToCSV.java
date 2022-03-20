package fr.univrennes1.istic.wikipediamatrix.Serializer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import fr.univrennes1.istic.wikipediamatrix.App;

public class listToCSV extends Serializer{

    public listToCSV(String save_path) {
        super(save_path);
    }

    @Override
    public void saveFile(String[][] string_table, String file_name) throws IOException{
        /* Initialisation */
        Path csv_path = this.getFilePath(file_name);

        /* Parcours des lignes du tableau */
        FileWriter writer = null;
        try {
            writer = new FileWriter(csv_path.toString());
            for (int i = 0; i < string_table.length; i++) {
                int index = 0;
                /* Parcours des colonnes du tableau */
                for (String string : string_table[i]) {
                        String text = string;
                        if (text == null) {
                            text = "";
                        }
                        text.replaceAll("\"", "\"\"");
                        text = "\"" + text + "\"";
                        writer.append(text);
                        if(index != string_table[i].length -1) {
                            writer.append(',');
                        };
                        index++;
                }
                if (i < string_table.length - 1) {
                    writer.append('\n');
                }
            }
            writer.close();
        } catch (IOException e) {
                writer.close();
                App.LOGGER.error(e.getMessage());
                return;
        }
    }

    @Override
    public String mkFileName(String url, int n) {
        return url.trim() + "-" + n + ".csv";
    }
    
}
