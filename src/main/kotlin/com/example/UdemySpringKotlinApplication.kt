package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder

@SpringBootApplication
class UdemySpringKotlinApplication

fun main(args: Array<String>) {
	runApplication<UdemySpringKotlinApplication>(*args)


	val encoders: MutableMap<String, PasswordEncoder> = HashMap()
	val pbkdf2Encoder = Pbkdf2PasswordEncoder("", 8, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256)
	encoders["pbkdf2"] = pbkdf2Encoder
	val passwordEncoder = DelegatingPasswordEncoder("pbkdf2", encoders)
	passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder)

	val alexross = passwordEncoder.encode("123456")
	println("alexross(password) hash $alexross")

	val leandro = passwordEncoder.encode("user123")
	println("leandro(password) hash $leandro")

	val flavio = passwordEncoder.encode("02468")
	println("flavio(password) hash $flavio")

	val laura = passwordEncoder.encode("rootadmin")
	println("laura(password) hash $laura")

}
