package fm.project.repository.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fm.project.repository.DAO.interfaces.DAOInterface;
import fm.project.repository.entities.FlashcardEntity;
import fm.project.repository.entities.TagEntity;
import fm.project.util.ConnectionHandler;

public class FlashcardDAO implements DAOInterface<FlashcardEntity> {

    private final Connection connection = ConnectionHandler.getConnection();

    @Override
    public Integer create(FlashcardEntity entity) throws SQLException {
        String sql = "INSERT INTO flashcards (deck_id, card_front, card_back) VALUES (?, ?, ?) RETURNING flashcard_id";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entity.getDeckId());
            stmt.setString(2, entity.getCardFront());
            stmt.setString(3, entity.getCardBack());

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    return rs.getInt("flashcard_id");
                }
            }
        }
        return null;
    }

    @Override
    public Optional<FlashcardEntity> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM flashcards WHERE flashcard_id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    FlashcardEntity flashcardEntity = new FlashcardEntity();
                    flashcardEntity.setId(rs.getInt("flashcard_id"));
                    flashcardEntity.setDeckId(rs.getInt("deck_id"));
                    flashcardEntity.setCardFront(rs.getString("card_front"));
                    flashcardEntity.setCardBack(rs.getString("card_back"));

                    return Optional.of(flashcardEntity);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<FlashcardEntity> findAll() throws SQLException {
        List<FlashcardEntity> flashcards = new ArrayList<>();

        String sql = "SELECT * FROM flashcards";
        try(PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                FlashcardEntity flashcardEntity = new FlashcardEntity();
                flashcardEntity.setId(rs.getInt("flashcard_id"));
                flashcardEntity.setDeckId(rs.getInt("deck"));
                flashcardEntity.setCardFront(rs.getString("card_front"));
                flashcardEntity.setCardBack(rs.getString("card_back"));
                flashcards.add(flashcardEntity);
            }
        }
        return flashcards;
    }

    @Override
    public FlashcardEntity updateById(FlashcardEntity entity) throws SQLException {
        String sql = "UPDATE flashcards SET card_front = ?, card_back = ? WHERE flashcard_id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getCardFront());
            stmt.setString(2, entity.getCardBack());
            stmt.setInt(3, entity.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Flashcard not found with id: " + entity.getId());
            }

            return entity;
        }
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM flashcards WHERE flashcard_id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected == 1;
        }
    }

    public Integer addTag(Integer flashcardId, Integer tagId) throws SQLException {
        String sql = "INSERT INTO flashcard_tags (flashcard_id, tag_id) VALUES (?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, flashcardId);
            stmt.setInt(2, tagId);

            return stmt.executeUpdate();
        }
    }
        

    public boolean deleteTag(Integer flashcardId, Integer tagId) throws SQLException {
        String sql = "DELETE FROM flashcard_tags WHERE flashcard_id = ? AND tag_id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, flashcardId);
            stmt.setInt(2, tagId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected == 1;
        }

    }

    public boolean deleteAllTags(Integer flashcardId) throws SQLException {
        String sql = "DELETE FROM flashcard_tags WHERE flashcard_id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, flashcardId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }

    }

    public List<TagEntity> findTagsByFlashcardId(Integer flashcardId) throws SQLException {
        String sql = "SELECT tag_id, tag_name FROM flashcard_tags " + 
            "INNER JOIN tags ON flashcard_tags.tag_id = tags.tag_id " +
            "WHERE flashcard_id = ?";

        List<TagEntity> tags = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, flashcardId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TagEntity tagEntity = new TagEntity();
                    tagEntity.setId(rs.getInt("tag_id"));
                    tagEntity.setName(rs.getString("tag_name"));
                    tags.add(tagEntity);
                }
            }
        }
    return tags;
    } 
    
    public List<FlashcardEntity> searchByDeckId(Integer deckId) throws SQLException{
        String sql = "SELECT flashcard_id, card_front, card_back FROM flashcards WHERE deck_id = ?";
        List<FlashcardEntity> flashcards = new ArrayList<>();

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, deckId);
            
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                FlashcardEntity flashcardEntity = new FlashcardEntity();
                flashcardEntity.setId(rs.getInt("flashcard_id"));
                flashcardEntity.setCardFront(rs.getString("card_front"));
                flashcardEntity.setCardBack(rs.getString("card_back"));
                flashcards.add(flashcardEntity);
                }
            }
        }
        return flashcards;
    }

}
