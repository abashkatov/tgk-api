package ru.adv2ls.communication.controller

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class Main {
    @GetMapping("/", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseBody
    fun greeting(): String {
        // @RequestParam(name="name", require=false, defaultValue="World") String name, Model model
//        model.addAttribute("name", name);
//        val modelAndView = ModelAndView("greeting")
//        return modelAndView
        return "{\"title\":\"Title from api\"}";
    }
}