package com.github.example;

import com.github.example.data.Course;
import com.github.example.exception.AlreadyExistsException;
import com.github.example.exception.NotFoundException;

public interface CourseFacade {

	Course createCourse(Course course) throws AlreadyExistsException;

	Course getCourse(String courseId) throws NotFoundException;

	Course updateCourse(Course course) throws NotFoundException;

}
