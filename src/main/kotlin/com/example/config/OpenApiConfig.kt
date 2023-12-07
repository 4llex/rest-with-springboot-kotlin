package com.example.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("RESTful API with Kotlin and spring boot")
                    .version("v1")
                    .description("Some description here")
                    .termsOfService("Termos de serviços ...")
                    .license(
                        License()
                            .name("apache 2;0")
                            .url("https://license.com.br")
                    )
            )
    }

}