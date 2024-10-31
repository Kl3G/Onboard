package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOCreatePost;
import com.example.Portfolio_Onboard.DTO.DTOModifyPost;
import com.example.Portfolio_Onboard.Entity.EntityFiles;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import com.example.Portfolio_Onboard.Entity.EntityWorld;
import com.example.Portfolio_Onboard.Repository.RepoFiles;
import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import com.example.Portfolio_Onboard.Repository.RepoPost;
import com.example.Portfolio_Onboard.Repository.RepoWorld;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
public class ServiceCreatePostImpl implements ServiceCreatePost{

    private final RepoMemberInfo repoMemberInfo;
    private final RepoWorld repoWorld;
    private final RepoPost repoPost;
    private final RepoFiles repoFiles;

    public ServiceCreatePostImpl(RepoMemberInfo repoMemberInfo, RepoWorld repoWorld, RepoPost repoPost, RepoFiles repoFiles) {
        this.repoMemberInfo = repoMemberInfo;
        this.repoWorld = repoWorld;
        this.repoPost = repoPost;
        this.repoFiles = repoFiles;
    }

    @Override
    public String setPost(DTOCreatePost dtoCreatePost) {

        EntityMemberInfo MemberInfo = repoMemberInfo.findByUserid(dtoCreatePost.getUserid());
        Optional<EntityWorld> optionalBoard = repoWorld.findById(dtoCreatePost.getBidx());
        /*Optional<EntityFiles> optionalFiles = repoFiles.findById(dtoCreatePost.getPidx());*/

        if (optionalBoard.isPresent()) {

            EntityWorld board = optionalBoard.get();
            /*EntityFiles files = optionalFiles.get();*/
            MultipartFile[] files = dtoCreatePost.getFiles();
            String[] filePaths = new String[files.length];
            String filePath = "";
            for (MultipartFile file : files) {

                filePath = "D:/Portfolio_Onboard/src/main/resources/static/data/" + file.getOriginalFilename(); // 파일 경로를 String으로 변환
            }


            EntityPost entityPost2 = dtoCreatePost.entityPost(MemberInfo, board, null);
            EntityFiles entityFiles = new EntityFiles(dtoCreatePost.getPidx(), entityPost2, filePath);
            EntityPost entityPost = dtoCreatePost.entityPost(MemberInfo, board, entityFiles);
            repoPost.save(entityPost);
        }

        return "redirect:/index";
    }

    @Override
    public String updatePost(DTOModifyPost dtoModifyPost) {

        EntityPost post = repoPost.getReferenceById(dtoModifyPost.getPidx());
        post.setPpwd(dtoModifyPost.getPpwd());
        post.setCategory(dtoModifyPost.getCategory());
        post.setText(dtoModifyPost.getText());
        post.setTitle(dtoModifyPost.getTitle());
        post.setUserip(dtoModifyPost.getUserip());
        post.setNewdate(new Date());

        repoPost.save(post);

        return "redirect:/index";
    }

    @Override
    public Optional<EntityPost> createOrUpdate(Long pidx) {
        if (pidx != null && pidx > 0) {

            return repoPost.findById(pidx);
        }
        return Optional.empty();
        /* Optional<PeopleEntity> 타입의 null 처리는 Optional.empty(); 로 해 준다.
        Optional<PeopleEntity> people;처럼 선언만 하면 기본값이 null이 된다.
        이 경우 null 체크를 피할 수 없으며, 나중에 사용 시 NullPointerException이 발생할 위험이 있다.
        따라서, 안전한 코드 작성을 위해 Optional을 선언할 때는 반드시 초기화하는 것이 좋다. */
    }
}
