package net.medical.repository;

import net.medical.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository  extends JpaRepository<Comment,Integer> {

    int countByPostId(int postId);

    List<Comment> findAllByPostId(int postId);
}
