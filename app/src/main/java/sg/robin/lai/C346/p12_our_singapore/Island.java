package sg.robin.lai.C346.p12_our_singapore;

public class Island {
    private int id;
    private String name;
    private String description;
    private int area;
    private float star;

    public Island(int id,String name, String description, int area, float star) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.area = area;
        this.star = star;
    }

    public Island(String name, String description, int area, float star) {
        this.name = name;
        this.description = description;
        this.area = area;
        this.star = star;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }
}
