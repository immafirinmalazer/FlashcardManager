package fm.project.repository.entities;

public class FlashcardEntity {
    private Integer id;
    private Integer deckId;
    private String cardFront;
    private String cardBack;

    public FlashcardEntity() {
    }

    public FlashcardEntity(Integer deckId, String cardFront, String cardBack) {
        this.deckId = deckId;
        this.cardFront = cardFront;
        this.cardBack = cardBack;
    }

    public FlashcardEntity(Integer id, Integer deckId, String cardFront, String cardBack) {
        this.id = id;
        this.deckId = deckId;
        this.cardFront = cardFront;
        this.cardBack = cardBack;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeckId() {
        return deckId;
    }

    public void setDeckId(Integer deckId) {
        this.deckId = deckId;
    }

    public String getCardFront() {
        return cardFront;
    }

    public void setCardFront(String cardFront) {
        this.cardFront = cardFront;
    }

    public String getCardBack() {
        return cardBack;
    }

    public void setCardBack(String cardBack) {
        this.cardBack = cardBack;
    }

    @Override
    public String toString() {
        return "FlashcardEntity [id=" + id + ", deckId=" + deckId + ", cardFront=" + cardFront + ", cardBack="
                + cardBack + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FlashcardEntity other = (FlashcardEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
}
