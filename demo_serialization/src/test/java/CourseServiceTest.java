import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import fm.project.model.Course;
import fm.project.repository.DAO.CourseDAO;
import fm.project.repository.entities.CourseEntity;
import fm.project.service.CourseService;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseDAO courseDAO;

    @InjectMocks
    private CourseService courseService;

    private CourseEntity testCourseEntity;
    private Course testCourseModel;

    @BeforeEach
    void setup() {

        testCourseEntity = new CourseEntity("Biology", 1);

        testCourseModel = new Course("Biology", 1);
    }

    @Test
    void getAllModels_Success_ReturnsList() throws SQLException {
        // Arrange dependency behavior
        CourseEntity testCourseEntity2 = new CourseEntity("Physics", 2);
        List<CourseEntity> courseEntities = List.of(testCourseEntity, testCourseEntity2);

        when(courseDAO.findAll()).thenReturn(courseEntities);

        // Act out actual output of tested method
        List<Course> result = courseService.getAllModels();

        // Assert results match expected values
        assertEquals("Biology", result.get(0).getName());
        assertEquals("Physics", result.get(1).getName());

        verify(courseDAO, times(1)).findAll();
    }

    @Test
    void getAllModels_ThrowsSQLException_ReturnsEmptyList() throws SQLException {
        // Arrange dependency behavior
        when(courseDAO.findAll()).thenThrow(new SQLException("DB error"));

        // Act out actual output of tested method
        List<Course> result = courseService.getAllModels();

        // Assert results match expected values
        assertTrue(result.isEmpty());

        verify(courseDAO, times(1)).findAll();
    }




}