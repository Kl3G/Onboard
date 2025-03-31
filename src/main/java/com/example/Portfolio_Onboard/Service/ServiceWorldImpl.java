package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.*;
import com.example.Portfolio_Onboard.Entity.EntityComments;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import com.example.Portfolio_Onboard.Repository.RepoComment;
import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import com.example.Portfolio_Onboard.Repository.RepoPost;
import com.example.Portfolio_Onboard.Repository.RepoWorld;
import lombok.extern.slf4j.Slf4j;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.stream.Collectors;

@Slf4j
@Service
public class ServiceWorldImpl implements ServiceWorld {

    private final RepoMemberInfo repoMemberInfo;
    private final RepoWorld repoWorld;
    private final RepoPost repoPost;
    private final RepoComment repoComment;
    private static final Logger logger = LoggerFactory.getLogger(ServiceWorldImpl.class);

    ServiceWorldImpl(RepoWorld repoWorld, RepoMemberInfo repoMemberInfo, RepoPost repoPost, RepoComment repoComment){

        this.repoMemberInfo = repoMemberInfo;
        this.repoWorld = repoWorld;
        this.repoPost = repoPost;
        this.repoComment = repoComment;
    }

    @Override
    public String setWorld(DTOCreateBoard dtoCreateBoard, RedirectAttributes redirectAttributes) {

        if (repoWorld.existsByBoardName(dtoCreateBoard.getB_name())) {

            redirectAttributes.addAttribute("boardDuplicate", "boardDuplicate");
            return "redirect:/index";
        }

        EntityMemberInfo memberInfo = repoMemberInfo.findByUserid(dtoCreateBoard.getUserid());
        // 세션에 담겨 있던 userid를 model로 createBoard.html에 전달,
        // form을 submit할 때 @PostMapping("/createBoard_proc") 실행되고 dtoCreateBoard에 바인딩된다.
        // userid

        try {

            String fileName = dtoCreateBoard.getFiles().getOriginalFilename();
            String extension = "";

            if (fileName != null) {

                extension = fileName.substring(fileName.lastIndexOf("."));
            }

            String newFileName = UUID.randomUUID() + extension;

            //File destDir = new File("/app/data/boardImg");
            File destDir = new File("/app/data/boardImg");
            if (!destDir.exists()) {

                destDir.mkdirs(); // 디렉토리 및 하위 디렉토리 생성, 이미 존재하는 디렉토리는 자동으로 빼고 생성해 준다.
            }

            //File dest = new File("C:/data/image/" + fileName);
            //File dest = new File("/app/data/boardImg/" + newFileName); // 파일을 저장할 때는 반드시 파일명까지 포함된 경로를 지정해야 한다.
            File dest = new File("/app/data/boardImg/" + newFileName);
            dtoCreateBoard.getFiles().transferTo(dest); // 실제로 파일 저장을 실행.

            repoWorld.save(dtoCreateBoard.entityWorld(memberInfo, newFileName));
        }catch (Exception e) {

            log.error("보드 이미지 업로드 실패");
        }

        return "redirect:/index";
    }

    @Override
    public Map<String, Boolean> duplicateBoardName (String boardName) {

        Optional<EntityWorld> entityWorld = repoWorld.findByB_name(boardName);

        Map<String, Boolean> response = new HashMap<>();

        boolean exists = false;

        if (entityWorld.isPresent()) {

            exists = true;

            response.put("exists", exists);
        } else {

            response.put("exists", exists);
        }

        return response;
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
            boardInfo.setImage(entityWorld.get().getImage());
            boardInfo.setRegdate(entityWorld.get().getRegdate());
        } else {
            System.out.println("값이 없습니다.");
        }

        return boardInfo;
    }

    @Override
    public Page<DTOPostView> postList(Long bidx, Pageable pageable) {

        Page<EntityPost> postsPage  = null;
        postsPage  = repoPost.findByBoard_Bidx(bidx, pageable);

        Page<DTOPostView> dtoPage = postsPage.map(post -> {

            DTOPostView view = new DTOPostView();

            view.setPidx(post.getPidx());
            view.setBidx(post.getBoard().getBidx());
            view.setCategory(post.getCategory());
            view.setNick(post.getNick());
            view.setUserip(post.getUserip());
            view.setTitle(post.getTitle());
            view.setRegdate(post.getRegdate());
            view.setNewdate(post.getNewdate());
            view.setViewCount(post.getViewCount());
            view.setGoodCount(post.getGoodCount());

            return view;
        });

        return dtoPage;
    }

    @Override // 게시글 조회수 증가, 출력
    public void incrementViewCount(Long pidx) {
        // 포스트를 찾아서 view_count를 증가시킵니다.
        EntityPost post = repoPost.findById(pidx).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setViewCount(post.getViewCount() + 1);
        repoPost.save(post);
    }

    @Override // 게시글 좋아요 증가, 출력
    public void incrementGoodCount(Long pidx) {

        EntityPost post = repoPost.findById(pidx).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setGoodCount(post.getGoodCount() + 1);
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

    @Override
    public List<DTOPopularPost> getPopularPost() {

        List<EntityPost> posts = repoPost.findTop4ByOrderByGoodCountDesc();

        List<DTOPopularPost> dtoList = posts.stream()
            .map(post -> {

                DTOPopularPost dto = new DTOPopularPost();

                int start = post.getText().indexOf("src=");
                String image = "";

                if(start != -1) { // 게시글에 이미지를 첨부하지 않았을 경우 post.getText().indexOf("src="); 는 -1을 반환한다.
                // if(start != -1) 이 조건문을 걸지 않으면 start 가 -1이 됐을 때도 image 에 데이터를 할당하려고 하기 때문에 에러가 발생한다.

                    // post.getText() 에서 파일명만 발췌한다.
                    image = post.getText().substring(start + 14, post.getText().indexOf("\"", start + 5));
                }

                String newImage = "/app/data/image/" + image; // 1. 실제(숨긴) 물리 경로를 붙여준다.
                String thumbnailFilename = post.getPidx() + image; // 2. 다른 디렉토리에 저장할 썸네일 이미지 이름 설정.
                String thumbnailFilePath = "/app/data/image/thumbnail/" + thumbnailFilename; // 3. 썸네일 이미지를 저장할 디렉토리 설정.
                File thumbnailFile = new File(thumbnailFilePath); // 4. 썸네일 이미지 파일 경로 객체 생성.

                thumbnailFile.getParentFile().mkdirs(); // 썸네일 이미지를 저장할 디렉토리가 없으면 생성해 준다.

                if (!thumbnailFile.exists()) {

                    try {

                        Thumbnails.of(new File(newImage)) // 5. 썸네일로 변환할 이미지 가져오기. (물리 경로)
                                .size(180, 110) // 6. 너비와 높이 변환
                                .outputQuality(0.6) // 7. 출력 이미지의 품질 70%로 설정, 파일 크기를 줄이면서 압축.
                                .toFile(thumbnailFile); // 8. 썸네일로 변환한 이미지를 썸네일 디렉토리 경로에 저장.

                    } catch (IOException e) {

                        logger.error("이미지 변환 중 오류 발생", e);
                    }
                }
                dto.setText("/thumbnail/" + thumbnailFilename);


                dto.setPidx(post.getPidx());
                dto.setBidx(post.getBoard().getBidx());
                dto.setTitle(post.getTitle());

                /* File thumbnailFile = new File("C:/data/image/thumbnail/" + thumbnailFilename);
                if (thumbnailFile.exists()) {

                    boolean deleted = thumbnailFile.delete();
                    if (!deleted) {
                    // 삭제 실패 시 추가 처리가 필요합니다.
                    }
                } */

                return dto;
            })
            .collect(Collectors.toList());

        return dtoList;
    };


    @Override
    public List<DTOPopularPost> getPopularPostOfPlace(String boardPlace) {

        List<EntityPost> posts = repoPost.findTop4ByBoardPlaceOrderByGoodCountDesc(boardPlace);

        List<DTOPopularPost> dtoList = posts.stream()
            .map(post -> {

                DTOPopularPost dto = new DTOPopularPost();

                int start = post.getText().indexOf("src=");
                String image = "";

                if (start != -1) {

                    image = post.getText().substring(start + 14, post.getText().indexOf("\"", start + 5));
                }
                String newImage = "/app/data/image/" + image;

                String thumbnailFilename = post.getPidx() + image;
                String thumbnailFilePath = "/app/data/image/thumbnail/" + thumbnailFilename;
                File thumbnailFile = new File(thumbnailFilePath);

                thumbnailFile.getParentFile().mkdirs();

                if (!thumbnailFile.exists()) {

                    try {

                        Thumbnails.of(new File(newImage))
                                .size(180, 110)
                                .outputQuality(0.6)
                                .toFile(thumbnailFile);

                    } catch (IOException e) {

                        logger.error("이미지 변환 중 오류 발생", e);
                    }
                }
                dto.setText("/thumbnail/" + thumbnailFilename);

                dto.setPidx(post.getPidx());
                dto.setBidx(post.getBoard().getBidx());
                dto.setTitle(post.getTitle());

                return dto;
            })
            .collect(Collectors.toList());

        return dtoList;
    };
}
