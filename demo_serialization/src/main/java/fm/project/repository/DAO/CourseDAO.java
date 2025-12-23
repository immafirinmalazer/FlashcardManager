package fm.project.repository.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fm.project.repository.DAO.interfaces.DAOInterface;
import fm.project.repository.entities.CourseEntity;
import fm.project.util.ConnectionHandler;

public class CourseDAO implements DAOInterface<CourseEntity>{

    private final Connection connection = ConnectionHandler.getConnection();

    @Override
    public Integer create(CourseEntity entity) throws SQLException {
        String sql = "INSERT INTO courses (course_name) VALUES (?) RETURNING course_id";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getName());

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    return rs.getInt("course_id");
                }
            }
        }
        return null;
    }

    @Override
    public Optional<CourseEntity> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM courses WHERE course_id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    CourseEntity courseEntity = new CourseEntity();
                    courseEntity.setId(rs.getInt("course_id"));
                    courseEntity.setName(rs.getString("course_name"));

                    return Optional.of(courseEntity);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<CourseEntity> findAll() throws SQLException {
        List<CourseEntity> courses = new ArrayList<>();

        String sql = "SELECT * FROM courses";
        try(PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while(rs.next()) {
                CourseEntity courseEntity = new CourseEntity();
                courseEntity.setId(rs.getInt("course_id"));
                courseEntity.setName(rs.getString("course_name"));
                courses.add(courseEntity);
            }
        }
        return courses;
    }

    @Override
    public CourseEntity updateById(CourseEntity entity) throws SQLException {
        String sql = "UPDATE courses SET course_name = ? WHERE course_id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getName());
            stmt.setInt(2, entity.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Course not found with id: " + entity.getId());
            }

            return entity;
        }
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        String sql = "DELETE FROM courses WHERE course_id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected == 1;
        }
    }

    public Optional<CourseEntity> findByCourseName(String courseName) throws SQLException {
        String sql = "SELECT * FROM courses WHERE course_name = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, courseName);

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    CourseEntity courseEntity = new CourseEntity();
                    courseEntity.setId(rs.getInt("course_id"));
                    courseEntity.setName(rs.getString("course_name"));
                    
                    return Optional.of(courseEntity);
                }
            }
        }
        return Optional.empty();
    }   
}

