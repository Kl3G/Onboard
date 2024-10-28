package com.example.Portfolio_Onboard.Controller;

import com.example.Portfolio_Onboard.DTO.DTOBoardInfo;
import com.example.Portfolio_Onboard.DTO.DTOCreateChildComments;
import com.example.Portfolio_Onboard.DTO.DTOCreateComment;
import com.example.Portfolio_Onboard.Service.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Log4j2
@Controller
public class PostController {

    private final ServiceJoin serviceJoin;
    private final ServiceWorld serviceWorld;
    private final ServiceCreatePost serviceCreatePost;
    private final ServiceTest serviceTest;
    private final ServiceComment serviceComment;

    @Autowired
    PostController(ServiceJoin serviceJoin, ServiceWorld serviceWorld, ServiceCreatePost serviceCreatePost, ServiceTest serviceTest, ServiceComment serviceComment){

        this.serviceJoin = serviceJoin;
        this.serviceWorld = serviceWorld;
        this.serviceCreatePost = serviceCreatePost;
        this.serviceTest = serviceTest;
        this.serviceComment = serviceComment;
    }


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
        }
        catch ( UnknownHostException e ) {
            e.printStackTrace();
        }

        if( local == null ) {
            String userip = "";
        }
        else {
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

        model.addAttribute("post", serviceWorld.postView(pidx)); // 게시글 데이터 전송
        model.addAttribute("commentCount", serviceComment.countComments(pidx)); // 댓글 갯수 카운트
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
}
