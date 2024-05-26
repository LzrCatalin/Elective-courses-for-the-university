package org.example.springproject.entity;

import jakarta.persistence.*;
import org.example.springproject.enums.WeekDay;
import org.example.springproject.enums.WeekParity;


@Entity
@Table(name = "courseSchedule")
public class CourseSchedule {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn (name = "courseId")
    private Course course;

    @Column(name = "startTime")
    private String startTime;
    @Column(name = "endTime")
    private String endTime;
    @Column(name = "weekDay")
    @Enumerated(EnumType.STRING)
    private WeekDay weekDay;
    @Column(name = "weekParity")
    @Enumerated(EnumType.STRING)
    private WeekParity weekParity;

    public CourseSchedule() {}
    public CourseSchedule(Long id, Course courseData, String startTime, String endTime,WeekDay weekDay, WeekParity weekParity) {
        this.id = id;
        course = courseData;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekDay = weekDay;
        this.weekParity = weekParity;
    }
    public CourseSchedule(Course courseDetails, String courseStartTime, String courseEndTime, WeekDay courseWeekDay,WeekParity courseWeekParity) {
        course = courseDetails;
        this.startTime = courseStartTime;
        this.endTime = courseEndTime;
        this.weekDay = courseWeekDay;
        this.weekParity = courseWeekParity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course newCourse) {
        this.course = newCourse;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public WeekParity getWeekParity() {
        return weekParity;
    }

    public void setWeekParity(WeekParity weekParity) {
        this.weekParity = weekParity;
    }
}
