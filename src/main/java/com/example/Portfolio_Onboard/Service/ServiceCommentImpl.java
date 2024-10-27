package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOCreateComment;
import com.example.Portfolio_Onboard.Entity.EntityComments;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import com.example.Portfolio_Onboard.Repository.RepoComment;
import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import com.example.Portfolio_Onboard.Repository.RepoPost;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCommentImpl implements ServiceComment{

    private final RepoPost repoPost;
    private final RepoComment repoComment;
    private final RepoMemberInfo repoMemberInfo;

    public ServiceCommentImpl(RepoPost repoPost, RepoComment repoComment, RepoMemberInfo repoMemberInfo) {
        this.repoPost = repoPost;
        this.repoComment = repoComment;
        this.repoMemberInfo = repoMemberInfo;
    }


    @Override
    public String setComment(DTOCreateComment dtoCreateComment) {

        EntityPost Post = repoPost.findByPidx(dtoCreateComment.getPidx());
        EntityMemberInfo MemberInfo = repoMemberInfo.findByUserid(dtoCreateComment.getUserid());

        EntityComments entityComment = dtoCreateComment.entityComment(Post, MemberInfo);

        repoComment.save(entityComment);

        return "redirect:/index";
    }

    @Override
    public long countComments(Long pidx) {

        return repoComment.countByPost_Pidx(pidx);
    }

    @Override
    public List<EntityComments> getCommentList(Long pidx) {

        return repoComment.findByPost_Pidx(pidx);
    }


}
