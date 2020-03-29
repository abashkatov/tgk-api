package ru.adv2ls.communication.service.security

import ru.adv2ls.communication.entity.User

interface UserService {
    fun save(user: User)

    fun findByUsername(username: String): User?
}