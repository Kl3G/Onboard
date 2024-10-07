package com.example.Portfolio_Onboard.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/member")
public class MainController {

    @GetMapping("/index")
    public String getIndex(){

        return "index";
    }

    @GetMapping("/board")
    public String getBoard(){

        return "board";
    }
}
