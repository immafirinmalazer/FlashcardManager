package fm.project.model;

public class Deck {
    private String name;
    private Integer id;

    public Deck(){
        
    }

    public Deck(String name) {
        this.name = name;
    }

    public Deck(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + ", id=" + id;
    }

}
