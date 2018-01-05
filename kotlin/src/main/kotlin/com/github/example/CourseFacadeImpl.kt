package com.github.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.Assert

import com.github.example.data.Course
import com.github.example.exception.AlreadyExistsException
import com.github.example.exception.NotFoundException
import com.github.example.repository.CourseRepository

@Component
class CourseFacadeImpl(val courseRepository: CourseRepository) : CourseFacade {

	override fun createCourse(course: Course): Course {
		Assert.notNull(course, "The given Course must not be null!")
		course.id?.let {
			if (courseRepository.exists(course.id)) {
				throw AlreadyExistsException("Course with given id already exists")
			}			
		}
		return courseRepository.save(course)
	}

	override fun getCourse(courseId: String): Course {
		Assert.notNull(courseId, "The given CourseId must not be null!")
		val course = courseRepository.findOne(courseId)
		if (course == null) {
			throw NotFoundException("Course not found")
		}
		return course
	}

	override fun updateCourse(course: Course): Course {
		Assert.notNull(course, "The given Course must not be null!")
		if (!courseRepository.exists(course.id)) {
			throw NotFoundException("Course not found")
		}
		return courseRepository.save(course);
	}

}
