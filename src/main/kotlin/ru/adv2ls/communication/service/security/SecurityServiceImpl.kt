package ru.adv2ls.communication.service.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import ru.adv2ls.communication.entity.User
import java.util.*


@Service
class SecurityServiceImpl(
        @Autowired private val authenticationManager: AuthenticationManager,
        @Qualifier("userDetailsServiceImpl") @Autowired private val userDetailsService: UserDetailsService
) : SecurityService {

    override fun findLoggedInUsername(): String? {
        val userDetails = SecurityContextHolder.getContext().authentication.details
        return if (userDetails is UserDetails) {
            (userDetails as UserDetails).username
        } else null

    }

    @Override
    override fun signIn(user: User, password: String): UsernamePasswordAuthenticationToken {
        val userDetails = getUserDetails(user)
        val authenticationToken = UsernamePasswordAuthenticationToken(userDetails, password, userDetails.authorities)
        authenticationManager.authenticate(authenticationToken)

        if (authenticationToken.isAuthenticated) {
            SecurityContextHolder.getContext().authentication = authenticationToken
        }

        return authenticationToken;
    }

    override fun getUserDetails(user: User): UserDetails {
        val grantedAuthorities: MutableSet<GrantedAuthority> = HashSet()
        return org.springframework.security.core.userdetails.User(
                user.username,
                user.password,
                grantedAuthorities
        )
    }
}