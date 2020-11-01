package mas.ssatr.bodea.andreea.Models;

import java.util.List;

public class PetriNet {
    private List<Location> locations;
    private List<Transition> transitions;

    public PetriNet(List<Location> locations, List<Transition> transitions) {
        this.locations = locations;
        this.transitions = transitions;
    }

    public PetriNet() {
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public Transition GetTransitionByName(String name){
        return transitions.stream()
                .filter(transition -> name.equals(transition.getName()))
                .findAny()
                .orElse(null);
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

    public boolean isTransitionExecutable(Transition t) {
        int prevLocationsWithToken = 0;

        for (String prevLocation : t.getPrevLocations()) {

            Location prev = locations.stream()
                    .filter(location -> prevLocation.equals(location.getName()))
                    .findAny()
                    .orElse(null);

            if(prev != null){
                if(prev.getTokens()>0){
                    prevLocationsWithToken++;
                }
            }
        }
        //if all prev locations have tokens the transition is executable
        return prevLocationsWithToken == t.getPrevLocations().size();
    }

    public void ExecuteTransitionPrevLocationsUpdate(Transition t){
        for (String prevLocation : t.getPrevLocations()){
            Location prev = locations.stream()
                    .filter(location -> prevLocation.equals(location.getName()))
                    .findAny()
                    .orElse(null);
            if(prev != null) {
                prev.setTokens(prev.getTokens() - 1);
            }
        }
    }

    public void ExecuteTransitionNextLocationsUpdate(Transition t){
        for (String nextLocation : t.getNextLocations()){
            Location next = locations.stream()
                    .filter(location -> nextLocation.equals(location.getName()))
                    .findAny()
                    .orElse(null);
            if(next != null) {
                next.setTokens(next.getTokens() + 1);
            }
        }
    }
}
