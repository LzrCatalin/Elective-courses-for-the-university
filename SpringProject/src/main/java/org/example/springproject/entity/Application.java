package org.example.springproject.entity;

import jakarta.persistence.*;
import org.example.springproject.enums.Status;

@Entity
@Table(name = "applications")

public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="studentId", nullable = false)
    private Student student;

    /**
     * ManyToOne relationship with the Course entity
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="courseId", nullable = false)
    private Course course;
    @Column(name = "priority")
    private Integer priority;
    @Column(name = "status")
    private Status status;

    /**
     * Empty Constructor
     */
    public Application(){}

    /**
     * Constructor
     * @param student of the application
     * @param course of the application
     * @param priority of the application
     * @param status of the application
     */
    public Application(Student student, Course course, Integer priority, Status status) {
        this.student = student;
        this.course = course;
        this.priority = priority;
        this.status = status;
    }

    /**
     * Getter for the user of the application
     * @return user of the application
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Setter for the user of the application
     * @param student of the application
     */
    public void setStudent(Student student) {
        this.student = student;
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
    public Status getStatus() {
        return status;
    }

    /**
     * Setter for the status of the application
     * @param status of the application
     */
    public void setStatus(Status status) {
        this.status = status;
    }
}
