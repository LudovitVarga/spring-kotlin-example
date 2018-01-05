package com.github.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.example.data.Course;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationTests.class);

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void createCourse_newCourse_shouldCreateCourse() {
		Course course = new Course();
		course.setName("Test course");

		ResponseEntity<Course> response = restTemplate.postForEntity("/courses", course, Course.class);
		LOG.info("Response is {}", response);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isNotNull();
		assertThat(response.getBody().getName()).isEqualTo("Test course");
		assertThat(response.getBody().getStudents()).isEmpty();
	}

	@Test
	public void createCourse_existingCourseId_shouldFail() {
		Course course = new Course();
		course.setId("1");
		course.setName("Test course");

		ResponseEntity<Course> response = restTemplate.postForEntity("/courses", course, Course.class);
		LOG.info("Response is {}", response);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	public void createCourse_null_shouldFail() {
	    HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Course> request = new HttpEntity<>(null, headers);

		ResponseEntity<Course> response = restTemplate.postForEntity("/courses", request, Course.class);
		LOG.info("Response is {}", response);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void createCourse_notValidCourse_shouldFail() {
		ResponseEntity<Course> response = restTemplate.postForEntity("/courses", new Course(), Course.class);
		LOG.info("Response is {}", response);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void getCourse_nonExistingCourseId_shouldReturnNothing() {
		ResponseEntity<Course> response = restTemplate.getForEntity("/courses/999", Course.class);
		LOG.info("Response is {}", response);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void getCourse_existingCourseId_shouldReturnSomething() {
		ResponseEntity<Course> response = restTemplate.getForEntity("/courses/1", Course.class);
		LOG.info("Response is {}", response);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getName()).isEqualTo("Spring course");
		assertThat(response.getBody().getStudents()).hasSize(3);
	}

	@Test
	public void updateCourse_existingCourse_shouldUpdateCourse() {
		Course course = new Course();
		course.setId("1");
		course.setName("New spring course");

		ResponseEntity<Course> response = restTemplate.postForEntity("/courses/1", course, Course.class);
		LOG.info("Response is {}", response);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getId()).isEqualTo("1");
		assertThat(response.getBody().getName()).isEqualTo("New spring course");
		assertThat(response.getBody().getStudents()).hasSize(3);
	}

	@Test
	public void updateCourse_nonExistingCourse_shouldUpdateFail() {
		Course course = new Course();
		course.setId("999");
		course.setName("New test course");

		ResponseEntity<Course> response = restTemplate.postForEntity("/courses/999", course, Course.class);
		LOG.info("Response is {}", response);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
