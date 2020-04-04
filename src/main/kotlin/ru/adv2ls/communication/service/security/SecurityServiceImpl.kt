package ru.adv2ls.communication.service.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class SecurityServiceImpl : SecurityService {
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Override
    override fun findLoggedInUsername(): String? {
        val userDetails = SecurityContextHolder.getContext().authentication.details
        return if (userDetails is UserDetails) {
            (userDetails as UserDetails).username
        } else null

    }

    @Override
    override fun autoLogin(username: String?, password: String?) {
        val userDetails = userDetailsService.loadUserByUsername(username)
        val authenticationToken = UsernamePasswordAuthenticationToken(userDetails, password, userDetails.authorities)

        authenticationManager.authenticate(authenticationToken)

        if (authenticationToken.isAuthenticated) {
            SecurityContextHolder.getContext().authentication = authenticationToken
        }
    }
}