package com.example.services

import com.example.controller.PersonController
import com.example.data.vo.v1.PersonVO
import com.example.exceptions.ResourceNotFoundException
import com.example.mapper.DozerMapper
import com.example.model.Person
import com.example.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var personRepository: PersonRepository

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<PersonVO> {
        logger.info("Finding All person!")
        val persons = personRepository.findAll()
        return DozerMapper.parseListObjects(persons, PersonVO::class.java)
    }

    fun findById(id: Long) : PersonVO {
        logger.info("Finding one person!")
        val person = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this Id") }

        val personVO: PersonVO = DozerMapper.parseObject(person, PersonVO::class.java)

        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun create(person: PersonVO): PersonVO {
        logger.info("Creating one person!")
        var entity: Person = DozerMapper.parseObject(person, Person::class.java)

        return DozerMapper.parseObject(personRepository.save(entity), PersonVO::class.java)
    }

    fun update(person: PersonVO): PersonVO {
        logger.info("Trying update person!")
        val entity = personRepository.findById(person.key)
            .orElseThrow { ResourceNotFoundException("No records found for this Id") }

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender

        return DozerMapper.parseObject(personRepository.save(entity), PersonVO::class.java)
    }

    fun delete(id: Long) {
        logger.info("Deleting one person!")
        val entity = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this Id") }
        personRepository.delete(entity)
    }

}