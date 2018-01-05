package com.github.example.data

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.validation.constraints.NotNull

@Entity
data class Course(@Id @GeneratedValue(strategy = GenerationType.AUTO) var id: String? = null) {

	@NotNull
	var name: String? = null

	@OneToMany(mappedBy = "courseId")
	var students: List<Student> = emptyList()

}
