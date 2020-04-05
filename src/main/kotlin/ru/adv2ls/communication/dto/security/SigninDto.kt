package ru.adv2ls.communication.dto.security

import javax.validation.constraints.NotBlank

class SigninDto(
        @NotBlank val username: String,
        @NotBlank val password: String
)