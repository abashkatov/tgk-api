package ru.adv2ls.communication.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UserDto (
        @NotBlank
        @Size(min=4, max = 32)
        var username: String,
        @Email
        var email: String,
        @NotBlank
        @Size(min=6, max = 32)
        var password: String,
        @NotBlank
        @Size(min=6, max = 32)
        var passwordConfirm: String
)