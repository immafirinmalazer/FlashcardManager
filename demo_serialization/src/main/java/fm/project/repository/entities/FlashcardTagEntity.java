package fm.project.repository.entities;
/*
    NOTE: this class might be unnecessary. Try to avoid using it.
*/
public class FlashcardTagEntity {
    private Integer flashcardId;
    private Integer tagId;

    public FlashcardTagEntity() {
    }

    public FlashcardTagEntity(Integer flashcardId, Integer tagId) {
        this.flashcardId = flashcardId;
        this.tagId = tagId;
    }

    public Integer getFlashcardId() {
        return flashcardId;
    }

    public void setFlashcardId(Integer flashcardId) {
        this.flashcardId = flashcardId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "FlashcardTagEntity [flashcardId=" + flashcardId + ", tagId=" + tagId + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((flashcardId == null) ? 0 : flashcardId.hashCode());
        result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
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
        FlashcardTagEntity other = (FlashcardTagEntity) obj;
        if (flashcardId == null) {
            if (other.flashcardId != null)
                return false;
        } else if (!flashcardId.equals(other.flashcardId))
            return false;
        if (tagId == null) {
            if (other.tagId != null)
                return false;
        } else if (!tagId.equals(other.tagId))
            return false;
        return true;
    }

    
}
