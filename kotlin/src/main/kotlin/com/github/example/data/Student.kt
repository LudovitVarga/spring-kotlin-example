package com.github.example.data

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
data class Student(@Id @GeneratedValue(strategy = GenerationType.AUTO) var id: String? = null) {

	@NotNull
	var courseId: String? = null

	@NotNull
	var name: String? = null

	@Enumerated(EnumType.STRING)
	var grade: Grade? = null

}
