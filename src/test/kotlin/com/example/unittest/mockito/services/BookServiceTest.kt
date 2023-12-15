package com.example.unittest.mockito.services

import com.example.exceptions.RequiredObjectsNullException
import com.example.repository.BookRepository
import com.example.services.BookService
import com.example.unittest.mocks.MockBook
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class BookServiceTest {

    private lateinit var inputObject: MockBook

    @InjectMocks
    private lateinit var service: BookService
    @Mock
    private lateinit var repository: BookRepository


    @BeforeEach
    fun setUpMock() {
        inputObject = MockBook()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findAll() {
        val list = inputObject.mockEntityList()
        `when`(repository.findAll()).thenReturn(list)

        val books = service.findAll()

        assertNotNull(books)
        assertEquals(14, books.size)


        val bookOne = books[1]
        assertNotNull(bookOne)
        assertNotNull(bookOne.key)
        assertNotNull(bookOne.links)
        assertTrue(bookOne.links.toString().contains("</api/books/v1/1>;rel=\"self\""))
        assertEquals("Some Author1", bookOne.author)
        assertEquals(25.0, bookOne.price)
        assertEquals("Some Title1", bookOne.title)

        val bookFour = books[4]
        assertNotNull(bookFour)
        assertNotNull(bookFour.key)
        assertNotNull(bookFour.links)
        assertTrue(bookFour.links.toString().contains("</api/books/v1/4>;rel=\"self\""))
        assertEquals("Some Author4", bookFour.author)
        assertEquals(25.0, bookFour.price)
        assertEquals("Some Title4", bookFour.title)

        val bookSeven = books[7]
        assertNotNull(bookSeven)
        assertNotNull(bookSeven.key)
        assertNotNull(bookSeven.links)
        assertTrue(bookSeven.links.toString().contains("</api/books/v1/7>;rel=\"self\""))
        assertEquals("Some Author7", bookSeven.author)
        assertEquals(25.0, bookSeven.price)
        assertEquals("Some Title7", bookSeven.title)
    }

    @Test
    fun findById() {
        val book = inputObject.mockEntity(1)
        book.id = 1L
        `when`(repository.findById(1)).thenReturn(Optional.of(book))

        val result = service.findById(1)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)

        println(result.links)
        assertTrue(result.links.toString().contains("</api/books/v1/1>;rel=\"self\""))
        assertEquals("Some Author1", result.author)
        assertEquals(25.0, result.price)
        assertEquals("Some Title1", result.title)
    }

    @Test
    fun create() {
        val entity = inputObject.mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        `when`(repository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVO(1)
        val result = service.create(vo)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        println(result.links)
        assertTrue(result.links.toString().contains("</api/books/v1/1>;rel=\"self\""))
        assertEquals("Some Author1", result.author)
        assertEquals(25.0, result.price)
        assertEquals("Some Title1", result.title)
    }

    @Test
    fun createWithNullBook() {
        val exception: Exception = assertThrows(
            RequiredObjectsNullException::class.java
        ) { service.create(null) }

        val expectedMessage = "t is not allowed to persost a null object"
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun updateWithNullBook() {
        val exception: Exception = assertThrows(
            RequiredObjectsNullException::class.java
        ) { service.update(null) }

        val expectedMessage = "t is not allowed to persost a null object"
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun update() {
        val entity = inputObject.mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        `when`(repository.findById(1)).thenReturn(Optional.of(entity))
        `when`(repository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVO(1)
        val result = service.update(vo)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        println(result.links)
        assertTrue(result.links.toString().contains("</api/books/v1/1>;rel=\"self\""))
        assertEquals("Some Author1", result.author)
        assertEquals(25.0, result.price)
        assertEquals("Some Title1", result.title)
    }

    @Test
    fun delete() {
        val entity = inputObject.mockEntity(1)
        `when`(repository.findById(1)).thenReturn(Optional.of(entity))
        service.delete(1)
    }
}