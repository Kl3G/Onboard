package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOBoardInfo;
import com.example.Portfolio_Onboard.DTO.DTOBoardView;
import com.example.Portfolio_Onboard.DTO.DTOCreateBoard;
import com.example.Portfolio_Onboard.DTO.DTOPostView;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
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

    ServiceWorldImpl(RepoWorld repoWorld, RepoMemberInfo repoMemberInfo, RepoPost repoPost){

        this.repoMemberInfo = repoMemberInfo;
        this.repoWorld = repoWorld;
        this.repoPost = repoPost;
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
    public List<EntityPost> countPost() {

        List<EntityPost> posts = null;
        posts = repoPost.findAll();

        return posts;
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
