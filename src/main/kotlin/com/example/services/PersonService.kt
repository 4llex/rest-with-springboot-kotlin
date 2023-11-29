package com.example.services

import com.example.model.Person
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger

@Service
class PersonService {

    val counter: AtomicLong = AtomicLong()

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<Person> {
        logger.info("Finding All person!")

        val persons: MutableList<Person> = ArrayList()
        for (i in 0..7 ){
            val person = mockPerson(i)
            persons.add(person)
        }

        return persons
    }

    fun findById(id: Long) : Person {
        logger.info("Finding one person!")

        val person = Person(
            counter.incrementAndGet(),
            "Alex",
            "Rosa",
            "Cambui-MG",
            "Male"
        )
        return person
    }

    fun create(person: Person): Person {

        return person
    }

    fun update(person: Person): Person {

        return person
    }

    fun delete(id: Long) {

    }


    private fun mockPerson(i: Int): Person {
        val person = Person(
            counter.incrementAndGet(),
            "Alex $i",
            "Rosa $i",
            "Cambui-MG $i",
            "Male $i"
        )
        return person

    }
}