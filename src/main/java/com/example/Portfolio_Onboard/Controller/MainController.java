package com.example.Portfolio_Onboard.Controller;

import com.example.Portfolio_Onboard.DTO.*;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Service.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final ServiceSearch serviceSearch;

    @Autowired
    MainController(ServiceJoin serviceJoin, ServiceWorld serviceWorld, ServiceFindId serviceFindId, ServiceFindPwd serviceFindPwd, ServiceModifyInfo serviceModifyInfo, ServiceSearch serviceSearch){

        this.serviceJoin = serviceJoin;
        this.serviceWorld = serviceWorld;
        this.serviceFindId = serviceFindId;
        this.serviceFindPwd = serviceFindPwd;
        this.serviceModifyInfo = serviceModifyInfo;
        this.serviceSearch = serviceSearch;
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
    public String setJoin(DTOJoin dtoJoin, RedirectAttributes redirectAttributes){

        return serviceJoin.setJoin(dtoJoin, redirectAttributes);
    }

    // 검색 기능 구현
    @PostMapping("/search_proc")
    public String setSearchResult(@RequestParam("keyword") String keyword,
                                  RedirectAttributes redirectAttributes){

        redirectAttributes.addAttribute("keyword", keyword);

        return "redirect:/searchResult";
    }

    @GetMapping("/searchResult")
    public String getSearchResult(@RequestParam("keyword") String keyword, Model model){


        model.addAttribute("boardCount", serviceWorld.countBoard()); // 전체보드수
        model.addAttribute("postCount", serviceWorld.countPost()); // 전체게시글수
        model.addAttribute("commentsCount", serviceWorld.countComments()); // 전체댓글수

        List<DTOSearchBoard> boards = serviceSearch.setSearchResultBoard(keyword);
        // 리스트에서 0번 인덱스부터 9번 인덱스까지(총 10개 요소)를 새로운 서브 리스트로 반환한다.
        model.addAttribute("boards", boards);

        Sort sort = Sort.by(
                Sort.Order.desc("goodCount"),
                Sort.Order.desc("viewCount"),
                Sort.Order.desc("regdate")
        );
        List<DTOSearchPost> posts = serviceSearch.setSearchResultPost(keyword, sort);
        // 리스트에서 0번 인덱스부터 9번 인덱스까지(총 10개 요소)를 새로운 서브 리스트로 반환한다.
        model.addAttribute("posts", posts);


        model.addAttribute("keyword", keyword);

        return "searchResult";
    }
    // ---------------------------------------------------------


    // 보드, 포스트 검색 결과 더보기
    @GetMapping("/moreBoard")
    public String getMoreBoard(@RequestParam("keyword") String keyword,
                               @RequestParam(value="page", defaultValue="1") int page,
                               Model model){

        model.addAttribute("boardCount", serviceWorld.countBoard()); // 전체보드수
        model.addAttribute("postCount", serviceWorld.countPost()); // 전체게시글수
        model.addAttribute("commentsCount", serviceWorld.countComments()); // 전체댓글수

        // 페이징 코드
        int pageSize = 20;
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("bidx").descending());
        // Spring Data JPA의 페이지는 0부터 시작하므로, page 값에서 1을 빼준다.
        // PageRequest.of(page, size) PageRequest.of(page, size) 형태로 사용한다.
        // page: 가져올 페이지 번호 (0부터 시작)
        // size: 한 페이지에 포함할 데이터 개수

        Page<DTOSearchBoard> boardList = serviceSearch.worldList(keyword, pageable);
        // postPage 자체는 Page 타입이고, 그 안에 담긴 각각의 데이터는 DTOPostView 타입이다.

        int totalPages = boardList.getTotalPages();
        // 전체 페이지 개수를 반환하는 메서드
        // 예를 들어 데이터가 50개 있고 size=10으로 요청했다면, totalPages = 5가 됨.

        if(totalPages == 0) {

            totalPages = 1;
        }

        // 현재 페이지의 게시글 목록
        model.addAttribute("boards", boardList.getContent()); // 20개의 데이터만 담고 있다.

        // 페이징 관련 정보 전달
        model.addAttribute("currentPage", boardList.getNumber() + 1); // 1부터 보이게 하기 위해 +1
        model.addAttribute("totalPages", totalPages); // // 전체 페이지 개수를 반환.
        model.addAttribute("totalBoards", boardList.getTotalElements()); // 페이징에 상관없이 전체 레코드의 갯수를 반환.
        model.addAttribute("pageSize", pageSize);  // 템플릿에서 내림차순 번호 계산에 사용.
        // -------------------------------------------------------------------------------------

        model.addAttribute("keyword", keyword);

        return "moreBoard";
    }

    @GetMapping("/morePost")
    public String getMorePost(@RequestParam("keyword") String keyword,
                              @RequestParam(value="page", defaultValue="1") int page,
                              Model model){

        model.addAttribute("boardCount", serviceWorld.countBoard()); // 전체보드수
        model.addAttribute("postCount", serviceWorld.countPost()); // 전체게시글수
        model.addAttribute("commentsCount", serviceWorld.countComments()); // 전체댓글수

        // 페이징 코드
        int pageSize = 15;
        Sort sort = Sort.by(
                Sort.Order.desc("goodCount"),
                Sort.Order.desc("viewCount"),
                Sort.Order.desc("regdate")
        );
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        // Spring Data JPA의 페이지는 0부터 시작하므로, page 값에서 1을 빼준다.
        // PageRequest.of(page, size) PageRequest.of(page, size) 형태로 사용한다.
        // page: 가져올 페이지 번호 (0부터 시작)
        // size: 한 페이지에 포함할 데이터 개수

        Page<DTOSearchPost> postList = serviceSearch.postList(keyword, pageable);
        // postPage 자체는 Page 타입이고, 그 안에 담긴 각각의 데이터는 DTOPostView 타입이다.

        int totalPages = postList.getTotalPages();
        // 전체 페이지 개수를 반환하는 메서드
        // 예를 들어 데이터가 50개 있고 size=10으로 요청했다면, totalPages = 5가 됨.

        if(totalPages == 0) {

            totalPages = 1;
        }

        // 현재 페이지의 게시글 목록
        model.addAttribute("posts", postList.getContent()); // 15개의 데이터만 담고 있다.

        // 페이징 관련 정보 전달
        model.addAttribute("currentPage", postList.getNumber() + 1); // 1부터 보이게 하기 위해 +1
        model.addAttribute("totalPages", totalPages); // // 전체 페이지 개수를 반환.
        model.addAttribute("totalPosts", postList.getTotalElements()); // 페이징에 상관없이 전체 레코드의 갯수를 반환.
        model.addAttribute("pageSize", pageSize);  // 템플릿에서 내림차순 번호 계산에 사용.
        // -------------------------------------------------------------------------------------

        model.addAttribute("keyword", keyword);


        return "morePost";
    }
    // ---------------------------------------------------------


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
    public String setNewPwd(DTONewPwd dtoNewPwd, HttpSession session){

        session.removeAttribute("pwdCheckPassed");
        return serviceFindPwd.newPwd(dtoNewPwd);
    }
    // ----------------------------------------------------------


    // 회원 정보 수정
    @GetMapping("/infoModifyPwdCheck")
    public String getInfoModifyCheckPwd(Model model, HttpSession session){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {

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
                                      HttpServletResponse response,
                                        HttpSession session) throws IOException {

        DTOCheckPwdResult checkResult = serviceFindPwd.checkPwd(dtoModifyPwdCheck);

        if("SUCCESS".equals(checkResult.getResult())){
            // 필요한 경우 Flash attribute 추가
            // 예를 들어, 회원 정보를 다시 조회하거나 추가하는 작업 수행

            redirectAttributes.addFlashAttribute("memberInfo", checkResult.getEntityMemberInfo());
            // 리다이렉트 후 단 한 번만 사용할 수 있도록 세션에 임시 저장되는 데이터
            // 오직 리다이렉트("redirect:/...")를 사용할 때만 작동한다.
            // 뷰 리졸버를 사용해 직접 템플릿 이름을 반환하면 Flash Attribute는 적용되지 않는다.
            session.setAttribute("pwdCheckPassed", true);
            return "redirect:/infoModify";
        }else{

            response.setContentType("text/html;charset=UTF-8");

            PrintWriter out = response.getWriter();
            out.println("<script>alert('パスワードが一致しません。'); history.back();</script>");
            out.flush();
            return null;
        }
    }

    @GetMapping("/infoModify")
    public String getInfoModify(@ModelAttribute("memberInfo") EntityMemberInfo memberInfo,
                                Model model, RedirectAttributes redirectAttributes,
                                HttpServletResponse response, HttpSession session) throws IOException{

        if(session.getAttribute("pwdCheckPassed") == null || memberInfo.getUserid() == null){

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('アクセスが拒否されました。'); history.back();</script>");
            out.flush();

            return null;
        }else {

            model.addAttribute("memberInfo", memberInfo);
            return "infoModify/infoModify";
        }
    }

    @GetMapping("/modifyPwd")
    public String getModifyPwd(Model model, HttpSession session,
                               HttpServletResponse response) throws IOException{

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && session.getAttribute("pwdCheckPassed") != null) {

            model.addAttribute("userid", session.getAttribute("userid"));
            model.addAttribute("mail", session.getAttribute("mail"));

            return "infoModify/modifyPwd";
        }else{

            session.removeAttribute("pwdCheckPassed");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('アクセスが拒否されました。'); history.back();</script>");
            out.flush();

            return null;
        }
    }

    @GetMapping("/modifyNick")
    public String getModifyNick(Model model, HttpSession session,
                                HttpServletResponse response) throws IOException{

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && session.getAttribute("pwdCheckPassed") != null) {

            model.addAttribute("userid", session.getAttribute("userid"));

            return "infoModify/modifyNick";
        }else {

            session.removeAttribute("pwdCheckPassed");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('アクセスが拒否されました。'); history.back();</script>");
            out.flush();

            return null;
        }
    }

    @PostMapping("/modifyNick_proc")
    public String setModifyNick(DTOModifyNick dtoModifyNick, HttpSession session){

        session.removeAttribute("pwdCheckPassed");
        return serviceModifyInfo.modifyNick(dtoModifyNick);
    }

    @GetMapping("/modifyMail")
    public String getModifyMail(Model model, HttpSession session,
                                HttpServletResponse response) throws IOException{

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && session.getAttribute("pwdCheckPassed") != null) {

            model.addAttribute("userid", session.getAttribute("userid"));

            return "infoModify/modifyMail";
        }else {

            session.removeAttribute("pwdCheckPassed");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('アクセスが拒否されました。'); history.back();</script>");
            out.flush();

            return null;
        }
    }

    @PostMapping("/newMail_proc")
    public String setModifyMail(DTOModifyMail dtoModifyMail, HttpSession session){

        session.removeAttribute("pwdCheckPassed");

        return serviceModifyInfo.modifyMail(dtoModifyMail);
    }

    @GetMapping("/withdrawal")
    public String getWithdrawal(){

        return "infoModify/withdrawal";
    }

    @PostMapping("/withdrawal_proc")
    public String setWithdrawal(@RequestParam("userid") String userid,
                                @RequestParam("pwd") String pwd){

        return serviceModifyInfo.withdrawal(userid, pwd);
    }
    // ----------------------------------------------------------

    @GetMapping("/board")
    public String getBoard(@RequestParam("bidx") Long bidx,
                           @RequestParam(value="page", defaultValue="1") int page,
                           Model model){
    // /board?bidx=1 처럼 url에 포함시킨 데이터를 받기 위해 @RequestParam 를 사용했다.

        DTOBoardInfo boardInfo = serviceWorld.boardInfo(bidx);
        String regdate = String.valueOf(boardInfo.getRegdate());
        String date = regdate.substring(0,10); // 연월일, regdate의 0번째 문자부터 출력하고 10번째 문자부터 출력하지 않고 자른다.


        // 페이징 코드
        int pageSize = 20;
        Sort sort = Sort.by(
                Sort.Order.desc("goodCount"),
                Sort.Order.desc("viewCount"),
                Sort.Order.desc("regdate")
        );
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        // Spring Data JPA의 페이지는 0부터 시작하므로, page 값에서 1을 빼준다.
        // PageRequest.of(page, size) PageRequest.of(page, size) 형태로 사용한다.
        // page: 가져올 페이지 번호 (0부터 시작)
        // size: 한 페이지에 포함할 데이터 개수

        Page<DTOPostView> postPage = serviceWorld.postList(bidx, pageable);
        // postPage 자체는 Page 타입이고, 그 안에 담긴 각각의 데이터는 DTOPostView 타입이다.

        int totalPages = postPage.getTotalPages();
        // 전체 페이지 개수를 반환하는 메서드
        // 예를 들어 데이터가 50개 있고 size=10으로 요청했다면, totalPages = 5가 됨.

        if(totalPages == 0) {

            totalPages = 1;
        }

        // 현재 페이지의 게시글 목록
        model.addAttribute("postList", postPage.getContent()); // 20개의 데이터만 담고 있다.

        // 페이징 관련 정보 전달
        model.addAttribute("currentPage", postPage.getNumber() + 1); // 1부터 보이게 하기 위해 +1
        model.addAttribute("totalPages", totalPages); // // 전체 페이지 개수를 반환.
        model.addAttribute("totalPosts", postPage.getTotalElements()); // 페이징에 상관없이 전체 레코드의 갯수를 반환.
        model.addAttribute("pageSize", pageSize);  // 템플릿에서 내림차순 번호 계산에 사용.
        // -------------------------------------------------------------------------------------

        model.addAttribute("commentsCount", serviceWorld.countComments()); // 전체댓글수
        model.addAttribute("postCount", serviceWorld.countPost());
        model.addAttribute("boardCount", serviceWorld.countBoard());
        model.addAttribute("date", date);
        model.addAttribute("boardInfo", serviceWorld.boardInfo(bidx));
        // index.html 파일에서 생성한 url의 파라미터를 model로 board에 전달해 준다.
        model.addAttribute("postList", serviceWorld.postList(bidx, pageable));
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
