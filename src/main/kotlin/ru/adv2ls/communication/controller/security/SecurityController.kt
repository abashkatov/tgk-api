package ru.adv2ls.communication.controller.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import ru.adv2ls.communication.dto.UserDto
import ru.adv2ls.communication.entity.User
import ru.adv2ls.communication.service.security.SecurityService
import ru.adv2ls.communication.service.security.UserService
import ru.adv2ls.communication.validator.UserValidator
import javax.validation.Valid


@RestController
class SecurityController {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var securityService: SecurityService

    @Autowired
    private lateinit var userValidator: UserValidator

    @ResponseBody
    @RequestMapping("/login")
    fun login(): String {
        return "{\"result\":\"login\"}";
    }

    @ResponseBody
    @PostMapping("/registration")
    fun registration(@RequestBody @Valid userDto: UserDto, bindingResult: BindingResult): String {
        userValidator.validate(userDto, bindingResult);
        if(bindingResult.hasErrors()) {
            return "registration"
        }
        val user: User = User(userDto)
        userService.save(user);
        securityService.autoLogin(userDto.username, userDto.password);

        return "registration complete";
    }

    @ResponseBody
    @RequestMapping("/logout")
    fun logout(): String {
        return "logout";
    }
}