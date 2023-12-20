package com.example.services

import com.example.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class UserService(
    @field:Autowired var repository: UserRepository
): UserDetailsService {

    private val logger = Logger.getLogger(UserService::class.java.name)

    override fun loadUserByUsername(username: String?): UserDetails {
        logger.info("Finding on User with UserName $username")

        val user = repository.findByUserName(username)
        return user ?: throw UsernameNotFoundException("UserName $username not found!")
    }

}