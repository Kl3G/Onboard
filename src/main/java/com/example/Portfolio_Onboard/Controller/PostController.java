package com.example.Portfolio_Onboard.Controller;

import com.example.Portfolio_Onboard.DTO.*;
import com.example.Portfolio_Onboard.Entity.*;
import com.example.Portfolio_Onboard.Repository.*;
import com.example.Portfolio_Onboard.Service.*;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;



@Log4j2
@Controller
public class PostController {

    private final ServiceWorld serviceWorld;
    private final ServiceComment serviceComment;
    private final ServiceCreatePost serviceCreatePost;
    private final RepoChildComments repoChildComments;
    private final RepoComment repoComment;
    private final RepoPost repoPost;
    private final RepoWorld repoWorld;
    private final RepoFiles repoFiles;


    @Autowired
    PostController(ServiceJoin serviceJoin, ServiceWorld serviceWorld, ServiceCreatePost serviceCreatePost, ServiceComment serviceComment, ServiceCreatePost serviceCreatePost1, RepoChildComments repoChildComments, RepoComment repoComment, RepoPost repoPost, RepoWorld repoWorld, RepoFiles repoFiles){

        this.serviceWorld = serviceWorld;
        this.serviceComment = serviceComment;
        this.serviceCreatePost = serviceCreatePost1;
        this.repoChildComments = repoChildComments;
        this.repoComment = repoComment;
        this.repoPost = repoPost;
        this.repoWorld = repoWorld;
        this.repoFiles = repoFiles;
    }

    /*@GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileName") String fileName,
                                                 HttpServletRequest request) {

        return serviceCreatePost.downloadFile(fileName, request);
    }*/


    @GetMapping(value = {"/createPost", "/modifyPost"})
    public String getCreatePost(@RequestParam("bidx") Long bidx,
                                @RequestParam(value = "pidx", defaultValue = "") Long pidx,
                                HttpServletRequest request, Model model){


        Optional<EntityWorld> optionalBoard = repoWorld.findById(bidx);
        EntityWorld board = optionalBoard.get();

        Optional<EntityPost> post = serviceCreatePost.createOrUpdate(pidx);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp != null && !clientIp.isEmpty() && !"unknown".equalsIgnoreCase(clientIp)) {
            // X-Forwarded-For 헤더는 여러 IP가 콤마로 구분되어 있을 수 있으므로 첫 번째 IP를 사용합니다.
            clientIp = clientIp.split(",")[0];
        } else {
            clientIp = request.getRemoteAddr();
        }
        model.addAttribute("ip", clientIp);


        if (authentication != null && authentication.isAuthenticated()) {
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();

            // 세션에서 userid와 nick 값을 가져온다
            String userid = (String) session.getAttribute("userid");
            String nick = (String) session.getAttribute("nick");

            if(userid == null){

                userid = "guest";
                nick = "ㅇㅇ";
            }

            model.addAttribute("userid", userid);
            model.addAttribute("nick", nick);
        }

        model.addAttribute("bidx", bidx);
        model.addAttribute("boardInfo", board);
        model.addAttribute("boardCount", serviceWorld.countBoard()); // 전체보드수
        model.addAttribute("postCount", serviceWorld.countPost()); // 전체게시글수
        model.addAttribute("commentsCount", serviceWorld.countComments()); // 전체댓글수

        if (post.isPresent()) {

            model.addAttribute("post", post.get());
            return "modifyPost";
        }

        return "createPost";
    }

    /*---------------------------------게시글 작성, 수정 매서드-----------------------------------------*/
    /*-----------------------------------------------------------------------------------*/
    @GetMapping("/postModifyPwdCheck")
    public String checkPostPwd(@RequestParam("bidx") Long bidx, @RequestParam("pidx") Long pidx, @RequestParam("userid") String userid, Model model){

        DTOBoardInfo boardInfo = serviceWorld.boardInfo(bidx);
        Optional<EntityPost> post2 = serviceCreatePost.createOrUpdate(pidx);
        EntityPost post = post2.get();

        model.addAttribute("boardCount", serviceWorld.countBoard()); // 전체보드수
        model.addAttribute("postCount", serviceWorld.countPost()); // 전체게시글수
        model.addAttribute("commentsCount", serviceWorld.countComments()); // 전체댓글수
        model.addAttribute("userid", userid);
        model.addAttribute("boardInfo", boardInfo);
        model.addAttribute("post", post);


        return "postModifyPwdCheck";
    }

    @PostMapping("/checkPostPassword")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkPostPassword(@RequestParam(value = "ppwd", required = false) String ppwd,
                                                                 @RequestParam("pidx") Long pidx, @RequestParam("userid") String userid) {
        Optional<EntityPost> postOptional = repoPost.findById(pidx);
        EntityPost post = postOptional.get();
        String DBuserid = post.getUserid();

        if (Objects.equals(DBuserid, userid)){

            if (postOptional.isPresent()) {
                String storedPassword = post.getPpwd(); // 데이터베이스에서 가져온 비밀번호

                // 비밀번호 비교
                if (Objects.equals(ppwd, storedPassword)) {
                    return ResponseEntity.ok(Map.of("success", true)); // 비밀번호가 일치함
                }
            }

            return ResponseEntity.ok(Map.of("success2", true)); // 비밀번호가 일치하지 않음
        }else {

            return ResponseEntity.ok(Map.of("success1", true));
        }
    }

    @GetMapping("/postDelete")
    public String checkDeletePwd(@RequestParam("bidx") Long bidx, @RequestParam("pidx") Long pidx, @RequestParam("userid") String userid, Model model){

        DTOBoardInfo boardInfo = serviceWorld.boardInfo(bidx);
        Optional<EntityPost> post2 = serviceCreatePost.createOrUpdate(pidx);
        EntityPost post = post2.get();

        model.addAttribute("boardCount", serviceWorld.countBoard()); // 전체보드수
        model.addAttribute("postCount", serviceWorld.countPost()); // 전체게시글수
        model.addAttribute("commentsCount", serviceWorld.countComments()); // 전체댓글수
        model.addAttribute("userid", userid);
        model.addAttribute("boardInfo", boardInfo);
        model.addAttribute("post", post);

        return "postDelete";
    }


    @PostMapping("/createPost_proc")
    public String setCreatePost(@ModelAttribute DTOCreatePost dtoCreatePost) {

        return serviceCreatePost.setPost(dtoCreatePost);
    }


    @PostMapping("/modifyPost_proc")
    public String modifyPost(@ModelAttribute DTOModifyPost dtoModifyPost) {

        return serviceCreatePost.updatePost(dtoModifyPost);
    }

    /*---------------------------------게시글 작성, 수정 매서드-----------------------------------------*/
    /*-----------------------------------------------------------------------------------*/




    @GetMapping("/post")
    public String getPost(@RequestParam("pidx") Long pidx, @RequestParam("bidx") Long bidx,
                          @RequestParam(value="page1", defaultValue="1") int page1,
                          @RequestParam(value="page2", defaultValue="1") int page2, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();

            // 세션에서 userid와 nick 값을 가져온다
            String userid = (String) session.getAttribute("userid");
            String nick = (String) session.getAttribute("nick");

            if(userid == null){

                userid = "guest";
                nick = "ㅇㅇ";
            }

            model.addAttribute("userid", userid);
            model.addAttribute("nick", nick);
        }

        InetAddress local = null;
        try {
            local = InetAddress.getLocalHost();
        } catch ( UnknownHostException e ) {
            e.printStackTrace();
        }

        if( local == null ) {

            String userip = "";
        } else {

            String userip = local.getHostAddress();
            model.addAttribute("userip", userip);
        }


        serviceWorld.incrementViewCount(pidx); // 조회수 증가, 카운트

        DTOBoardInfo boardInfo = serviceWorld.boardInfo(bidx);

        String regdate = String.valueOf(boardInfo.getRegdate());
        String date = regdate.substring(0,10);// 연월일, regdate의 0번째 문자부터 출력하고 10번째 문자부터 출력하지 않고 자른다.

        // 페이징 코드
        int pageSize = 30;
        Sort sort = Sort.by(

                Sort.Order.desc("goodCount"),
                Sort.Order.desc("viewCount"),
                Sort.Order.desc("regdate")
        );
        Pageable pageable = PageRequest.of(page1 - 1, pageSize, sort);
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
        model.addAttribute("postList", postPage.getContent()); // 30개의 데이터만 담고 있다.

        // 페이징 관련 정보 전달
        model.addAttribute("currentPage", postPage.getNumber() + 1); // 1부터 보이게 하기 위해 +1
        model.addAttribute("totalPages", totalPages); // // 전체 페이지 개수를 반환.
        model.addAttribute("totalPosts", postPage.getTotalElements()); // 페이징에 상관없이 전체 레코드의 갯수를 반환.
        model.addAttribute("pageSize", pageSize);  // 템플릿에서 내림차순 번호 계산에 사용.
        // -------------------------------------------------------------------------------------

        //model.addAttribute("date", date);

        model.addAttribute("boardInfo", serviceWorld.boardInfo(bidx));
        // index.html 파일에서 생성한 url의 파라미터를 model로 board에 전달해 준다.
        model.addAttribute("postList", serviceWorld.postList(bidx, pageable));
        // 보드의 게시글 리스트 출력

        Optional<EntityFiles> getFiles = repoFiles.findById(pidx);
        EntityFiles files = getFiles.get();
        List<String> ofileList = List.of(files.getOfile().split(","));
        List<String> sfileList = List.of(files.getSfile().split(","));
        model.addAttribute("sfileList", sfileList);
        model.addAttribute("ofileList", ofileList);
        // 파일명 출력

        model.addAttribute("boardCount", serviceWorld.countBoard()); // 전체보드수
        model.addAttribute("postCount", serviceWorld.countPost()); // 전체게시글수
        model.addAttribute("commentsCount", serviceWorld.countComments()); // 전체댓글수
        model.addAttribute("post", serviceWorld.postView(pidx)); // 게시글 데이터 전송
        model.addAttribute("commentCount", serviceComment.countComments(pidx)); // 게시글의 댓글 갯수 카운트




        // 페이징 코드
        int pageSize2 = 15;
        Pageable pageable2 = PageRequest.of(page2 - 1, pageSize2, Sort.by("cidx").descending());
        // Spring Data JPA의 페이지는 0부터 시작하므로, page 값에서 1을 빼준다.
        // PageRequest.of(page, size) PageRequest.of(page, size) 형태로 사용한다.
        // page: 가져올 페이지 번호 (0부터 시작)
        // size: 한 페이지에 포함할 데이터 개수

        Page<DTOCommentView> commentPage = serviceComment.getCommentList(pidx, pageable2);
        // postPage 자체는 Page 타입이고, 그 안에 담긴 각각의 데이터는 DTOPostView 타입이다.

        int totalPages2 = commentPage.getTotalPages();
        // 전체 페이지 개수를 반환하는 메서드
        // 예를 들어 데이터가 50개 있고 size=10으로 요청했다면, totalPages = 5가 됨.

        if(totalPages2 == 0) {

            totalPages2 = 1;
        }

        model.addAttribute("commentList", commentPage);
        log.error("commentList" + commentPage);

        // 페이징 관련 정보 전달
        model.addAttribute("currentPage2", commentPage.getNumber() + 1); // 1부터 보이게 하기 위해 +1
        model.addAttribute("totalPages2", totalPages2); // // 전체 페이지 개수를 반환.
        model.addAttribute("totalPosts2", commentPage.getTotalElements()); // 페이징에 상관없이 전체 레코드의 갯수를 반환.
        model.addAttribute("pageSize2", pageSize2);  // 템플릿에서 내림차순 번호 계산에 사용.
        model.addAttribute("currentPage1", page1); // 하단 게시물 페이징
        model.addAttribute("currentPage2", page2); // 상단 댓글 페이징
        model.addAttribute("pidx", pidx);
        model.addAttribute("bidx", bidx);
        // -------------------------------------------------------------------------------------

        return "post";
    }


    @PostMapping("/comment_proc")
    public String setComment(DTOCreateComment dtoCreateComment){

        return serviceComment.setComment(dtoCreateComment);
    }

    @PostMapping("/childcomment_proc")
    public String setChildComment(DTOCreateChildComments dtoCreateChildComments){

        return serviceComment.setChildComment(dtoCreateChildComments);
    }




    /*---------------------------------삭제 매서드-----------------------------------------*/
    /*-----------------------------------------------------------------------------------*/

    @PostMapping("/checkCommentPwd")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkCommentPwd(@RequestParam(value = "cpwd", required = false) String cpwd,
                                                                 @RequestParam("cidx") Long cidx) {

        Optional<EntityComments> optionalComments = repoComment.findById(cidx);
        EntityComments comment = optionalComments.get();
        String DBcpwd = comment.getCpwd();

        if (Objects.equals(DBcpwd, cpwd)){

            return ResponseEntity.ok(Map.of("success", true)); // 비밀번호가 일치함
        }else {

            return ResponseEntity.ok(Map.of("success2", true));
        }
    }

    @PostMapping("/commentDel")
    public String delComment(@RequestParam("cidx") Long cidx,
                             @RequestParam("bidx") Long bidx, @RequestParam("pidx") Long pidx){

        repoComment.deleteById(cidx);

        return "redirect:/post?pidx="+pidx+"&bidx="+bidx;
    }

    @PostMapping("/checkChildCommentPwd")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkChildCommentPwd(@RequestParam(value = "ccpwd", required = false) String ccpwd,
                                                               @RequestParam("ccidx") Long ccidx) {

        Optional<EntityChildComments> optionalComments = repoChildComments.findById(ccidx);
        EntityChildComments childComments = optionalComments.get();
        String DBccpwd = childComments.getCcpwd();

        if (Objects.equals(DBccpwd, ccpwd)){

            return ResponseEntity.ok(Map.of("success", true)); // 비밀번호가 일치함
        }else {

            return ResponseEntity.ok(Map.of("success2", true));
        }
    }

    @PostMapping("/childCommentDel")
    public String delChildComment(@RequestParam("ccidx") Long ccidx,
                                  @RequestParam("bidx") Long bidx, @RequestParam("pidx") Long pidx){

        repoChildComments.deleteById(ccidx);

        return "redirect:/post?pidx="+pidx+"&bidx="+bidx;
    }

    @PostMapping("/postDel")
    public String delPost(@RequestParam("pidx") Long pidx, @RequestParam("bidx") Long bidx){

        return serviceCreatePost.delPost(pidx, bidx);
    }

    /*@PostMapping("/boardDel")*/

    /*---------------------------------삭제 매서드-----------------------------------------*/
    /*-----------------------------------------------------------------------------------*/
}
