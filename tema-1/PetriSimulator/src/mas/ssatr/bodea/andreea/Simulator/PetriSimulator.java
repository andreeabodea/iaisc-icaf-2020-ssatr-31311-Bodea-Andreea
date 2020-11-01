package mas.ssatr.bodea.andreea.Simulator;

import mas.ssatr.bodea.andreea.Helpers.FileHelper;
import mas.ssatr.bodea.andreea.Helpers.PetriLoaderHelper;
import mas.ssatr.bodea.andreea.Models.Location;
import mas.ssatr.bodea.andreea.Models.PetriNet;
import mas.ssatr.bodea.andreea.Models.Transition;

import java.util.*;

public class PetriSimulator {
    private PetriLoaderHelper petriLoaderHelper;
    private FileHelper fileHelper;

    private PetriNet petriNet;
    private Map<String, Integer> inExecutionTransitions = new HashMap<String, Integer>();
    private boolean run = true;
    private int sysClock = 0;

    public PetriSimulator(PetriLoaderHelper petriLoaderHelper, FileHelper fileHelper) {
        this.petriLoaderHelper = petriLoaderHelper;
        this.fileHelper = fileHelper;
    }

    public void SimulateNet(){
        petriNet = petriLoaderHelper.Load();
        fileHelper.Initialize(petriNet);

        while(run){
            run = evaluateNet();
            sysClock++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean evaluateNet(){
        if(sysClock == 0){
            WritePetriNetExecutionStep();
            return true;
        }

        boolean isPetriNetExecutable = false;

        for (Transition transition : petriNet.getTransitions()) {

            if (petriNet.isTransitionExecutable(transition)) {
                isPetriNetExecutable = true;
                petriNet.ExecuteTransitionPrevLocationsUpdate(transition);

                inExecutionTransitions.put(transition.getName(), transition.GetExecutionTime());
            }
        }
        if(!isPetriNetExecutable){
            isPetriNetExecutable = inExecutionTransitions.size() > 0;
        }
        EvaluateInExecutionTransitions();
        WritePetriNetExecutionStep();
        return isPetriNetExecutable;
    }

    private void EvaluateInExecutionTransitions() {
        List<String> toRemoveKeys = new ArrayList<String>();

        for (Map.Entry<String, Integer> entry : inExecutionTransitions.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (value <= 1) {
                petriNet.ExecuteTransitionNextLocationsUpdate(petriNet.GetTransitionByName(key));
                toRemoveKeys.add(key);
            }
            inExecutionTransitions.put(key, inExecutionTransitions.get(key) -1);
        }
        for(String key : toRemoveKeys){
            inExecutionTransitions.remove(key);
        }
    }

    private void WritePetriNetExecutionStep() {
        System.out.println("SysTime: "+ sysClock);
        var stringToWrite = "Time: "+ sysClock +"  -";

        for (Location location : petriNet.getLocations()) {
            stringToWrite +=  " "+ location.getTokens() + " ";
            System.out.println("Location: " + location.getName() + " Tokens: " + location.getTokens());
        }
        fileHelper.Write(stringToWrite);

        System.out.println("--------------------------------------------------------------------------------------");
        for (Map.Entry<String, Integer> entry : inExecutionTransitions.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("Transition: "+key+ " RemainingTime: "+value);
        }
        System.out.println("--------------------------------------------------------------------------------------");
    }
}

