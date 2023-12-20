package com.example.integrationtests.controller.cors.withjson

import com.example.integrationtests.TestConfigs
import com.example.integrationtests.testcontainers.AbstractIntegrationTest
import com.example.integrationtests.vo.PersonVO
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.LogDetail
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerCorsWithJson() : AbstractIntegrationTest() {

	private lateinit var specification: RequestSpecification
	private lateinit var objectMapper: ObjectMapper
	private lateinit var person: PersonVO

	@BeforeAll
	fun setupTests() {
		objectMapper = ObjectMapper()
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

		person = PersonVO()
	}

	@Test
	@Order(1)
	fun testCreate() {

		mockPerson()

		specification = RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_EXAMPLE)
			.setBasePath("/api/persons/v1")
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(RequestLoggingFilter(LogDetail.ALL))
			.addFilter(ResponseLoggingFilter(LogDetail.ALL))
			.build()

		val content = given()
			.spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.body(person)
				.`when`()
			.post()
			.then()
				.statusCode(200)
			.extract()
			.body()
				.asString()

		val createdPerson = objectMapper.readValue(content, PersonVO::class.java)

		person = createdPerson

		assertNotNull(createdPerson.id)
		assertNotNull(createdPerson.firstName)
		assertNotNull(createdPerson.lastName)
		assertNotNull(createdPerson.address)
		assertNotNull(createdPerson.gender)

		assertTrue(createdPerson.id > 0)

		assertEquals("Nelson", createdPerson.firstName)
		assertEquals("Piquet", createdPerson.lastName)
		assertEquals("Brasilia, DF, Brasil", createdPerson.address)
		assertEquals("Male", createdPerson.gender)
	}

	@Test
	@Order(2)
	fun testCreateWithWrongOrigin() {

		mockPerson()

		specification = RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_EXAMPLES)
			.setBasePath("/api/persons/v1")
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(RequestLoggingFilter(LogDetail.ALL))
			.addFilter(ResponseLoggingFilter(LogDetail.ALL))
			.build()

		val content = given()
			.spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.body(person)
				.`when`()
			.post()
			.then()
				.statusCode(403)
			.extract()
			.body()
				.asString()

		assertEquals("Invalid CORS request", content)
	}

	@Test
	@Order(3)
	fun testFindById() {

		mockPerson()

		specification = RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL_HOST)
			.setBasePath("/api/persons/v1")
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(RequestLoggingFilter(LogDetail.ALL))
			.addFilter(ResponseLoggingFilter(LogDetail.ALL))
			.build()

		val content = given()
			.spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", person.id)
			.`when`()["{id}"]
			.then()
			.statusCode(200)
			.extract()
			.body()
			.asString()

		val createdPerson = objectMapper.readValue(content, PersonVO::class.java)

		assertNotNull(createdPerson.id)
		assertNotNull(createdPerson.firstName)
		assertNotNull(createdPerson.lastName)
		assertNotNull(createdPerson.address)
		assertNotNull(createdPerson.gender)

		assertTrue(createdPerson.id > 0)

		assertEquals("Nelson", createdPerson.firstName)
		assertEquals("Piquet", createdPerson.lastName)
		assertEquals("Brasilia, DF, Brasil", createdPerson.address)
		assertEquals("Male", createdPerson.gender)
	}

	@Test
	@Order(4)
	fun testFindByIdWithWrongOrigin() {

		mockPerson()

		specification = RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_EXAMPLES)
			.setBasePath("/api/persons/v1")
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(RequestLoggingFilter(LogDetail.ALL))
			.addFilter(ResponseLoggingFilter(LogDetail.ALL))
			.build()

		val content = given()
			.spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", person.id)
			.`when`()["{id}"]
			.then()
			.statusCode(403)
			.extract()
			.body()
			.asString()

		assertEquals("Invalid CORS request", content)
	}

	private fun mockPerson() {
		person.firstName = "Nelson"
		person.lastName = "Piquet"
		person.address = "Brasilia, DF, Brasil"
		person.gender = "Male"
	}

}