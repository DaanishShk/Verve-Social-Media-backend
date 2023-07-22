package com.webapp.socialmedia.domain.repositories;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.comment.Comment;
import com.webapp.socialmedia.domain.model.post.Post;
import com.webapp.socialmedia.domain.model.post.Visibility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c as comment, " +
            "       SUM(CASE WHEN v.type='LIKE' THEN 1 ELSE 0 END) as likes, " +
            "       SUM(CASE WHEN v.type='DISLIKE' THEN 1 ELSE 0 END) as dislikes," +
            "       SUM(CASE WHEN v.account = :user THEN " +
            "               CASE WHEN v.type='LIKE' THEN 1 " +
            "                    WHEN v.type='DISLIKE' THEN -1" +
            "                    ELSE 0 END" +
            "           ELSE 0 END) as userVoteType " +
            "FROM Comment c " +
            "   LEFT JOIN Vote v ON c.id = v.entityId " +
            "   JOIN FETCH c.account a " +
            "WHERE :postId = c.postId " +
            "GROUP BY c, a " +
            "ORDER BY c.timestamp ")
    List<CommentDto> findCommentsByPost(@Param(value = "postId") Long postId,
                                        @Param(value = "user") Account user);

    public interface CommentDto {
        public Comment getComment();
        public Long getLikes();
        public Long getDislikes();
        public int getUserVoteType();
    }

    Slice<Comment> findCommentsByAccount_UsernameOrderByTimestampDesc(String username, Pageable pageable);

    @Query("SELECT c " +
            "FROM Comment c" +
            "   JOIN Post p ON c.postId = p.id " +
            "WHERE p.visibility = 'PUBLIC' " +
            "   AND c.account.username = :username " +
            "ORDER BY c.timestamp")
    Slice<Comment> findCommentsByPostVisibilityPublic(@Param(value = "username") String username, Pageable pageable);
}