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

    @GetMapping("/asia")
    public String getAsia(){

        return "asia";
    }

    @GetMapping("/europe")
    public String getEurope(){

        return "europe";
    }

    @GetMapping("/northAmerica")
    public String getNorthAmerica(){

        return "northAmerica";
    }

    @GetMapping("/southAmerica")
    public String getSouthAmerica(){

        return "southAmerica";
    }

    @GetMapping("/oceania")
    public String getOceania(){

        return "oceania";
    }

    @GetMapping("/notice")
    public String getNotice(){

        return "notice";
    }

    @GetMapping("/createBoard")
    public String getCreateBoard(){

        return "createBoard";
    }


    @GetMapping("/board")
    public String getBoard(){

        return "board";
    }
}
