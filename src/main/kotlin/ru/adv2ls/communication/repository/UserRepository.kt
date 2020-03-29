package ru.adv2ls.communication.repository

import org.springframework.data.jpa.repository.JpaRepository;
import ru.adv2ls.communication.entity.User

interface UserRepository : JpaRepository<User?, Long?> {
    fun findByUsername(username: String): User?
}