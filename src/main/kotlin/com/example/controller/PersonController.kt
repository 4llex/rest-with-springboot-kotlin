package com.example.controller

import com.example.model.Person
import com.example.services.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("persons")
class PersonController(
) {

    @Autowired
    private lateinit var personService: PersonService

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findAll(
    ): List<Person> {
        return personService.findAll()
    }

    @GetMapping(value = ["/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findById(
        @PathVariable(value = "id") id: Long
    ): Person {
        return personService.findById(id)
    }

    @PostMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(
        @RequestBody person: Person
    ): Person {
        return personService.create(person)
    }

    @PutMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun update(
        @RequestBody person: Person
    ) {
        personService.update(person)
    }

    @DeleteMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun delete(
        @PathVariable(value = "id") id: Long
    ): ResponseEntity<*> {
        personService.delete(id)
        return ResponseEntity.noContent().build<Any>()
    }

}