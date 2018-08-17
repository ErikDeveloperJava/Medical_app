package net.medical.repository;

import net.medical.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findAllByUserId(int userId,Pageable pageable);

    int countByUserId(int userId);
}
