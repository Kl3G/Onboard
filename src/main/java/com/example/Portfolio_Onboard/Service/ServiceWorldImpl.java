package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOBoardInfo;
import com.example.Portfolio_Onboard.DTO.DTOBoardView;
import com.example.Portfolio_Onboard.DTO.DTOCreateBoard;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import com.example.Portfolio_Onboard.Repository.RepoWorld;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ServiceWorldImpl implements ServiceWorld {

    private RepoMemberInfo repoMemberInfo;
    private RepoWorld repoWorld;

    @Autowired
    ServiceWorldImpl(RepoWorld repoWorld, RepoMemberInfo repoMemberInfo){

        this.repoMemberInfo = repoMemberInfo;
        this.repoWorld = repoWorld;
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
                    view.setB_idx(board.getB_idx());
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
                    view.setB_idx(board.getB_idx());
                    view.setB_name(board.getB_name());
                    view.setPlace(board.getPlace());
                    return view;
                })
                .collect(Collectors.toList());

        return dtoBoardView;
    }

    @Override
    public DTOBoardInfo boardInfo(Long b_idx) {

        Optional<EntityWorld> entityWorld = repoWorld.findById(b_idx);

        DTOBoardInfo boardInfo = new DTOBoardInfo();
        if (entityWorld.isPresent()) {
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
}
