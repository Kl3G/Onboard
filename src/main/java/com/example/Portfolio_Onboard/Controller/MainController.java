package com.example.Portfolio_Onboard.Controller;

import com.example.Portfolio_Onboard.DTO.*;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Service.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Log4j2
@Controller
//@RequestMapping("/member")
public class MainController {

    private final ServiceJoin serviceJoin;
    private final ServiceWorld serviceWorld;
    private final ServiceFindId serviceFindId;
    private final ServiceFindPwd serviceFindPwd;
    private final ServiceModifyInfo serviceModifyInfo;

    @Autowired
    MainController(ServiceJoin serviceJoin, ServiceWorld serviceWorld, ServiceFindId serviceFindId, ServiceFindPwd serviceFindPwd, ServiceModifyInfo serviceModifyInfo){

        this.serviceJoin = serviceJoin;
        this.serviceWorld = serviceWorld;
        this.serviceFindId = serviceFindId;
        this.serviceFindPwd = serviceFindPwd;
        this.serviceModifyInfo = serviceModifyInfo;
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


    // 아이디 찾기
    @GetMapping("/findId")
    public String getFindId(){

        return "findId";
    }

    @PostMapping("/findId_proc")
    public String setFindId(DTOFindId dtoFindId, RedirectAttributes redirectAttributes){

        return serviceFindId.findId(dtoFindId, redirectAttributes);
    }

    @GetMapping("/foundInfo")
    public String getFoundInfo(@ModelAttribute("userid") String userid, Model model) {
    /* return "redirect:/post?pidx="+pidx+"&bidx="+bidx; 처럼 url에 포함시키지 않고,
    redirectAttributes.addFlashAttribute 로 전송했기 때문에 @ModelAttribute로 받았다. */
        model.addAttribute("userid", userid);

        return "foundInfo";
    }

    @GetMapping("/idNotFound")
    public String getIdNotFound(){

        return "idNotFound";
    }
    // ---------------------------------------------------------


    // 비밀번호 찾기, 재설정
    @GetMapping("/findPwd")
    public String getFindPwd(){

        return "findPwd";
    }

    @PostMapping("/findPwd_proc")
    public String setFindPwd(DTOFindPwd dtoFindPwd, RedirectAttributes redirectAttributes){

        return serviceFindPwd.findPwd(dtoFindPwd, redirectAttributes);
    }

    @GetMapping("/pwdNotFound")
    public String getPwdNotFound(){

        return "pwdNotFound";
    }

    @GetMapping("/newPwd")
    public String getNewPwd(){

        return "newPwd";
    }

    @PostMapping("/newPwd_proc")
    public String setNewPwd(DTONewPwd dtoNewPwd){

        return serviceFindPwd.newPwd(dtoNewPwd);
    }
    // ----------------------------------------------------------


    // 회원 정보 수정
    @GetMapping("/infoModifyPwdCheck")
    public String getInfoModifyCheckPwd(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {

            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();

            model.addAttribute("userid", session.getAttribute("userid"));
            model.addAttribute("boardCount", serviceWorld.countBoard()); // 전체보드수
            model.addAttribute("postCount", serviceWorld.countPost()); // 전체게시글수
            model.addAttribute("commentsCount", serviceWorld.countComments()); // 전체댓글수
        }
        return "infoModify/infoModifyPwdCheck";
    }

    @PostMapping("/infoModifyPwdCheck_proc")
    public String setInfoModifyPwdCheck(DTOModifyPwdCheck dtoModifyPwdCheck,
                                      RedirectAttributes redirectAttributes,
                                      HttpServletResponse response) throws IOException {

        DTOCheckPwdResult checkResult = serviceFindPwd.checkPwd(dtoModifyPwdCheck);

        if("SUCCESS".equals(checkResult.getResult())){
            // 필요한 경우 Flash attribute 추가
            // 예를 들어, 회원 정보를 다시 조회하거나 추가하는 작업 수행

            redirectAttributes.addFlashAttribute("memberInfo", checkResult.getEntityMemberInfo());
            return "redirect:/infoModify";
        }else{

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('비밀번호가 일치하지 않습니다.'); history.back();</script>");
            out.flush();
            return null;
        }
    }

    @GetMapping("/infoModify")
    public String getInfoModify(@ModelAttribute("memberInfo") EntityMemberInfo memberInfo,
                                Model model, RedirectAttributes redirectAttributes,
                                HttpServletResponse response) throws IOException{

        if(memberInfo.getUserid() == null){

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.'); history.back();</script>");
            out.flush();
            return null;
        }else {

            model.addAttribute("memberInfo", memberInfo);
            return "infoModify/infoModify";
        }
    }

    @GetMapping("/modifyPwd")
    public String getModifyPwd(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {

            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();

            model.addAttribute("userid", session.getAttribute("userid"));
            model.addAttribute("mail", session.getAttribute("mail"));
        }

        return "infoModify/modifyPwd";
    }

    @GetMapping("/modifyNick")
    public String getModifyNick(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {

            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();

            model.addAttribute("userid", session.getAttribute("userid"));
        }

        return "infoModify/modifyNick";
    }

    @PostMapping("/modifyNick_proc")
    public String setModifyNick(DTOModifyNick dtoModifyNick){

        return serviceModifyInfo.modifyNick(dtoModifyNick);
    }

    @GetMapping("/modifyMail")
    public String getModifyMail(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {

            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();

            model.addAttribute("userid", session.getAttribute("userid"));
        }

        return "infoModify/modifyMail";
    }

    @PostMapping("/newMail_proc")
    public String setModifyMail(DTOModifyMail dtoModifyMail){

        return serviceModifyInfo.modifyMail(dtoModifyMail);
    }
    // ----------------------------------------------------------

    @GetMapping("/board")
    public String getBoard(@RequestParam("bidx") Long bidx, Model model){
    // /board?bidx=1 처럼 url에 포함시킨 데이터를 받기 위해 @RequestParam 를 사용했다.

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

    @GetMapping(value= {"/world/asia", "/world/europe", "/wo" +
            "rld/northAmerica", "/world/southAmerica", "/world/oceania"})
    public String getWorld(@RequestParam(value = "place") String num, Model model){

        String place = "";

        switch(num){

            case "1" : place = "world/asia";
            break;
            case "2" : place = "world/europe";
            break;
            case "3" : place = "world/northAmerica";
            break;
            case "4" : place = "world/southAmerica";
            break;
            case "5" : place = "world/oceania";
        }

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
}
