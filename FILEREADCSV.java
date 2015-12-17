import javax.swing.*;
import java.io.*;                   // for general file handling
public class FILEREADCSV
{
    // file object to open/create, write and close a file
    private FileReader fReader;   // an object to fetch data from file

    // (use default constructor)

    public String[] readCSVtable() throws IOException {

        // Create a file chooser in the current project directory
        File currentDir = new File("").getAbsoluteFile();
        final JFileChooser fc = new JFileChooser(currentDir);
        // display file chooser dialog for user:
        int returnVal = fc.showOpenDialog(null);
        // open file chosen
        File csvFile = fc.getSelectedFile();

            // prepare large char array for file contents 5,000 characters
        char[] inBuffer = new char[5000];

        // open the file for reading
        fReader = new FileReader(csvFile);
        // read ALL characters from the file, store how many characters
        int size = fReader.read(inBuffer); 
        // close the file
        fReader.close();

        // convert char array to a string (easier to split), leave empty elements
        String fileContent = String.valueOf(inBuffer).substring(0,size);

        // split into rows, store each row in an array element (\n is newline)
        return fileContent.split("\n");
    }
}