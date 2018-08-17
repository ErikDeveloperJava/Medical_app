package net.medical.service;

import net.medical.model.Comment;
import net.medical.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    void add(Post post, MultipartFile image);

    List<Post> getAll(Pageable pageable);

    int countAll();

    boolean existsById(int id);

    Comment addComment(Comment comment);

    public void deleteByid(int id);

    List<Post> getAllByDoctorId(int doctorId,Pageable pageable);

    int countByDoctorId(int doctorId);
}
