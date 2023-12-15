package com.example.services

import com.example.controller.BookController
import com.example.data.vo.v1.BookVO
import com.example.exceptions.RequiredObjectsNullException
import com.example.exceptions.ResourceNotFoundException
import com.example.mapper.DozerMapper
import com.example.model.Book
import com.example.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class BookService {

    @Autowired
    private lateinit var bookRepository: BookRepository

    private val logger = Logger.getLogger(BookService::class.java.name)

    fun findAll(): List<BookVO> {
        logger.info("Finding All book!")
        val books = bookRepository.findAll()
        val vos = DozerMapper.parseListObjects(books, BookVO::class.java)

        for (book in vos) {
            val withSelfRel = linkTo(BookController::class.java).slash(book.key).withSelfRel()
            book.add(withSelfRel)
        }

        return vos
    }

    fun findById(id: Long) : BookVO {
        logger.info("Finding one book!")
        val book = bookRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this Id") }

        val bookVO: BookVO = DozerMapper.parseObject(book, BookVO::class.java)

        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)
        return bookVO
    }

    fun create(book: BookVO?): BookVO {
        if (book == null) {
            throw RequiredObjectsNullException()
        }

        logger.info("Creating one book eith title: ${book.title}!")
        var entity: Book = DozerMapper.parseObject(book, Book::class.java)

        val bookVO: BookVO = DozerMapper.parseObject(bookRepository.save(entity), BookVO::class.java)

        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)
        return bookVO
    }

    fun update(book: BookVO?): BookVO {
        if (book == null) {
            throw RequiredObjectsNullException()
        }

        logger.info("Trying update book!")
        val entity = bookRepository.findById(book.key)
            .orElseThrow { ResourceNotFoundException("No records found for this Id") }

        entity.author = book.author
        entity.title = book.title
        entity.price = book.price
        entity.launchDate = book.launchDate

        val bookVO: BookVO = DozerMapper.parseObject(bookRepository.save(entity), BookVO::class.java)

        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)
        return bookVO
    }

    fun delete(id: Long) {
        logger.info("Deleting one book!")
        val entity = bookRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No records found for this Id") }
        bookRepository.delete(entity)
    }

}