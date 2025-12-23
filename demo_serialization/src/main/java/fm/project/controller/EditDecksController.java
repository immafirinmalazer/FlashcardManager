package fm.project.controller;

import java.util.List;

import fm.project.model.Deck;
import fm.project.repository.entities.DeckEntity;
import fm.project.service.CourseService;
import fm.project.service.DeckService;
import fm.project.util.InputHandler;

public class EditDecksController {
    private final CourseService courseService = new CourseService();
    private final DeckService deckService = new DeckService();

    public void handleInput(){
        boolean running = true;
        while(running){
            printMenu();
            int choice = InputHandler.getIntInput("Enter your choice: ");
            switch(choice){
                case 1 -> getAllDecks();
                case 2 -> addDeck();
                case 3 -> removeDeck();
                case 0 -> {
                    System.out.println("Leaving \'Edit Decks\'. Returning to Main Menu.");
                    running = false;
                }
                default -> System.out.println("Invalid choice");
            } // end switch
        } // end while
    } // end handleInput()

    private void printMenu() {
        System.out.println("\n=== EDIT DECKS ===");
        System.out.println("1. View All Decks");
        System.out.println("2. Add a New Deck");
        System.out.println("3. Remove a Deck");
        System.out.println("0. Exit to Main Menu");
    }

    private void getAllDecks() {
        List<Deck> deckList = deckService.getAllModels();

        if (deckList.isEmpty()) {
            System.out.println("No Decks Available");
            return;
        }

        for (Deck deck : deckList) {
            System.out.println(deck);
        }
    }

    private void addDeck() {
        String deckName = InputHandler.getStringInput("Name New Deck: ");
        DeckEntity deckEntity = new DeckEntity();
        deckEntity.setName(deckName);
        Integer deckId = deckService.createEntity(deckEntity);

        if (deckId == -1) {
            System.out.println("Invalid Deck Name");
        } else {
            System.out.println("New Deck " + deckName + " created!");
        }
    }

    private void removeDeck() {
        List<Deck> deckList = deckService.getAllModels();
        
        if(deckList.isEmpty()) {
            System.out.println("No Decks Available");
        }

        Integer id;
        String deckName;

        for (Deck deck : deckList) {
            id = deck.getId();
            deckName = deck.getName();
            System.out.println(id + ". " + deckName);
        }

        Integer removeId = InputHandler.getIntInput("Select a Deck by ID to Remove: ");

        try {
            if (deckService.deleteEntity(removeId)) {
                System.out.println("Removed Deck with ID: " + removeId);
            }
        } catch ( RuntimeException e) {
            System.out.println("Error! Unable to Remove Deck!");
        }
    }
    
} // end class
