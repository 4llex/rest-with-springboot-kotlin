package com.example.config

import com.example.serialization.converter.YamlJackson2HttpMessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    //pega as origens CORS definidas no application.yaml
    @Value("\${cors.originPatterns:default}")
    private  val corsOriginPatterns: String = ""

    private val MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf( "application/x-yaml")

    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.add(YamlJackson2HttpMessageConverter())
    }

    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
        // Content Negotiation via QueryParam
        configurer.favorParameter(true)
            .parameterName("mediaType")
            .ignoreAcceptHeader(true)
            .useRegisteredExtensionsOnly(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML)
        // ------------------------------------------------------------------

        // Content Negotiation via Header - mais flexivel no POST request
//        configurer.favorParameter(false)
//            .ignoreAcceptHeader(false)
//            .useRegisteredExtensionsOnly(false)
//            .defaultContentType(MediaType.APPLICATION_JSON)
//            .mediaType("json", MediaType.APPLICATION_JSON)
//            .mediaType("xml", MediaType.APPLICATION_XML)
//            .mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML)
    }


    //Congiura CORS global
    override fun addCorsMappings(registry: CorsRegistry) {
        val allowedOrigins = corsOriginPatterns.split(",").toTypedArray()
        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOrigins(*allowedOrigins)
            .allowCredentials(true)
    }

}