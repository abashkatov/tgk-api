package ru.adv2ls.communication.service.security

interface SecurityService {
    fun findLoggedInUsername(): String?
    fun autoLogin(username: String?, password: String?)
}