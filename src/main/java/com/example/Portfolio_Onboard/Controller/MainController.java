package com.example.Portfolio_Onboard.Controller;

import com.example.Portfolio_Onboard.DTO.DTOBoardView;
import com.example.Portfolio_Onboard.DTO.DTOJoin;
import com.example.Portfolio_Onboard.DTO.DTOCreateBoard;
import com.example.Portfolio_Onboard.Service.ServiceJoin;
import com.example.Portfolio_Onboard.Service.ServiceWorld;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.jar.Attributes;

@Slf4j
@Controller
//@RequestMapping("/member")
public class MainController {

    private final ServiceJoin serviceJoin;
    private final ServiceWorld serviceWorld;

    @Autowired
    MainController(ServiceJoin serviceJoin, ServiceWorld serviceWorld){

        this.serviceJoin = serviceJoin;
        this.serviceWorld = serviceWorld;
    }

    @GetMapping("/index")
    public String getIndex(Model model){

        List<DTOBoardView> boardList = serviceWorld.list(); // DTOBoardView 리스트 가져옴
        int boardCount = 0; // 갯수를 세기 위한 변수

        // DTOBoardView 리스트에서 place 값과 num 값을 비교
        for (DTOBoardView board : boardList) {
            if (board != null) { // num과 board의 place 비교
                boardCount++; // 일치할 경우 카운트 증가
            }
        }

        model.addAttribute("boardCount", boardCount); // 보드 갯수 추가
        model.addAttribute("boards", boardList);

        return "index";
    }

    @GetMapping("/join")
    public String getJoin(){

        return "join";
    }

    @PostMapping("/join_proc")
    public String setJoin(DTOJoin dtoJoin){

        return serviceJoin.setJoin(dtoJoin);
    }

    @GetMapping("/board")
    public String getBoard(@RequestParam("b_idx") Long b_idx, Model model){

        model.addAttribute("boardInfo", serviceWorld.boardInfo(b_idx));
        // index.html 파일에서 생성한 url의 파라미터를 model로 board에 전달해 준다.
        return "board";
    }

    @GetMapping("/createBoard")
    public String getCreateBoard(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();

            // 세션에서 userid와 nick 값을 가져온다
            String userid = (String) session.getAttribute("userid");
            String nick = (String) session.getAttribute("nick");

            // 모델에 userid와 nick 값을 추가하여 view에 전달
            model.addAttribute("userid", userid);
            model.addAttribute("nick", nick);
        }

        return "createBoard";
    }

    @PostMapping("/createBoard_proc")
    public String setCreateBoard(DTOCreateBoard dtoCreateBoard){

        return serviceWorld.setWorld(dtoCreateBoard);
    }

    @GetMapping(value= {"/world/asia", "/world/europe", "/world/northAmerica", "/world/southAmerica", "/world/oceania"})
    public String getAsia(@RequestParam(value = "place") String num, Model model){

        String place = "";

        switch(num){

            case "1" : place = "/world/asia";
            break;
            case "2" : place = "/world/europe";
            break;
            case "3" : place = "/world/northAmerica";
            break;
            case "4" : place = "/world/southAmerica";
            break;
            case "5" : place =  "/world/oceania";
        }

        List<DTOBoardView> boardList = serviceWorld.list2(num); // DTOBoardView 리스트 가져옴
        int boardCount = 0; // 갯수를 세기 위한 변수

        // DTOBoardView 리스트에서 place 값과 num 값을 비교
        for (DTOBoardView board : boardList) {
            if (board.getPlace().equals(num)) { // num과 board의 place 비교
                boardCount++; // 일치할 경우 카운트 증가
            }
        }

        model.addAttribute("boardCount", boardCount); // 보드 갯수 추가
        model.addAttribute("boards", boardList);

        return place;
    }

    @GetMapping("/notice")
    public String getNotice(){

        return "notice";
    }

    @GetMapping("/api/session-status")
    public ResponseEntity<?> checkSessionStatus(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 로그인한 사용자의 인증 정보를 가져온다.
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok().body("logged_in");
        }
        return ResponseEntity.ok().body("not_logged_in");
    }

    @GetMapping("/api/get-nick")
    public ResponseEntity<String> getNick() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
            String nick = (String) session.getAttribute("nick");
            return ResponseEntity.ok(nick);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
