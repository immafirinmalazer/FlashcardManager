package fm.project.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fm.project.model.Flashcard;
import fm.project.repository.DAO.FlashcardDAO;
import fm.project.repository.DAO.TagDAO;
import fm.project.repository.entities.FlashcardEntity;
import fm.project.repository.entities.TagEntity;
import fm.project.service.interfaces.ServiceInterface;

public class FlashcardService implements ServiceInterface<FlashcardEntity, Flashcard>{

    private FlashcardDAO flashcardDAO = new FlashcardDAO();
    private TagDAO tagDAO = new TagDAO();

    @Override
    public Integer createEntity(FlashcardEntity entity) {
        try {
            Integer newId = flashcardDAO.create(entity);
            return newId;
        } catch (SQLException e) {
            e.printStackTrace(); // replace with logger
                return -1;
        }
    }

    @Override
    public Optional<FlashcardEntity> getEntityById(Integer id) {
        try {
            Optional<FlashcardEntity> flashcardEntity = flashcardDAO.findById(id);
            if(flashcardEntity.isEmpty()) {
                throw new RuntimeException( "Flashcard with id " + id + " not found");
            }
            return flashcardEntity;
        } catch (SQLException | RuntimeException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<FlashcardEntity> getAllEntities() {
        try {
            List<FlashcardEntity> flashcardEntities = flashcardDAO.findAll();
            return flashcardEntities;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public FlashcardEntity updateEntity(Integer id, FlashcardEntity newEntity) {
        try {
            newEntity.setId(id);
            newEntity = flashcardDAO.updateById(newEntity);
            return newEntity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteEntity(Integer id) {
        try {
            Optional<FlashcardEntity> flashcardEntity = flashcardDAO.findById(id);

            if (flashcardEntity.isEmpty()) {
                throw new RuntimeException( "Flashcard with id " + id + " not found");
            }

            if(! flashcardDAO.deleteById(id)) {
                throw new RuntimeException("Failed to delete Flashcard with id " + id);
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Flashcard> convertEntityToModel(FlashcardEntity entity) {
     Flashcard flashcard = new Flashcard();
     flashcard.setId(entity.getId());
     flashcard.setFront(entity.getCardFront());
     flashcard.setBack(entity.getCardBack());

        return Optional.of(flashcard);
    }

    @Override
    public Optional<Flashcard> getModelById(Integer id) {
        Optional<FlashcardEntity> flashcardEntity = getEntityById(id);
        try {
            if(flashcardEntity.isPresent()) {
                Optional<Flashcard> flashcard = convertEntityToModel(flashcardEntity.get());

                if (flashcard.isPresent()) {
                    return flashcard;
                } else {
                    throw new RuntimeException("FlashcardEntity conversion failed");
                }

            } else {
                throw new RuntimeException("FlashcardEntity not found");
            }
            
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public FlashcardEntity linkTagEntity(Integer flashcardId, String tagName) {
        try {
            TagEntity tag;

            Optional<TagEntity> existing = tagDAO.findByName(tagName);

        if (existing.isPresent()) {
            tag = existing.get();
        } else {
            TagEntity newTag = new TagEntity();
            newTag.setName(tagName);

            try {
                Integer tagId = tagDAO.create(newTag);
                newTag.setId(tagId);
                tag = newTag;

            } catch (SQLException e) {
                tag = tagDAO.findByName(tagName)
                        .orElseThrow(() -> e);
            }
        }
        flashcardDAO.addTag(flashcardId, tag.getId());

        return flashcardDAO.findById(flashcardId)
                .orElseThrow(() -> new RuntimeException("Flashcard not found"));

        } catch (SQLException e) {
            throw new RuntimeException("Failed to link tag", e);
        }
    }

    public List<Flashcard> getModelsByDeckId (Integer deckId) {
        List<Flashcard> flashcards = new ArrayList<>();
        
        try {
            List<FlashcardEntity> flashcardEntities = flashcardDAO.searchByDeckId(deckId);

        for(FlashcardEntity flashcardEntity : flashcardEntities){
            Optional<Flashcard> flashcard = convertEntityToModel(flashcardEntity);
            if(flashcard.isPresent()){
                flashcards.add(flashcard.get());
            }
        }
        } catch (SQLException e) {
            //logger
        }
        return flashcards;
    }
}
