package ru.adv2ls.communication.service.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.adv2ls.communication.repository.UserRepository
import java.util.*


@Service
class UserDetailsServiceImpl: UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Override
    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)
        val grantedAuthorities: MutableSet<GrantedAuthority> = HashSet()
//        for (role in user.getRoles()) {
//            grantedAuthorities.add(SimpleGrantedAuthority(role.getName()))
//        }

        return User(user.username, user.password, grantedAuthorities)
    }
}