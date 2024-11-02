package com.example.Portfolio_Onboard.Controller;

import com.example.Portfolio_Onboard.DTO.*;
import com.example.Portfolio_Onboard.Entity.EntityComments;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import com.example.Portfolio_Onboard.Repository.RepoChildComments;
import com.example.Portfolio_Onboard.Repository.RepoComment;
import com.example.Portfolio_Onboard.Repository.RepoPost;
import com.example.Portfolio_Onboard.Repository.RepoWorld;
import com.example.Portfolio_Onboard.Service.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.InetAddress;
import java.net.UnknownHostException;
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


    @Autowired
    PostController(ServiceJoin serviceJoin, ServiceWorld serviceWorld, ServiceCreatePost serviceCreatePost, ServiceTest serviceTest, ServiceComment serviceComment, ServiceCreatePost serviceCreatePost1, RepoChildComments repoChildComments, RepoComment repoComment, RepoPost repoPost, RepoWorld repoWorld){

        this.serviceWorld = serviceWorld;
        this.serviceComment = serviceComment;
        this.serviceCreatePost = serviceCreatePost1;
        this.repoChildComments = repoChildComments;
        this.repoComment = repoComment;
        this.repoPost = repoPost;
        this.repoWorld = repoWorld;
    }


    @GetMapping(value = {"/createPost", "/modifyPost"})
    public String getCreatePost(@RequestParam("bidx") Long bidx, @RequestParam(value = "pidx", defaultValue = "") Long pidx,
                                @RequestParam(value = "ppwd", defaultValue = "") String ppwd, Model model){


        Optional<EntityWorld> optionalBoard = repoWorld.findById(bidx);
        EntityWorld board = optionalBoard.get();

        Optional<EntityPost> post = serviceCreatePost.createOrUpdate(pidx);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        InetAddress local = null;

        try {
            local = InetAddress.getLocalHost();
        } catch ( UnknownHostException e ) {
            e.printStackTrace();
        }

        if( local == null ) {
            String ip = "";
        } else {
            String ip = local.getHostAddress();
            model.addAttribute("ip", ip);
        }

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
        String DBuserid = post.getMemberInfo().getUserid();

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
    public String getPost(@RequestParam("pidx") Long pidx, @RequestParam("bidx") Long bidx, Model model){

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

        model.addAttribute("date", date);

        model.addAttribute("boardInfo", serviceWorld.boardInfo(bidx));
        // index.html 파일에서 생성한 url의 파라미터를 model로 board에 전달해 준다.
        model.addAttribute("postList", serviceWorld.postList(bidx));
        // 보드의 게시글 리스트 출력

        model.addAttribute("boardCount", serviceWorld.countBoard()); // 전체보드수
        model.addAttribute("postCount", serviceWorld.countPost()); // 전체게시글수
        model.addAttribute("commentsCount", serviceWorld.countComments()); // 전체댓글수
        model.addAttribute("post", serviceWorld.postView(pidx)); // 게시글 데이터 전송
        model.addAttribute("commentCount", serviceComment.countComments(pidx)); // 게시글의 댓글 갯수 카운트
        model.addAttribute("commentList", serviceComment.getCommentList(pidx));


        return "post";
    }


    @PostMapping("/comment_proc")
    public String setComment(DTOCreateComment dtoCreateComment){

        return serviceComment.setComment(dtoCreateComment);
    }

    @PostMapping("/childcomment_proc")
    public String setChildcomment(DTOCreateChildComments dtoCreateChildComments){

        return serviceComment.setChildComment(dtoCreateChildComments);
    }




    /*---------------------------------삭제 매서드-----------------------------------------*/
    /*-----------------------------------------------------------------------------------*/

    @PostMapping("/childCommentDel")
    public String delChildComment(@RequestParam("ccidx") Long ccidx, @RequestParam("ccpwd") String ccpwd){

        repoChildComments.deleteByCcidxAndCcpwd(ccidx, ccpwd);

        return "index";
    }

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

    @PostMapping("/postDel")
    public String delPost(@RequestParam("pidx") Long pidx, @RequestParam("bidx") Long bidx){

        repoPost.deleteById(pidx);

        return "redirect:/board?bidx="+bidx;
    }

    /*@PostMapping("/boardDel")*/

    /*---------------------------------삭제 매서드-----------------------------------------*/
    /*-----------------------------------------------------------------------------------*/
}
