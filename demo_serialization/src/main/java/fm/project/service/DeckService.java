package fm.project.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import fm.project.model.Deck;
import fm.project.repository.DAO.DeckDAO;
import fm.project.repository.entities.DeckEntity;
import fm.project.service.interfaces.ServiceInterface;

public class DeckService implements ServiceInterface<DeckEntity, Deck>{
    private DeckDAO deckDAO = new DeckDAO();

    @Override
    public Integer createEntity(DeckEntity entity) {
        try {
            Integer newId = deckDAO.create(entity);
            return newId;
        } catch (SQLException e) {
            e.printStackTrace(); // replace with logger
                return -1;
        }
    }

    @Override
    public Optional<DeckEntity> getEntityById(Integer id) {
        try {
            Optional<DeckEntity> deckEntity = deckDAO.findById(id);
            if(deckEntity.isEmpty()) {
                throw new RuntimeException("Deck with id " + id + " not found");
            }
            return deckEntity;
        } catch (SQLException | RuntimeException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<DeckEntity> getAllEntities() {
        try {
            List<DeckEntity> deckEntities = deckDAO.findAll();
            return deckEntities;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DeckEntity updateEntity(Integer id, DeckEntity newEntity) {
        try {
            newEntity.setId(id);
            newEntity = deckDAO.updateById(newEntity);
            return newEntity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteEntity(Integer id) {
        try {
            Optional<DeckEntity> deckEntity = deckDAO.findById(id);

            if (deckEntity.isEmpty()) {
                throw new RuntimeException("Deck with id " + id + " not found");
            }

            if(!(deckDAO.deleteById(id))) {
                throw new RuntimeException("Failed to delete deck with id " + id);
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Deck> convertEntityToModel(DeckEntity entity) {
        Deck deck = new Deck();
        deck.setId(entity.getId());
        deck.setName(entity.getName());

        return Optional.of(deck);
    }

    @Override
    public Optional<Deck> getModelById(Integer id) {
        Optional<DeckEntity> deckEntity = getEntityById(id);
        try {
            if(deckEntity.isPresent()) {
                Optional<Deck> deck = convertEntityToModel(deckEntity.get());

                if(deck.isPresent()) {
                    return deck;
                } else {
                    throw new RuntimeException("DeckEntity conversion failed");
                }

            } else {
                throw new RuntimeException("DeckEntity not found");
            }
            
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<Deck> getAllModels() {
        
        List<DeckEntity> deckEntities = getAllEntities();
        List<Deck> decks = new ArrayList<>();

        if (deckEntities == null) {
        return Collections.emptyList(); // never null
        }

        for(DeckEntity deckEntity : deckEntities){
            Optional<Deck> deck = convertEntityToModel(deckEntity);
            if(deck.isPresent()){
                decks.add(deck.get());
            }
        }
        return decks;
    }

    public List<Deck> getModelsByCourseId (Integer courseId) {
        List<Deck> decks = new ArrayList<>();
        
        try {
            List<DeckEntity> deckEntities = deckDAO.searchByCourseId(courseId);

        for(DeckEntity deckEntity : deckEntities){
            Optional<Deck> deck = convertEntityToModel(deckEntity);
            if(deck.isPresent()){
                decks.add(deck.get());
            }
        }
        } catch (SQLException e) {
            //logger
        }
        return decks;
    }
    
}
