package com.example.Portfolio_Onboard.Controller;

import com.example.Portfolio_Onboard.DTO.*;
import com.example.Portfolio_Onboard.Entity.EntityComments;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import com.example.Portfolio_Onboard.Service.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
@Controller
//@RequestMapping("/member")
public class MainController {

    private final ServiceJoin serviceJoin;
    private final ServiceWorld serviceWorld;
    private final ServiceTest serviceTest;

    @Autowired
    MainController(ServiceJoin serviceJoin, ServiceWorld serviceWorld, ServiceTest serviceTest){

        this.serviceJoin = serviceJoin;
        this.serviceWorld = serviceWorld;
        this.serviceTest = serviceTest;
    }

    @GetMapping("/index")
    public String getIndex(Model model){


        model.addAttribute("boardCount", serviceWorld.countBoard()); // 전체보드수
        model.addAttribute("postCount", serviceWorld.countPost()); // 전체게시글수
        model.addAttribute("commentsCount", serviceWorld.countComments()); // 전체댓글수
        model.addAttribute("boards", serviceWorld.list());

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
    public String getBoard(@RequestParam("bidx") Long bidx, Model model){

        DTOBoardInfo boardInfo = serviceWorld.boardInfo(bidx);
        String regdate = String.valueOf(boardInfo.getRegdate());
        String date = regdate.substring(0,10);
        // 연월일, regdate의 0번째 문자부터 출력하고 10번째 문자부터 출력하지 않고 자른다.

        model.addAttribute("commentsCount", serviceWorld.countComments()); // 전체댓글수
        model.addAttribute("postCount", serviceWorld.countPost());
        model.addAttribute("boardCount", serviceWorld.countBoard());
        model.addAttribute("date", date);
        log.error(String.valueOf(boardInfo));
        model.addAttribute("boardInfo", serviceWorld.boardInfo(bidx));
        // index.html 파일에서 생성한 url의 파라미터를 model로 board에 전달해 준다.
        model.addAttribute("postList", serviceWorld.postList(bidx));
        // 보드의 게시글 리스트 출력
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
            model.addAttribute("boardCount", serviceWorld.countBoard()); // 전체보드수
            model.addAttribute("postCount", serviceWorld.countPost()); // 전체게시글수
            model.addAttribute("commentsCount", serviceWorld.countComments()); // 전체댓글수
            model.addAttribute("userid", userid);
            model.addAttribute("nick", nick);
        }

        return "createBoard";
    }

    @PostMapping("/createBoard_proc")
    public String setCreateBoard(DTOCreateBoard dtoCreateBoard){

        return serviceWorld.setWorld(dtoCreateBoard);
    }

    @GetMapping(value= {"/asia", "/world/europe", "/world/northAmerica", "/world/southAmerica", "/world/oceania"})
    public String getWorld(@RequestParam(value = "place") String num, Model model){

        String place = "";

        System.out.println("asd"+place);
        System.out.println("findfindfindfindfindfindfindfindfindfindfindfindfindfind");

        switch(num){

            case "1" : place = "/asia";
            break;
            case "2" : place = "/world/europe";
            break;
            case "3" : place = "/world/northAmerica";
            break;
            case "4" : place = "/world/southAmerica";
            break;
            case "5" : place =  "/world/oceania";
        }

        System.out.println("asd"+place);
        System.out.println("findfindfindfindfindfindfindfindfindfindfindfindfindfind");

        List<DTOBoardView> boardList = serviceWorld.list2(num); // DTOBoardView 리스트 가져옴
        int worldBoardCount = 0; // 갯수를 세기 위한 변수

        // DTOBoardView 리스트에서 place 값과 num 값을 비교
        for (DTOBoardView board : boardList) {
            if (board.getPlace().equals(num)) { // num과 board의 place 비교
                worldBoardCount++; // 일치할 경우 카운트 증가
            }
        }

        model.addAttribute("commentsCount", serviceWorld.countComments()); // 전체댓글수
        model.addAttribute("postCount", serviceWorld.countPost());
        model.addAttribute("boardCount", serviceWorld.countBoard()); // 전체보드 갯수
        model.addAttribute("worldBoardCount", worldBoardCount); // 대륙보드 갯수, 정보
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

    @GetMapping("/test")
    public String test(){

        return serviceTest.test2();
    }
}
