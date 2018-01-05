package com.github.example;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.example.data.Course;
import com.github.example.exception.AlreadyExistsException;
import com.github.example.exception.NotFoundException;

@RestController
public class CourseController {

	@Autowired
	private CourseFacade courseFacade;

	@PostMapping("/courses")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Course createCourse(@NotNull @Valid @RequestBody Course course) throws AlreadyExistsException {
		return courseFacade.createCourse(course);
	}

	@GetMapping("/courses/{courseId}")
	@ResponseBody
	public Course getCourse(@NotNull @PathVariable("courseId") String courseId) throws NotFoundException {
		return courseFacade.getCourse(courseId);
	}

	@PostMapping("/courses/{courseId}")
	@ResponseBody
	public Course updateCourse(@NotNull @Valid @RequestBody Course course) throws NotFoundException {
		return courseFacade.updateCourse(course);
	}

}
