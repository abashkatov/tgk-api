package ru.adv2ls.communication.service.security

import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import ru.adv2ls.communication.service.security.jwt.AuthenticationFilter
import ru.adv2ls.communication.service.security.jwt.TokenProvider


class Configurer(
        private val tokenProvider: TokenProvider
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        val jwtTokenFilter = AuthenticationFilter(tokenProvider)
        httpSecurity.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}