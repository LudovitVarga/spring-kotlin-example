package com.github.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.example.data.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, String> {

}
