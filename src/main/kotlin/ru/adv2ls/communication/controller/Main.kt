package ru.adv2ls.communication.controller

import com.fasterxml.jackson.databind.util.JSONPObject
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView
import java.awt.PageAttributes

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