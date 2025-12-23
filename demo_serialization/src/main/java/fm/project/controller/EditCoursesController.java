package fm.project.controller;

import java.util.List;

import fm.project.model.Course;
import fm.project.repository.entities.CourseEntity;
import fm.project.service.CourseService;
import fm.project.util.InputHandler;

public class EditCoursesController {
    private final CourseService courseService = new CourseService();

    public void handleInput(){
        boolean running = true;
        while(running){
            printMenu();
            int choice = InputHandler.getIntInput("Enter your choice: ");
            switch(choice){
                case 1 -> getAllCourses();
                case 2 -> addCourse();
                case 3 -> removeCourse();
                case 0 -> {
                    System.out.println("Leaving \'Edit Courses\'. Returning to Main Menu.");
                    running = false;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== EDIT COURSES ===");
        System.out.println("1. View All Courses");
        System.out.println("2. Add a New Course");
        System.out.println("3. Remove a Course");
        // System.out.println("4. Move Decks"); //maybe
        System.out.println("0. Exit to Main Menu");
    }

    private void getAllCourses() {
        List<Course> courseList = courseService.getAllModels();

        if (courseList.isEmpty()) {
            System.out.println("No Courses Available");
            return;
        }

        for(Course course : courseList) {
            System.out.println(course);
        }
    }

    private void addCourse() {
        String courseName = InputHandler.getStringInput("Name New Course: ");
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setName(courseName);
        Integer courseId = courseService.createEntity(courseEntity);

        if (courseId == -1) {
            System.out.println("Invalid Course Name");
        } else {
            System.out.println("New Course " + courseName + " created!");
        }
    }

    private void removeCourse() {
        List<Course> courseList = courseService.getAllModels();

        if (courseList.isEmpty()) {
            System.out.println("No Courses Available");
            return;
        }

        Integer id;
        String courseName;

        for (Course course : courseList) {
            id = course.getId();
            courseName = course.getName();
            System.out.println(id + ". " + courseName);
        }

        Integer removeId = InputHandler.getIntInput("Select a Course by ID to Remove: ");

        try {
            if(courseService.deleteEntity(removeId)) {
                System.out.println("Removed Course with ID: " + removeId);
            }
        } catch (RuntimeException e) {
            System.out.println("Error! Unable to Remove Course!");
        }
    }
}
