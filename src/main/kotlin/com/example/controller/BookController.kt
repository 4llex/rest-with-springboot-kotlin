package com.example.controller

import com.example.data.vo.v1.BookVO
import com.example.services.BookService
import com.example.util.MediaType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/books/v1")
@Tag(name = "Book", description = "Endpoints for managing Book")
class BookController(
) {

    @Autowired
    private lateinit var bookService: BookService


    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Finds all Books", description = "Finds all Books", tags = ["Book"],
        responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [Content(array = ArraySchema(schema = Schema(implementation = BookVO::class)))]
            ),
            ApiResponse(
                description = "No Content", responseCode = "204", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [Content(schema = Schema(implementation = Unit::class))]
            )
        ]
    )
    fun findAll(): List<BookVO> {
        return bookService.findAll()
    }

    @GetMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Finds a Book", description = "Finds a Book", tags = ["Book"],
        responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [Content(schema = Schema(implementation = BookVO::class))]
            ),
            ApiResponse(
                description = "No Content", responseCode = "204", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [Content(schema = Schema(implementation = Unit::class))]
            )
        ]
    )
    fun findById(@PathVariable(value = "id") id: Long): BookVO {
        return bookService.findById(id)
    }

    @PostMapping(
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Adds a new Book", description = "Adds a new Book", tags = ["Book"],
        responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [Content(schema = Schema(implementation = BookVO::class))]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [Content(schema = Schema(implementation = Unit::class))]
            )
        ]
    )
    fun create(@RequestBody book: BookVO): BookVO {
        return bookService.create(book)
    }

    @PutMapping(
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Updates a book's information", description = "Updates a Book's information", tags = ["Book"],
        responses = [
            ApiResponse(
                description = "Success", responseCode = "200", content = [Content(schema = Schema(implementation = BookVO::class))]
            ),
            ApiResponse(
                description = "No Content", responseCode = "204", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [Content(schema = Schema(implementation = Unit::class))]
            )
        ]
    )
    fun update(@RequestBody book: BookVO): BookVO {
        return bookService.update(book)
    }

    @DeleteMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Deletes a book", description = "Deletes a Book", tags = ["Book"],
        responses = [
            ApiResponse(
                description = "No Content", responseCode = "204", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Bad Request", responseCode = "400", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Unauthorized", responseCode = "401", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Not Found", responseCode = "404", content = [Content(schema = Schema(implementation = Unit::class))]
            ),
            ApiResponse(
                description = "Internal Error", responseCode = "500", content = [Content(schema = Schema(implementation = Unit::class))]
            )
        ]
    )
    fun delete(@PathVariable(value = "id") id: Long): ResponseEntity<*> {
        bookService.delete(id)
        return ResponseEntity.noContent().build<Any>()
    }

}