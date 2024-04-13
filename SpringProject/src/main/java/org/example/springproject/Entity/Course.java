package org.example.springproject.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")

public class Course {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name ="category")
    private String category;
    @Column(name ="studyYear")
    private Integer studyYear;
    @Column(name ="teacher")
    private String teacher;
    @Column(name ="maxCapacity")
    private Integer maxCapacity;
    @Column(name ="facultySection")
    private String facultySection;

    /**
     * Empty Constructor
     */
    public Course(){}

    /**
     * Constructor
     * @param name of the course
     * @param category of the course
     * @param studyYear of the course
     * @param teacher of the course
     * @param maxCapacity of the course
     * @param facultySection of the course
     */
    public Course(String name, String category, Integer studyYear, String teacher, Integer maxCapacity, String facultySection) {
        this.name = name;
        this.category = category;
        this.studyYear = studyYear;
        this.teacher = teacher;
        this.maxCapacity = maxCapacity;
        this.facultySection = facultySection;
    }

    /**
     * Getter for the name of the course
     * @return name of the course
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the category of the course
     * @return category of the course
     */
    public String getCategory() {
        return category;
    }

    /**
     * Getter for the study year of the course
     * @return study year of the course
     */
    public Integer getStudyYear() {
        return studyYear;
    }

    /**
     * Getter for the teacher of the course
     * @return teacher of the course
     */
    public String getTeacher() {
        return teacher;
    }

    /**
     * Getter for the max capacity of the course
     * @return max capacity of the course
     */
    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Getter for the faculty section of the course
     * @return faculty section of the course
     */
    public String getFacultySection() {
        return facultySection;
    }

    /**
     * Setter for the name of the course
     * @param name of the course
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for the category of the course
     * @param category of the course
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Setter for the study year of the course
     * @param studyYear of the course
     */
    public void setStudyYear(Integer studyYear) {
        this.studyYear = studyYear;
    }

    /**
     * Setter for the teacher of the course
     * @param teacher of the course
     */
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    /**
     * Setter for the max capacity of the course
     * @param maxCapacity of the course
     */
    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    /**
     * Setter for the faculty section of the course
     * @param facultySection of the course
     */
    public void setFacultySection(String facultySection) {
        this.facultySection = facultySection;
    }
}
