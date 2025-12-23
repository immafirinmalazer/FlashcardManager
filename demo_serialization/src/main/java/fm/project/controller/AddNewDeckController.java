package fm.project.controller;

import java.util.List;
import java.util.Optional;

import fm.project.model.Course;
import fm.project.model.Deck;
import fm.project.repository.entities.CourseEntity;
import fm.project.repository.entities.DeckEntity;
import fm.project.repository.entities.FlashcardEntity;
import fm.project.service.CourseService;
import fm.project.service.DeckService;
import fm.project.service.FlashcardService;
import fm.project.service.TagService;
import fm.project.util.InputHandler;

public class AddNewDeckController {
    private final CourseService courseService = new CourseService();
    private final DeckService deckService = new DeckService();
    private final FlashcardService flashcardService = new FlashcardService();
    private final TagService tagService = new TagService();

    public void handleInput(){
        Course course = null;
        boolean running = true;
        while(running){
            printCourseSelectMenu();
            int choice = InputHandler.getIntInput("Enter your choice: ");
            switch(choice){
                case 1 -> {course = selectCourse();
                    running = false;
                }
                case 2 -> {course = addCourse();
                    running = false;
                }
                case 0 -> {
                    System.out.println("Leaving \'Add a New Deck\'. Returning to Main Menu.");
                    running = false;
                }
                default -> System.out.println("Invalid choice");
            } // end switch
        } // end while  

        Deck newDeck = nameNewDeck(course);
        createFlashcards(newDeck);
    } // end handleInput()

    private void printCourseSelectMenu() {
        System.out.println("\n=== ADD A NEW DECK ===");
        System.out.println("1. Add Deck to Pre-existing Course");
        System.out.println("2. Create a New Course");
        System.out.println("0. Exit to Main Menu");
    }

    private Course selectCourse() {
        System.out.println("\n=== ADD DECK TO PRE-EXISTING COURSE ===");

        while(true){
            printCourses();
            int choice = InputHandler.getIntInput("Enter your choice by course ID: ");
            Optional<Course> course = courseService.getModelById(choice);

            if (course.isEmpty()) {
                System.out.println("Course with ID: " + choice + " not found.");
            } else {
                Course selected = course.get();
                System.out.println("Selected Course: " + selected.getName());
                return selected;
            }
        } // end while  
    } // selectCourse()

    private Course addCourse() {
        System.out.println("\n=== ADD DECK TO NEW COURSE ===");
        while (true) {
            String courseName = InputHandler.getStringInput("Name New Course: ");
            CourseEntity courseEntity = new CourseEntity();
            courseEntity.setName(courseName);
            Integer courseId = courseService.createEntity(courseEntity);
            

            if (courseId == -1) {
                System.out.println("Invalid Course Name");
            } else {
                System.out.println("New Course " + courseName + " created!");
                Optional<Course> optCourse = courseService.convertEntityToModel(courseEntity);
                Course course = optCourse.get();
                course.setId(courseId);
                return course;
            }
        }
    }

    private Deck nameNewDeck(Course course) {
        while (true) {
            String deckName = InputHandler.getStringInput("Name New Deck: ");
            DeckEntity deckEntity = new DeckEntity();
            deckEntity.setName(deckName);
            deckEntity.setCourseId(course.getId());
            Integer deckId = deckService.createEntity(deckEntity);

            if (deckId == -1) {
                System.out.println("Invalid Deck Name");
            } else {
                System.out.println("New Deck " + deckName + " created!");
                Optional<Deck> optDeck = deckService.convertEntityToModel(deckEntity);
                Deck deck = optDeck.get();
                deck.setId(deckId);
                return deck;
            }
        }
    }

    private void createFlashcards(Deck newDeck) {
        System.out.println("\n=== CREATE FLASHCARDS ===");
        int count = 0;

        while (true) { 
            System.out.println("Flashcard " + count + " / " + count);
            String front = InputHandler.getStringInput("FRONT: ");
            String back = InputHandler.getStringInput("BACK: ");
            FlashcardEntity flashcardEntity = new FlashcardEntity(newDeck.getId(),front, back);
            Integer flashcardId = flashcardService.createEntity(flashcardEntity);

            addTags(flashcardId);

            System.out.println("Flashcard added! Press 1 to continue or 0 to finish deck.");
            count++;
            int choice = 0;
            while (choice != 1) { 
                choice = InputHandler.getIntInput("Enter your choice: ");
                switch (choice) {
                    case 1 -> {break;}
                    case 0 -> {return;}
                    default -> {System.out.println("Invalid Choice");}
                }
                
            }

        }
    }

    private void addTags(Integer flashcardId) {
        while (true) {
        String tagName = InputHandler.getStringInput("Enter tag name (or type 'done' to finish): ");
        
        if (tagName.equalsIgnoreCase("done")) {
            break;
        }

        try {
            flashcardService.linkTagEntity(flashcardId, tagName);
            System.out.println("Tag '" + tagName + "' added to flashcard.");
        } catch (Exception e) {
            System.out.println("Failed to add tag: " + e.getMessage());
        }
    }

    }

    private void printCourses() {
        List<Course> courses = courseService.getAllModels();
        Integer id;
        String name;

        for (Course course : courses) {
            id = course.getId();
            name = course.getName();
            System.out.println(id + ". " + name);
        }
    }

} // end class AddNewDeckController() 
