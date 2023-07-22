package com.webapp.socialmedia.domain.repositories;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.post.Post;
import com.webapp.socialmedia.domain.model.post.Visibility;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Post findPostById(Long id);

    List<Post> findPostsByAccount_Username(String username);

    @Query("SELECT p as post, " +
            "       SUM(CASE WHEN v.type='LIKE' THEN 1 ELSE 0 END) as likes, " +
            "       SUM(CASE WHEN v.type='DISLIKE' THEN 1 ELSE 0 END) as dislikes," +
            "       SUM(CASE WHEN v.account = :user THEN " +
            "               CASE WHEN v.type='LIKE' THEN 1 " +
            "                    WHEN v.type='DISLIKE' THEN -1" +
            "                    ELSE 0 END" +
            "           ELSE 0 END) as userVoteType," +
            "       COUNT(DISTINCT c) as commentsLength " +
            "FROM Post p " +
            "   LEFT JOIN Vote v ON p.id = v.entityId " +
            "   JOIN FETCH p.account a " +
            "   LEFT JOIN Comment c ON p.id = c.postId " +
            "WHERE ( :sgId IS NULL OR p.id = :sgId ) " +
            "   AND ( :visi IS NULL OR p.visibility IN (:visi) ) " +
            "   AND ( COALESCE(:accs, null) IS NULL OR p.account IN (:accs) ) " +
            "GROUP BY p, a ") // put Distinct or else each unique vote gets mapped once to every comment (or just divide by commentsLength)
    List<PostDto> getPostsWithVotesAndUserVoteType(@Param(value = "visi") List<Visibility> visibility,
                                                   @Param(value = "user") Account user,
                                                   @Param(value = "accs") Set<Account> accounts,
                                                   @Param(value = "sgId") Long singlePostId,
                                                   Sort sort);

    public interface PostDto {
        public Post getPost();
        public Long getLikes();
        public Long getDislikes();
        public int getUserVoteType();
        public Long getCommentsLength();
    }

//    List<Post> findPostsByVisibility(Visibility visibility);
//    List<Post> findPostsByVisibilityAndAccountIn(Visibility visibility, Set<Account> following);
//    List<Post> findPostsByAccountIn(Set<Account> friends);

//@Query("SELECT p as post, " +
//        "       SUM(DISTINCT CASE WHEN v.type='LIKE' THEN 1 ELSE 0 END) as likes, " +
//        "       SUM(DISTINCT CASE WHEN v.type='DISLIKE' THEN 1 ELSE 0 END) as dislikes," +
//        "       SUM(DISTINCT CASE WHEN v.account = :user THEN " +
//        "               CASE WHEN v.type='LIKE' THEN 1 " +
//        "                    WHEN v.type='DISLIKE' THEN -1" +
//        "                    ELSE 0 END" +
//        "           ELSE 0 END) as userVoteType," +
//        "       COUNT(c) as commentsLength " +
//        "FROM Post p " +
//        "   LEFT JOIN Vote v ON p.id = v.entityId " +
//        "   JOIN FETCH p.account a " +
//        "   LEFT JOIN Comment c ON p.id = c.postId " +
//        "WHERE p.id = :postId " +
//        "GROUP BY p, a ")
//    List<PostDto> getPostWithVotes(@Param(value = "user") Account user,
//                                   @Param(value = "postId") Long postId);



}


//    SELECT p as post,
//    SUM( DISTINCT CASE WHEN v.type='LIKE' THEN 1 ELSE 0 END) as likes,
//    SUM( DISTINCT CASE WHEN v.type='DISLIKE' THEN 1 ELSE 0 END) as dislikes,
//    SUM( DISTINCT CASE WHEN v.account_id = 7 THEN
//            CASE WHEN v.type='LIKE' THEN 1
//            WHEN v.type='DISLIKE' THEN -1
//            ELSE 0 END
//            ELSE 0 END) as userVoteType,
//    COUNT(c) as commentsLength
//    FROM Post p
//        LEFT JOIN Vote v ON p.id = v.entity_id
//        JOIN Account a ON p.account_id = a.id
//        LEFT JOIN Comment c ON p.id = c.post_id
//        WHERE null IS NULL OR p.id = 5
//        AND (null IS NULL OR p.visibility IN ('PUBLIC'))
//        AND (null IS NULL OR p.account_id IN (1,7))
//        GROUP BY p, a