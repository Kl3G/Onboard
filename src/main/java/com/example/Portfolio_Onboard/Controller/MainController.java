package com.example.Portfolio_Onboard.Controller;

import com.example.Portfolio_Onboard.DTO.DTOJoin;
import com.example.Portfolio_Onboard.DTO.DTOLogin;
import com.example.Portfolio_Onboard.Service.ServiceJoinLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@RequestMapping("/member")
public class MainController {

    private final ServiceJoinLogin serviceJoinLogin;

    @Autowired
    MainController(ServiceJoinLogin serviceJoinLogin){

        this.serviceJoinLogin = serviceJoinLogin;
    }

    @GetMapping("/index")
    public String getIndex(){

        return "index";
    }

    @GetMapping("/join")
    public String getJoin(){

        return "join";
    }

    @PostMapping("/join_proc")
    public String setJoin(DTOJoin dtoJoin){

        return serviceJoinLogin.setJoin(dtoJoin);
    }

    @PostMapping("/login_proc")
    public String setLogin(DTOLogin dtoLogin){

        return "sd";
    }

    @GetMapping("/board")
    public String getBoard(){

        return "board";
    }

    @GetMapping("/createBoard")
    public String getCreateBoard(){

        return "createBoard";
    }

    @GetMapping(value= {"/world/asia", "/world/europe", "/world/northAmerica", "/world/southAmerica", "/world/oceania"})
    public String getAsia(@RequestParam(value = "country") int num){

        String country = "";

        switch(num){

            case 1 : country = "/world/asia";
            break;
            case 2 : country = "/world/europe";
            break;
            case 3 : country = "/world/northAmerica";
            break;
            case 4 : country = "/world/southAmerica";
            break;
            case 5 : country =  "/world/oceania";
        }

        return country;
    }

    @GetMapping("/notice")
    public String getNotice(){

        return "notice";
    }
}
