package ru.adv2ls.communication.validator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import ru.adv2ls.communication.dto.UserDto
import ru.adv2ls.communication.service.security.UserService

@Component
class UserValidator {
    @Autowired
    private val userService: UserService? = null

    fun supports(aClass: Class<*>): Boolean {
        return UserDto::class.java == aClass
    }

    fun validate(o: Any, errors: Errors) {
        val user: UserDto = o as UserDto

        if (!user.passwordConfirm.equals(user.password)) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm")
        }
    }
}