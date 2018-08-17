package net.medical.service.impl;

import net.medical.model.Comment;
import net.medical.model.Post;
import net.medical.repository.CommentRepository;
import net.medical.repository.PostRepository;
import net.medical.service.PostService;
import net.medical.util.ImageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ImageUtil imageUtil;

    @Transactional(rollbackFor = Exception.class)
    public void add(Post post, MultipartFile image) {
        post = postRepository.save(post);
        String imgName = System.currentTimeMillis() + image.getOriginalFilename();
        String parent = post.getUser().getId() + "/" + post.getId();
        try {
            imageUtil.save(parent,imgName,image.getBytes());
            post.setImgUrl(parent + "/" + imgName);
            LOGGER.debug("post saved");
        }catch (Exception  e){
            imageUtil.delete(parent);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Post> getAll(Pageable pageable) {
        return postRepository.findAll(PageRequest.
                of(pageable.getPageNumber(),pageable.getPageSize(),Sort.by(Sort.Direction.DESC,"createdDate"))).getContent();
    }

    @Override
    public int countAll() {
        return (int) postRepository.count();
    }

    @Override
    public boolean existsById(int id) {
        return postRepository.existsById(id);
    }

    @Transactional
    public Comment addComment(Comment comment) {
        comment = commentRepository.save(comment);
        LOGGER.debug("comment saved");
        return comment;
    }

    @Transactional
    public void deleteByid(int id){
        postRepository.deleteById(id);
        LOGGER.debug("post deleted");
    }

    @Override
    public List<Post> getAllByDoctorId(int doctorId,Pageable pageable) {
        return postRepository.findAllByUserId(doctorId,pageable);
    }

    @Override
    public int countByDoctorId(int doctorId) {
        return postRepository.countByUserId(doctorId);
    }
}
