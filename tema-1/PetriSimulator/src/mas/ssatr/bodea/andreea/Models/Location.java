package mas.ssatr.bodea.andreea.Models;

public class Location {
    private String name;
    private int tokens;

    public Location(String name, int tokens) {
        this.name = name;
        this.tokens = tokens;
    }

    public Location(String name) {
        this.name = name;
    }

    public Location() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }
}
