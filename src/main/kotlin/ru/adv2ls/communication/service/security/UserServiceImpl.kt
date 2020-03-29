package ru.adv2ls.communication.service.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import ru.adv2ls.communication.entity.User
import ru.adv2ls.communication.repository.UserRepository


@Service
class UserServiceImpl: UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder

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