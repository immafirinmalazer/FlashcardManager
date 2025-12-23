package fm.project.repository.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fm.project.repository.DAO.interfaces.DAOInterface;
import fm.project.repository.entities.TagEntity;
import fm.project.util.ConnectionHandler;

public class TagDAO implements DAOInterface<TagEntity>{

    private final Connection connection = ConnectionHandler.getConnection();

    @Override
    public Integer create(TagEntity entity) throws SQLException {
        String sql = "INSERT INTO tags (tag_name) VALUES (?) RETURNING tag_id";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getName());

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    return rs.getInt("tag_id");
                }
            }
        }
        throw new SQLException("Failed to create tag");
    }

    @Override
    public Optional<TagEntity> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM tags WHERE tag_id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    TagEntity tagEntity = new TagEntity();
                    tagEntity.setId(rs.getInt("tag_id"));
                    tagEntity.setName(rs.getString("tag_name"));

                    return Optional.of(tagEntity);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<TagEntity> findAll() throws SQLException {
        List<TagEntity> tags = new ArrayList<>();

        String sql = "SELECT * FROM tags";
        try(PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                TagEntity tagEntity = new TagEntity();
                tagEntity.setId(rs.getInt("tag_id"));
                tagEntity.setName(rs.getString("tag_name"));
                tags.add(tagEntity);
            }
        }
        return tags;
    }

    @Override
    public TagEntity updateById(TagEntity entity) throws SQLException {
        String sql = "UPDATE tags SET tag_name = ? WHERE tag_id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getName());
            stmt.setInt(2, entity.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Tag not found with id: " + entity.getId());
            }

            return entity;
        }
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM tags WHERE tag_id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected == 1;
        }
    }

    public Optional<TagEntity> findByName(String name) throws SQLException {
         String sql = "SELECT * FROM tags WHERE tag_name = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    TagEntity tagEntity = new TagEntity();
                    tagEntity.setId(rs.getInt("tag_id"));
                    tagEntity.setName(rs.getString("tag_name"));

                    return Optional.of(tagEntity);
                }
            }
        }
        return Optional.empty();
    }

    
}
