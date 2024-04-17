package org.example.springproject.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "applications")

public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userId", nullable = false)
    private User user;

    /**
     * ManyToOne relationship with the Course entity
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="courseId", nullable = false)
    private Course course;
    @Column
    private Integer priority;
    @Column
    private String status;

    /**
     * Empty Constructor
     */
    public Application(){}

    /**
     * Constructor
     * @param user of the application
     * @param course of the application
     * @param priority of the application
     * @param status of the application
     */
    public Application(User user, Course course, Integer priority, String status) {
        this.user = user;
        this.course = course;
        this.priority = priority;
        this.status = status;
    }

    /**
     * Getter for the user of the application
     * @return user of the application
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter for the user of the application
     * @param user of the application
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Getter for the course of the application
     * @return course of the application
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Setter for the course of the application
     * @param course of the application
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Getter for the priority of the application
     * @return priority of the application
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Setter for the priority of the application
     * @param priority of the application
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * Getter for the status of the application
     * @return status of the application
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter for the status of the application
     * @param status of the application
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
