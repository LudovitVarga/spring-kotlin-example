package com.github.example;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import com.github.example.data.Course

@RestController
class CourseController(val courseFacade : CourseFacade) {

	@PostMapping("/courses")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	fun createCourse(@NotNull @Valid @RequestBody course: Course) = courseFacade.createCourse(course)

	@GetMapping("/courses/{courseId}")
	@ResponseBody
	fun getCourse(@NotNull @PathVariable("courseId") courseId: String) = courseFacade.getCourse(courseId)

	@PostMapping("/courses/{courseId}")
	@ResponseBody
	fun addNewStudent(@NotNull @Valid @RequestBody course: Course) = courseFacade.updateCourse(course)

}
