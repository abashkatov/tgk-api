package ru.adv2ls.communication.controller.security

import javassist.tools.web.BadHttpRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import ru.adv2ls.communication.dto.UserDto
import ru.adv2ls.communication.dto.security.SigninDto
import ru.adv2ls.communication.dto.security.TokenDto
import ru.adv2ls.communication.entity.User
import ru.adv2ls.communication.repository.UserRepository
import ru.adv2ls.communication.service.security.SecurityService
import ru.adv2ls.communication.service.security.UserService
import ru.adv2ls.communication.service.security.jwt.TokenProvider
import ru.adv2ls.communication.validator.UserValidator
import javax.validation.Valid


@RestController
class SecurityController(
        @Autowired private val userService: UserService,
        @Autowired private val userValidator: UserValidator,
        @Autowired private val securityService: SecurityService,
        @Autowired private val tokenProvider: TokenProvider,
        @Qualifier("userDetailsServiceImpl") @Autowired private val userDetailsService: UserDetailsService,
        @Autowired private val authenticationManager: AuthenticationManager,
        @Autowired private val userRepository: UserRepository

) {

    @ResponseBody
    @PostMapping("/login", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun login(@RequestBody @Valid signinDto: SigninDto): TokenDto {
        val user = userRepository.findByUsername(signinDto.username)
                ?: throw UsernameNotFoundException(signinDto.username)
        val authenticationToken = securityService.signIn(user, signinDto.password)

        if (!authenticationToken.isAuthenticated) {
            throw BadCredentialsException("Bad credentials")
        }

        return TokenDto(userName = signinDto.username, token = tokenProvider.createToken(user))
    }

    @ResponseBody
    @PostMapping("/registration", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun registration(@RequestBody @Valid userDto: UserDto, bindingResult: BindingResult): TokenDto {
        userValidator.validate(userDto, bindingResult)
        if (bindingResult.hasErrors()) {
            throw BadCredentialsException("Bad credentials")
        }
        val user: User = User(userDto)
        userService.save(user)
        val authenticationToken = securityService.signIn(user, userDto.password)
        if (!authenticationToken.isAuthenticated) {
            throw BadHttpRequest()
        }

        return TokenDto(userName = user.username, token = tokenProvider.createToken(user))
    }
}