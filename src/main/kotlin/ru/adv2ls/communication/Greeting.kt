package ru.adv2ls.communication

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView

@Controller
class Greeting {
    @GetMapping("/greeting")
    @ResponseBody
    fun greeting(@RequestParam(required = false, defaultValue = "0") num : Int): String {
        // @RequestParam(name="name", require=false, defaultValue="World") String name, Model model
//        model.addAttribute("name", name);
//        val modelAndView = ModelAndView("greeting")
//        return modelAndView
        return "greeting #$num"
    }
}