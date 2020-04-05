package ru.adv2ls.communication.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.util.*

@Configuration
class SecretConfiguration {
    @Bean
    @Throws(Exception::class)
    fun keyPair(@Value("\${jwt.token.secret}") secret: String): KeyPair {
        val baseString = Base64.getEncoder().encodeToString(secret.toByteArray()).toByteArray()
        val generator = KeyPairGenerator.getInstance("RSA")
        generator.initialize(2048, SecureRandom(baseString))

        return generator.genKeyPair()
    }
}