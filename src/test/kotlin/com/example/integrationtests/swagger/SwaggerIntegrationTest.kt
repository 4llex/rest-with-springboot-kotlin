package com.example.integrationtests.swagger

import com.example.integrationtests.TestConfigs
import com.example.integrationtests.testcontainers.AbstractIntegrationTest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest() : AbstractIntegrationTest() {

	@Test
	fun shouldDisplaySwaggerUiPage() {
		val content = given()
			.basePath("/swagger-ui/index.html")
			.port(TestConfigs.SERVER_PORT)
				.`when`()
			.get()
			.then()
				.statusCode(200)
			.extract()
			.body()
				.asString()
		assertTrue(content.contains("Swagger UI"))
	}

}