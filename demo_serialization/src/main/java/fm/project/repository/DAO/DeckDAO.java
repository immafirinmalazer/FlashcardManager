package fm.project.repository.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fm.project.repository.DAO.interfaces.DAOInterface;
import fm.project.repository.entities.DeckEntity;
import fm.project.util.ConnectionHandler;

public class DeckDAO implements DAOInterface<DeckEntity>{

    private final Connection connection = ConnectionHandler.getConnection();

    @Override
    public Integer create(DeckEntity entity) throws SQLException {
        String sql = "INSERT INTO decks (deck_name, course_id) VALUES (?, ?) RETURNING deck_id";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getName());
            stmt.setInt(2, entity.getCourseId());

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    return rs.getInt("deck_id");
                }
            }
        }
        return null;
    } // create()

    @Override
    public Optional<DeckEntity> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM decks WHERE deck_id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    DeckEntity deckEntity = new DeckEntity();
                    deckEntity.setId(rs.getInt("deck_id"));
                    deckEntity.setName(rs.getString("deck_name"));

                    return Optional.of(deckEntity);
                }
            }
        }
        return Optional.empty();
    } // findById()

    @Override
    public List<DeckEntity> findAll() throws SQLException {
        List<DeckEntity> decks = new ArrayList<>();

        String sql = "SELECT * FROM decks";
        try(PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                DeckEntity deckEntity = new DeckEntity();
                deckEntity.setId(rs.getInt("deck_id"));
                deckEntity.setName(rs.getString("deck_name"));
                decks.add(deckEntity);
            }
        }
        return decks;
    } // findAll()

    @Override
    public DeckEntity updateById(DeckEntity entity) throws SQLException {
        String sql = "UPDATE decks SET deck_name = ? WHERE deck_id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getName());
            stmt.setInt(2, entity.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Deck not found with id: " + entity.getId());
            }

            return entity;
        }
    } // updateById()

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM decks WHERE deck_id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected == 1;
        }
    } // deleteById()

    public List<DeckEntity> searchByCourseId(Integer courseId) throws SQLException{
        String sql = "SELECT deck_id, deck_name FROM decks WHERE course_id = ?";
        List<DeckEntity> decks = new ArrayList<>();

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, courseId);
            
            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                DeckEntity deckEntity = new DeckEntity();
                deckEntity.setId(rs.getInt("deck_id"));
                deckEntity.setName(rs.getString("deck_name"));
                decks.add(deckEntity);
                }
            }
        }
        return decks;
    }
    

}
