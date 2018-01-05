package com.github.example

import com.github.example.data.Course

interface CourseFacade {

	fun createCourse(course: Course): Course

	fun getCourse(courseId: String): Course

	fun updateCourse(course: Course): Course

}
