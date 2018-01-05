package com.github.example

import org.assertj.core.api.Assertions.assertThat

import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringRunner

import com.github.example.data.Course

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ApplicationTests {

	val LOG: Logger = LoggerFactory.getLogger(ApplicationTests::class.java)

	@Autowired
	lateinit var restTemplate: TestRestTemplate 

	@Test
	fun createCourse_newCourse_shouldCreateCourse() {
		val course = Course()
		course.name = "Test course"

		val response = restTemplate.postForEntity("/courses", course, Course::class.java)
		LOG.info("Response is {}", response)

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED)
		assertThat(response.getBody()).isNotNull()
		assertThat(response.getBody().id).isNotNull()
		assertThat(response.getBody().name).isEqualTo("Test course")
		assertThat(response.getBody().students).isEmpty()
	}

	@Test
	fun createCourse_existingCourseId_shouldFail() {
		val course = Course()
		course.id = "1"
		course.name = "Test course"

		val response = restTemplate.postForEntity("/courses", course, Course::class.java)
		LOG.info("Response is {}", response)

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
	}

	@Test
	fun createCourse_null_shouldFail() {
	    val headers = HttpHeaders()
		headers.setContentType(MediaType.APPLICATION_JSON)
		val request = HttpEntity<Course>(null, headers)

		val response = restTemplate.postForEntity("/courses", request, Course::class.java)
		LOG.info("Response is {}", response)

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST)
	}

	@Test
	fun createCourse_notValidCourse_shouldFail() {
		val response = restTemplate.postForEntity("/courses", Course(), Course::class.java)
		LOG.info("Response is {}", response)

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST)
	}

	@Test
	fun getCourse_nonExistingCourseId_shouldReturnNothing() {
		val response = restTemplate.getForEntity("/courses/999", Course::class.java)
		LOG.info("Response is {}", response)

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND)
	}

	@Test
	fun getCourse_existingCourseId_shouldReturnSomething() {
		val response = restTemplate.getForEntity("/courses/1", Course::class.java)
		LOG.info("Response is {}", response)

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK)
		assertThat(response.getBody()).isNotNull()
		assertThat(response.getBody().name).isEqualTo("Spring course")
		assertThat(response.getBody().students).hasSize(3)
	}

	@Test
	fun updateCourse_existingCourse_shouldUpdateCourse() {
		val course = Course()
		course.id = "1"
		course.name = "New spring course"

		val response = restTemplate.postForEntity("/courses/1", course, Course::class.java)
		LOG.info("Response is {}", response)

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK)
		assertThat(response.getBody()).isNotNull()
		assertThat(response.getBody().id).isEqualTo("1")
		assertThat(response.getBody().name).isEqualTo("New spring course")
		assertThat(response.getBody().students).hasSize(3)
	}

	@Test
	fun updateCourse_nonExistingCourse_shouldUpdateFail() {
		val course = Course()
		course.id = "999"
		course.name = "New test course"

		val response = restTemplate.postForEntity("/courses/999", course, Course::class.java)
		LOG.info("Response is {}", response)

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND)
	}

}
