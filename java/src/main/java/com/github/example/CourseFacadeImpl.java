package com.github.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.github.example.data.Course;
import com.github.example.exception.AlreadyExistsException;
import com.github.example.exception.NotFoundException;
import com.github.example.repository.CourseRepository;

@Component
public class CourseFacadeImpl implements CourseFacade {

	@Autowired
	private CourseRepository courseRepository;

	@Override
	public Course createCourse(Course course) throws AlreadyExistsException {
		Assert.notNull(course, "The given Course must not be null!");
		if (course.getId() != null && courseRepository.exists(course.getId())) {
			throw new AlreadyExistsException("Course with given id already exists");
		}
		return courseRepository.save(course);
	}

	@Override
	public Course getCourse(String courseId) throws NotFoundException {
		Assert.notNull(courseId, "The given CourseId must not be null!");
		Course course = courseRepository.findOne(courseId);
		if (course == null) {
			throw new NotFoundException("Course not found");
		}
		return course;
	}

	@Override
	public Course updateCourse(Course course) throws NotFoundException {
		Assert.notNull(course, "The given Course must not be null!");
		if (!courseRepository.exists(course.getId())) {
			throw new NotFoundException("Course not found");
		}
		return courseRepository.save(course);
	}

}
