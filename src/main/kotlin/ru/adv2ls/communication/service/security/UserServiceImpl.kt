package ru.adv2ls.communication.service.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import ru.adv2ls.communication.entity.User
import ru.adv2ls.communication.repository.UserRepository


@Service
class UserServiceImpl(
        @Autowired private val userRepository: UserRepository,
        @Autowired private val bCryptPasswordEncoder: BCryptPasswordEncoder
) : UserService {

    @Override
    override fun save(user: User) {
        user.password = bCryptPasswordEncoder.encode(user.password)
        userRepository.save<User>(user)
    }

    @Override
    override fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

}