package fm.project.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import fm.project.model.Course;
import fm.project.repository.DAO.CourseDAO;
import fm.project.repository.entities.CourseEntity;
import fm.project.service.interfaces.ServiceInterface;


public class CourseService implements ServiceInterface<CourseEntity, Course> {

    private CourseDAO courseDAO = new CourseDAO();

    @Override
    public Integer createEntity(CourseEntity entity) {
        try {
            Integer newId = courseDAO.create(entity);
            return newId;
        } catch (SQLException e) {
            e.printStackTrace(); // replace with logger
                return -1;
        }
    }

    @Override
    public Optional<CourseEntity> getEntityById(Integer id) {
        try {
            Optional<CourseEntity> courseEntity = courseDAO.findById(id);
            if(courseEntity.isEmpty()) {
                throw new RuntimeException("Course with id " + id + " not found");
            }
            return courseEntity;
        } catch (SQLException | RuntimeException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<CourseEntity> getAllEntities() {
        try {
            List<CourseEntity> courseEntities = courseDAO.findAll();
            return courseEntities;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override //this one is not needed
    public CourseEntity updateEntity(Integer id, CourseEntity newEntity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean deleteEntity(Integer id) {
        try {
            Optional<CourseEntity> courseEntity = courseDAO.findById(id);

            if (courseEntity.isEmpty()) {
                throw new RuntimeException("Course with id " + id + " not found");
            }

            if(!(courseDAO.deleteById(id))) {
                throw new RuntimeException("Failed to delete course with id " + id);
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Course> convertEntityToModel(CourseEntity entity) {
        Course course = new Course();
        course.setId(entity.getId());
        course.setName(entity.getName());

        return Optional.of(course);
    }

    @Override
    public Optional<Course> getModelById(Integer id) {
        Optional<CourseEntity> courseEntity = getEntityById(id);
        try {
            if(courseEntity.isPresent()) {
                Optional<Course> course = convertEntityToModel(courseEntity.get());

                if(course.isPresent()) {
                    return course;
                } else {
                    throw new RuntimeException("CourseEntity conversion failed");
                }

            } else {
                throw new RuntimeException("CourseEntity not found");
            }
            
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<Course> getAllModels() {
        
        List<CourseEntity> courseEntities = getAllEntities();
        List<Course> courses = new ArrayList<>();

        if (courseEntities == null) {
        return Collections.emptyList(); // never null
        }

        for(CourseEntity courseEntity : courseEntities){
            Optional<Course> course = convertEntityToModel(courseEntity);
            if(course.isPresent()){
                courses.add(course.get());
            }
        }
        return courses;
    }
    
}
