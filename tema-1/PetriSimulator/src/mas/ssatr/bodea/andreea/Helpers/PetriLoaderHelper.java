package mas.ssatr.bodea.andreea.Helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import mas.ssatr.bodea.andreea.Models.PetriNet;

import java.io.File;
import java.io.IOException;

public class PetriLoaderHelper {

    public PetriNet Load() {
        ObjectMapper mapper = new ObjectMapper();
        PetriNet petriNet = new PetriNet();
        try {
            String jsonFilePath = "C:\\Users\\aabod\\Desktop\\Facultate\\Master1\\iaisc-icaf-2020-ssatr-31311-Bodea-Andreea\\tema-1\\petri.json";
            petriNet = mapper.readValue(new File(jsonFilePath), PetriNet.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return petriNet;
    }

}
