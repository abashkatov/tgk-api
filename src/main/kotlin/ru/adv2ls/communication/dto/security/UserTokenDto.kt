package ru.adv2ls.communication.dto.security

data class UserTokenDto(val userName: String, val email: String, val token: String)