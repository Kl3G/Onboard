package com.example.Portfolio_Onboard.Service;

import com.example.Portfolio_Onboard.DTO.DTOCommentView;
import com.example.Portfolio_Onboard.DTO.DTOCreateChildComments;
import com.example.Portfolio_Onboard.DTO.DTOCreateComment;
import com.example.Portfolio_Onboard.Entity.EntityComments;
import com.example.Portfolio_Onboard.Entity.EntityMemberInfo;
import com.example.Portfolio_Onboard.Entity.EntityPost;
import com.example.Portfolio_Onboard.Repository.RepoChildComments;
import com.example.Portfolio_Onboard.Repository.RepoComment;
import com.example.Portfolio_Onboard.Repository.RepoMemberInfo;
import com.example.Portfolio_Onboard.Repository.RepoPost;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ServiceCommentImpl implements ServiceComment{

    private final RepoPost repoPost;
    private final RepoComment repoComment;
    private final RepoChildComments repoChildComments;
    private final RepoMemberInfo repoMemberInfo;

    public ServiceCommentImpl(RepoPost repoPost, RepoComment repoComment, RepoChildComments repoChildComments, RepoMemberInfo repoMemberInfo) {
        this.repoPost = repoPost;
        this.repoComment = repoComment;
        this.repoChildComments = repoChildComments;
        this.repoMemberInfo = repoMemberInfo;
    }


    @Override
    public String setComment(DTOCreateComment dtoCreateComment) {

        EntityPost post = repoPost.findByPidx(dtoCreateComment.getPidx());
        EntityMemberInfo memberInfo = repoMemberInfo.findByUserid(dtoCreateComment.getUserid());

        EntityComments entityComment = dtoCreateComment.entityComment(post, memberInfo);

        repoComment.save(entityComment);
        String pidx = String.valueOf(dtoCreateComment.getPidx());
        String bidx = String.valueOf(post.getBoard().getBidx());

        return "redirect:/post?pidx=" + pidx + "&bidx=" + bidx;
    }

    @Override
    public String setChildComment(DTOCreateChildComments dtoCreateChildComments) {

        EntityComments comment = repoComment.findByCidx(dtoCreateChildComments.getCidx());
        EntityMemberInfo memberInfo = repoMemberInfo.findByUserid(dtoCreateChildComments.getUserid());

        repoChildComments.save(dtoCreateChildComments.setEntityChildComments(comment, memberInfo));

        String pidx = String.valueOf(comment.getPost().getPidx());
        String bidx = String.valueOf(comment.getPost().getBoard().getBidx());

        return "redirect:/post?pidx=" + pidx + "&bidx=" + bidx;
    }

    @Override
    public long countComments(Long pidx) {

        return repoComment.countByPost_Pidx(pidx);
    }

    @Override
    public Page<DTOCommentView> getCommentList(Long pidx, Pageable pageable) {

        Page<EntityComments> commentList = repoComment.findByPost_Pidx(pidx, pageable);
        log.error("commentList :" + commentList);

        Page<DTOCommentView> commentPage = commentList
                .map(entityComments -> {

                    DTOCommentView dto = new DTOCommentView();
                    dto.setText(entityComments.getText());
                    dto.setRegdate(entityComments.getRegdate());
                    dto.setPost(entityComments.getPost());
                    dto.setNick(entityComments.getNick());
                    dto.setCidx(entityComments.getCidx());
                    dto.setUserip(entityComments.getUserip());
                    dto.setChildcommentList(entityComments.getChildcommentList());

                    return dto;
                });

        return commentPage;
    }


}
