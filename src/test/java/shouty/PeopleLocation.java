package shouty;


public class PeopleLocation {
    private String name;
    private String x;
    private String y;

    public PeopleLocation(String name, String xCord, String yCord) {
        this.name = name;
        this.x = xCord;
        this.y = yCord;
    }

    public String getName() {
        return name;
    }

    public String getXCord() {
        return x;
    }

    public String getYCord() {
        return y;
    }
}
