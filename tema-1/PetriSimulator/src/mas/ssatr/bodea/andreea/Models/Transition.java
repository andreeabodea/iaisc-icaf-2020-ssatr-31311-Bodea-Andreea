package mas.ssatr.bodea.andreea.Models;

import java.util.List;

public class Transition {
    private String name;
    private int minTime;
    private int maxTime;
    private List<String> nextLocations;
    private List<String> prevLocations;

    public Transition(String name, int minTime, int maxTime, List<String> nextLocations, List<String> prevLocations) {
        this.name = name;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.nextLocations = nextLocations;
        this.prevLocations = prevLocations;
    }

    public Transition(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinTime() {
        return minTime;
    }

    public void setMinTime(int minTemp) {
        this.minTime = minTemp;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public List<String> getNextLocations() {
        return nextLocations;
    }

    public void setNextLocations(List<String> nextLocations) {
        this.nextLocations = nextLocations;
    }

    public List<String> getPrevLocations() {
        return prevLocations;
    }

    public void setPrevLocations(List<String> prevLocations) {
        this.prevLocations = prevLocations;
    }

    public int GetExecutionTime(){
        if (getMaxTime() != 0) {
            return getMinTime() + (int) (Math.random() * ((getMaxTime() - getMinTime()) + 1));
        }
        return getMinTime();
    }
}
