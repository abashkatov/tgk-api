package ru.adv2ls.communication.service.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import ru.adv2ls.communication.entity.User

interface SecurityService {
    fun findLoggedInUsername(): String?
    fun signIn(user: User, password: String): UsernamePasswordAuthenticationToken
    fun getUserDetails(user: User): UserDetails
}