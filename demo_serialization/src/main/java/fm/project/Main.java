package fm.project;

import fm.project.controller.AddNewDeckController;
import fm.project.controller.EditCoursesController;
import fm.project.controller.EditDecksController;
import fm.project.controller.StudyDeckController;
import fm.project.util.InputHandler;


public class Main {
    
    public static void main(String[] args) {

        AddNewDeckController addNewDeckController =  new AddNewDeckController();
        EditDecksController editDecksController = new EditDecksController();
        EditCoursesController editCoursesController = new EditCoursesController();
        StudyDeckController studyDeckController = new StudyDeckController();
        

        System.out.println("=== FLASHCARD MANAGEMENT SYSTEM ===");

        boolean running = true;
        while(running){
            printMenu();
            int choice = InputHandler.getIntInput("Make a choice: ");
            switch(choice){
                case 1 -> addNewDeckController.handleInput();
                case 2 -> editDecksController.handleInput();
                case 3 -> editCoursesController.handleInput();
                case 4 -> studyDeckController.handleInput();
                case 0 -> {
                    System.out.println("Closing Flashcard Manager. Goodbye!");
                    running = false;
                }
            }
        }
    }

    private static void printMenu(){
        System.out.println("=== MAIN MENU ===");
        System.out.println("1. Add a New Deck");
        System.out.println("2. View/Edit Decks");
        System.out.println("3. View/Edit Courses");
        System.out.println("4. Study a Deck");
        System.out.println("0. Exit");
    }
}
        
    
