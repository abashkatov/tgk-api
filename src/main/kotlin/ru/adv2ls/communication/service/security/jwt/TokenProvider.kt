package ru.adv2ls.communication.service.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import ru.adv2ls.communication.dto.security.ClaimsDto
import ru.adv2ls.communication.entity.User
import java.security.KeyPair
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class TokenProvider(
        @Qualifier("userDetailsServiceImpl") @Autowired private val userDetailsService: UserDetailsService,
        @Autowired private val keyPair: KeyPair,
        @Value("\${jwt.token.expired}") private val tokenTTL: Long
) {

    fun createToken(user: User): String {
        val claimsDto = ClaimsDto(username = user.username, email = user.email)
        val claimsString = claimsDto.toString()

        val claims = Jwts.claims().setSubject(user.username)
        val now = Date()
        val validity = Date(now.time + tokenTTL)
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date())
                .setExpiration(validity)
                .signWith(keyPair.private)
                .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(getUsername(token))

        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token: String): String {
        return getClaims(token).body.subject
    }

    private fun getClaims(token: String): Jws<Claims> {
        return Jwts
                .parserBuilder()
                .setSigningKey(keyPair.public)
                .build()
                .parseClaimsJws(token)
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7, bearerToken.length)
        } else null
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims = getClaims(token)
            !claims.body.expiration.before(Date())
        } catch (e: JwtException) {
            throw JwtAuthenticationException("JWT token is expired or invalid")
        } catch (e: IllegalArgumentException) {
            throw JwtAuthenticationException("JWT token is expired or invalid")
        }
    }
}