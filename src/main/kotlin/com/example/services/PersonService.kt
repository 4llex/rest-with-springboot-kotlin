package com.example.services

import com.example.exceptions.ResourceNotFoundException
import com.example.model.Person
import com.example.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var personRepository: PersonRepository

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<Person> {
        logger.info("Finding All person!")

        return personRepository.findAll()
    }

    fun findById(id: Long) : Person {
        logger.info("Finding one person!")
        return personRepository.findById(id).orElseThrow { ResourceNotFoundException("No records found for this Id") }
    }

    fun create(person: Person): Person {
        logger.info("Creating one person!")
        return personRepository.save(person)
    }

    fun update(person: Person): Person {
        logger.info("Trying update person!")
        val entity = personRepository.findById(person.id)
            .orElseThrow { ResourceNotFoundException("No records found for this Id") }

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender

        return personRepository.save(entity)
    }

    fun delete(id: Long) {
        logger.info("Deleting one person!")
        val entity = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this Id") }
        personRepository.delete(entity)
    }

}