package org.example.springproject.services.implementation;

import org.example.springproject.entity.Course;
import org.example.springproject.enums.FacultySection;
import org.example.springproject.repository.CourseRepository;
import org.example.springproject.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository repository;

    /**
     * Method to get all courses
     * @return a list of all courses
     */
    @Override
    public List<Course> getAllCourses() {
        return (List<Course>) repository.findAll();
    }

    /**
     * Method to add a new course
     * @param name of the course to be added
     * @param category of the course to be added
     * @param studyYear of the course to be added
     * @param teacher of the course to be added
     * @param maxCapacity of the course to be added
     * @param facultySection of the course to be added
     * @return a response entity with a message
     */
    @Override
    public Course addCourse(String name, String category, Integer studyYear, String teacher, Integer maxCapacity, FacultySection facultySection){
        try{
            Course newCourse = new Course(name,category,studyYear,teacher,maxCapacity,facultySection);

            return repository.save(newCourse);

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method to delete a course
     * @param id of the course to be deleted
     * @return a response entity with a message
     */
    @Override
    public void deleteCourse(Long id){
        try{
            Course courseToBeDeleted = repository.findCourseById(id);
//            if(courseToBeDeleted == null){
//                return ResponseEntity.badRequest().body("Course with id: " + id + " not found!");
//            }
            repository.deleteById(id);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method to update a course
     * @param id of the course to be updated
     * @param name new name of the course
     * @param category new category of the course
     * @param studyYear new study year of the course
     * @param teacher new teacher of the course
     * @param maxCapacity new max capacity of the course
     * @param facultySection new faculty section of the course
     * @return a response entity with a message
     */
    @Override
    public Course updateCourse(Long id,String name, String category, Integer studyYear, String teacher, Integer maxCapacity, FacultySection facultySection){
        try{
            Course courseToBeUpdated = repository.findCourseById(id);
//            if(courseToBeUpdated == null){
//                return ResponseEntity.badRequest().body("Course with the id: " + id + " not found!");
//            }
            courseToBeUpdated.setName(name);
            courseToBeUpdated.setCategory(category);
            courseToBeUpdated.setStudyYear(studyYear);
            courseToBeUpdated.setTeacher(teacher);
            courseToBeUpdated.setMaxCapacity(maxCapacity);
            courseToBeUpdated.setFacultySection(facultySection);

            return repository.save(courseToBeUpdated);

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}