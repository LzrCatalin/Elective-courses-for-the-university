package org.example.springproject;

import org.example.springproject.entity.Application;
import org.example.springproject.entity.Course;
import org.example.springproject.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpringProjectApplicationTests {
    @Test
    void contextLoads() {
    }

    /**
     * Test for adding a user to the system
     */
    @Test
    void addUser() {
        User user = new User(1L, "Dorel Marinescu", "student");
        assertEquals("Dorel Marinescu", user.getName());
        assertEquals("student", user.getRole());
    }

    /**
     * Test for create an application to a course
     */
    @Test
    void testApplication() {
        User user = new User(1L, "Dorel Marinescu", "student");
        assertEquals("Dorel Marinescu", user.getName());
        assertEquals("student", user.getRole());

        Course course = new Course("Math", "Geometry", 5, "John Doe", 30, "Mathematics");
        assertEquals("Math", course.getName());
        assertEquals("Geometry", course.getCategory());
        assertEquals(5, course.getStudyYear());
        assertEquals("John Doe", course.getTeacher());
        assertEquals(30, course.getMaxCapacity());
        assertEquals("Mathematics", course.getFacultySection());

        Application application = new Application(user, course, 1, "pending");
        assertEquals("Dorel Marinescu", application.getUser().getName());
        assertEquals("Math", application.getCourse().getName());
        assertEquals(1, application.getPriority());
        assertEquals("pending", application.getStatus());

    }
}
