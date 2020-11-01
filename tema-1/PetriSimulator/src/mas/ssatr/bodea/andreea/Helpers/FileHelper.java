package mas.ssatr.bodea.andreea.Helpers;

import mas.ssatr.bodea.andreea.Models.Location;
import mas.ssatr.bodea.andreea.Models.PetriNet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class FileHelper {

    public void Write(String text) {
        try {
            FileWriter fw = new FileWriter("results.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(text);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Initialize(PetriNet petriNet) {
        String stringToWrite = "          ";
        for (Location location : petriNet.getLocations()) {
            stringToWrite +=  " "+ location.getName();
        }
        Write(stringToWrite);
    }
}
