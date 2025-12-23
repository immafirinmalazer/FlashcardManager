package fm.project.controller;

import java.util.List;

import fm.project.model.Course;
import fm.project.model.Deck;
import fm.project.model.Flashcard;
import fm.project.service.CourseService;
import fm.project.service.DeckService;
import fm.project.service.FlashcardService;
import fm.project.util.InputHandler;


public class StudyDeckController {
    private final CourseService courseService = new CourseService();
    private final DeckService deckService = new DeckService();
    private final FlashcardService flashcardService = new FlashcardService();

    public void handleInput(){

        Course courseChoice = selectCourse();

        if (courseChoice == null) {
            return;
        }

        Deck deckChoice = selectDeck(courseChoice);

        if (deckChoice == null) {
            return;
        }

        while(true) {
            studyDeck(deckChoice);

            System.out.println("Congratulations! You've made it to the end of the deck.");
            System.out.println("Press 1 to play again or 0 to return to Main Menu.");

            int choice = InputHandler.getIntInput("Enter your choice: ");
            switch(choice){
                case 1 -> {
                    continue;
                }
                case 0 -> {
                    System.out.println("Leaving \'Study a Deck\'. Returning to Main Menu.");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }

        }
        
    } // handleInput()

    private Course selectCourse() {
        System.out.println("\n=== STUDY A DECK ===");
        System.out.println("Select a Course to Study\n");

        List<Course> courseList = courseService.getAllModels();

        if (courseList.isEmpty()) {
            System.out.println("No Courses Available");
            return null;
        }

        for(Course course : courseList) {
            System.out.println(course);
        }

        Integer choice = InputHandler.getIntInput("Enter your choice: ");
        Course course = courseService.getModelById(choice).get();

        return course; 
    } // selectCourse()

    private Deck selectDeck(Course courseChoice) {
        System.out.println("Select a Deck to Study\n");

        List<Deck> deckList = deckService.getModelsByCourseId(courseChoice.getId());

        if (deckList.isEmpty()) {
            System.out.println("No Decks Available");
            return null;
        }

        for (Deck deck : deckList) {
            System.out.println(deck);
        }

        Integer choice = InputHandler.getIntInput("Enter your choice: ");
        Deck deck = deckService.getModelById(choice).get();

        return deck; 

    }

    private void studyDeck(Deck deckChoice) {
        List<Flashcard> flashcards = flashcardService.getModelsByDeckId(deckChoice.getId());
        System.out.println("\n=== BEGIN STUDY ===");

        for (Flashcard flashcard : flashcards) {
            System.out.println("\nFlashcard");
            System.out.println("FRONT: " + flashcard.getFront());
            System.out.println("Press Enter to view BACK");
            InputHandler.pressEnterToContinue();
            System.out.println("BACK: " + flashcard.getBack());
            System.out.println("Press Enter to continue");
            InputHandler.pressEnterToContinue();
        } 
    }

} // end class
