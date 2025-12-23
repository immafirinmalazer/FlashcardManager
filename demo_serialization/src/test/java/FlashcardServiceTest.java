import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import fm.project.model.*;
import fm.project.repository.DAO.*;
import fm.project.repository.entities.*;
import fm.project.service.*;

@ExtendWith(MockitoExtension.class)
class FlashcardServiceTest {

    @Mock
    private FlashcardDAO flashcardDAO;

    @Mock
    private TagDAO tagDAO;

    @InjectMocks
    private FlashcardService flashcardService;

    private FlashcardEntity testFlashcardEntity;

    @BeforeEach
    void setup() {
        testFlashcardEntity = new FlashcardEntity();
        testFlashcardEntity.setId(1);
        testFlashcardEntity.setCardFront("Q");
        testFlashcardEntity.setCardBack("A");
    }

    @Test
    void createEntity_Success_ReturnsId() throws SQLException {
        when(flashcardDAO.create(testFlashcardEntity)).thenReturn(5);

        Integer result = flashcardService.createEntity(testFlashcardEntity);

        assertEquals(5, result);
        verify(flashcardDAO).create(testFlashcardEntity);
    }

    @Test
    void getEntityById_Found_ReturnsEntity() throws SQLException {
        when(flashcardDAO.findById(1))
               .thenReturn(Optional.of(testFlashcardEntity));

        Optional<FlashcardEntity> result = flashcardService.getEntityById(1);

        assertTrue(result.isPresent());
        assertEquals("Q", result.get().getCardFront());
    }

    @Test
    void getEntityById_NotFound_ReturnsEmptyOptional() throws SQLException {
        when(flashcardDAO.findById(1))
               .thenReturn(Optional.empty());

        Optional<FlashcardEntity> result = flashcardService.getEntityById(1);

        assertTrue(result.isEmpty());
    }

    @Test
    void deleteEntity_Success_ReturnsTrue() throws SQLException {
        when(flashcardDAO.findById(1))
               .thenReturn(Optional.of(testFlashcardEntity));
        when(flashcardDAO.deleteById(1)).thenReturn(true);

        boolean result = flashcardService.deleteEntity(1);

        assertTrue(result);
        verify(flashcardDAO).deleteById(1);
    }

    @Test
    void deleteEntity_DeleteFails_ReturnsFalse() throws SQLException {
        when(flashcardDAO.findById(1))
               .thenReturn(Optional.of(testFlashcardEntity));
        when(flashcardDAO.deleteById(1)).thenReturn(false);

        boolean result = flashcardService.deleteEntity(1);

        assertFalse(result);
    }

    @Test
    void getModelsByDeckId_SQLException_ReturnsEmptyList() throws SQLException {
        when(flashcardDAO.searchByDeckId(99))
               .thenThrow(new SQLException());

        List<Flashcard> result = flashcardService.getModelsByDeckId(99);

        assertTrue(result.isEmpty());
    }
}

