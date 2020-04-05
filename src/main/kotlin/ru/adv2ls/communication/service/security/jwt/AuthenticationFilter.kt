package ru.adv2ls.communication.service.security.jwt

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


class AuthenticationFilter(
        private val tokenProvider: TokenProvider
) : GenericFilterBean() {


    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        if (request == null || chain == null) {
            chain!!.doFilter(request, response)
        }
        val token: String? = tokenProvider.resolveToken(request as HttpServletRequest)

        if (token != null && tokenProvider.validateToken(token)) {
            val auth: Authentication = tokenProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = auth
        }

        chain.doFilter(request, response)
    }
}