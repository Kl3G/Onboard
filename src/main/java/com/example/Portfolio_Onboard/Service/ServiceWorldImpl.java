package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOBoardInfo;
import com.example.Portfolio_Onboard.DTO.DTOBoardView;
import com.example.Portfolio_Onboard.DTO.DTOCreateBoard;
import com.example.Portfolio_Onboard.DTO.DTOPostView;
import com.example.Portfolio_Onboard.Entity.EntityComments;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import com.example.Portfolio_Onboard.Repository.RepoComment;
import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import com.example.Portfolio_Onboard.Repository.RepoPost;
import com.example.Portfolio_Onboard.Repository.RepoWorld;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ServiceWorldImpl implements ServiceWorld {

    private final RepoMemberInfo repoMemberInfo;
    private final RepoWorld repoWorld;
    private final RepoPost repoPost;
    private final RepoComment repoComment;

    ServiceWorldImpl(RepoWorld repoWorld, RepoMemberInfo repoMemberInfo, RepoPost repoPost, RepoComment repoComment){

        this.repoMemberInfo = repoMemberInfo;
        this.repoWorld = repoWorld;
        this.repoPost = repoPost;
        this.repoComment = repoComment;
    }

    @Override
    public String setWorld(DTOCreateBoard dtoCreateBoard) {

        EntityMemberInfo memberInfo = repoMemberInfo.findByUserid(dtoCreateBoard.getUserid());
        // 세션에 담겨 있던 userid를 model로 createBoard.html에 전달,
        // form을 submit할 때 @PostMapping("/createBoard_proc") 실행되고 dtoCreateBoard에 바인딩된다.
        // userid

        repoWorld.save(dtoCreateBoard.entityWorld(memberInfo));

        return "redirect:/index";
    }

    @Override
    public List<DTOBoardView> list() {

        List<EntityWorld> boards = null;
        boards = repoWorld.findAll();

        List<DTOBoardView> dtoBoardView = null;
        dtoBoardView = boards.stream()
                .map((EntityWorld board) -> {
                    DTOBoardView view = new DTOBoardView();
                    view.setBidx(board.getBidx());
                    view.setB_name(board.getB_name());
                    view.setPlace(board.getPlace());
                    return view;
                })
                .collect(Collectors.toList());

        return dtoBoardView;
    }

    @Override
    public int countBoard() {

        List<EntityWorld> boards = null;
        boards = repoWorld.findAll();
        int boardCount = 0;

        for (EntityWorld board : boards) {
            if (board != null) { // num과 board의 place 비교
                boardCount++; // 일치할 경우 카운트 증가
            }
        }
        return boardCount;
    }

    @Override
    public List<DTOBoardView> list2(String place) {

        List<EntityWorld> boards = null;
        boards = repoWorld.findByPlace(place);

        List<DTOBoardView> dtoBoardView = null;
        dtoBoardView = boards.stream()
                .map((EntityWorld board) -> {
                    DTOBoardView view = new DTOBoardView();
                    view.setBidx(board.getBidx());
                    view.setB_name(board.getB_name());
                    view.setPlace(board.getPlace());
                    return view;
                })
                .collect(Collectors.toList());

        return dtoBoardView;
    }

    @Override
    public DTOBoardInfo boardInfo(Long bidx) {

        Optional<EntityWorld> entityWorld = repoWorld.findById(bidx);

        DTOBoardInfo boardInfo = new DTOBoardInfo();
        if (entityWorld.isPresent()) {
            boardInfo.setBidx(entityWorld.get().getBidx());
            boardInfo.setUserid(entityWorld.get().getMemberInfo().getUserid());
            boardInfo.setNick(entityWorld.get().getNick());
            boardInfo.setB_name(entityWorld.get().getB_name());
            boardInfo.setIntro(entityWorld.get().getIntro());
            boardInfo.setRegdate(entityWorld.get().getRegdate());
        } else {
            System.out.println("값이 없습니다.");
        }

        return boardInfo;
    }

    @Override
    public List<DTOPostView> postList(Long bidx) {

        List<EntityPost> posts = null;
        posts = repoPost.findByBoard_Bidx(bidx);

        List<DTOPostView> postList = null;
        postList = posts.stream()
            .map((EntityPost post) -> {
                DTOPostView view = new DTOPostView();
                view.setPidx(post.getPidx());
                view.setBidx(post.getBoard().getBidx());
                view.setCategory(post.getCategory());
                view.setNick(post.getNick());
                view.setUserip(post.getUserip());
                view.setTitle(post.getTitle());
                view.setRegdate(post.getRegdate());
                view.setView_count(post.getView_count());
                view.setGood_count(post.getGood_count());
                return view;
            })
            .collect(Collectors.toList());

        return postList;
    }

    @Override
    public void incrementViewCount(Long pidx) {
        // 포스트를 찾아서 view_count를 증가시킵니다.
        EntityPost post = repoPost.findById(pidx).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setView_count(post.getView_count() + 1);
        repoPost.save(post);
    }

    @Override
    public int countPost() {

        List<EntityPost> posts = null;
        posts = repoPost.findAll();
        int postCount = 0;

        for (EntityPost post : posts) {
            if (post != null) { // num과 board의 place 비교
                postCount++; // 일치할 경우 카운트 증가
            }
        }
        return postCount;
    }

    @Override
    public int countComments() {

        List<EntityComments> comments = null;
        comments = repoComment.findAll();
        int commentsCount = 0;

        for (EntityComments comment : comments) {
            if (comment != null) { // num과 board의 place 비교
                commentsCount++; // 일치할 경우 카운트 증가
            }
        }

        return commentsCount;
    }

    @Override
    public EntityPost postView(Long pidx) {

        Optional<EntityPost> entityPost = repoPost.findById(pidx);
        EntityPost post = null;

        if (entityPost.isPresent()) {

            post = entityPost.get();
        }

        return post;
    }
}
