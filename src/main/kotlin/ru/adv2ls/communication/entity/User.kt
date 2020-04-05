package ru.adv2ls.communication.entity

import ru.adv2ls.communication.dto.UserDto
import javax.persistence.*

@Entity(name = "tgk_users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        @Column(nullable = false)
        var username: String,
        @Column(nullable = false)
        var email: String,
        @Column(nullable = false)
        var password: String
) {
    constructor(userDto: UserDto) : this(
            id = 0,
            username = userDto.username,
            email = userDto.email,
            password = userDto.password
    )
}