package fm.project.model;

public class Flashcard {

    private Integer id;
    private String front;
    private String back;

    public Flashcard() {
        
    }

    public Flashcard(String someFront, String someBack) {
        this.front = someFront;
        this.back = someBack;
    }

    public Flashcard(Integer id, String someFront, String someBack) {
        this.id = id;
        this.front = someFront;
        this.back = someBack;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Q: " + front + "\nA: " + back;
    }
}
