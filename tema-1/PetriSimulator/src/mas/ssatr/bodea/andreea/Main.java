package mas.ssatr.bodea.andreea;

import mas.ssatr.bodea.andreea.Helpers.FileHelper;
import mas.ssatr.bodea.andreea.Helpers.PetriLoaderHelper;
import mas.ssatr.bodea.andreea.Simulator.PetriSimulator;

public class Main {

    public static void main(String[] args) {
        PetriLoaderHelper loaderHelper = new PetriLoaderHelper();
        FileHelper fileHelper = new FileHelper();

        PetriSimulator petriSimulator = new PetriSimulator(loaderHelper, fileHelper);
        petriSimulator.SimulateNet();
        //deadlock
        //bucla infinita
        //nu mai exista tranzii executabile-end

    }
}
