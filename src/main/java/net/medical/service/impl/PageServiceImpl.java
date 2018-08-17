package net.medical.service.impl;

import net.medical.model.Post;
import net.medical.model.User;
import net.medical.model.enums.UserRole;
import net.medical.page.DoctorPage;
import net.medical.page.MainPage;
import net.medical.page.PostDetail;
import net.medical.page.PostPage;
import net.medical.repository.CommentRepository;
import net.medical.repository.PostRepository;
import net.medical.repository.UserRepository;
import net.medical.repository.WorkingDaysRepository;
import net.medical.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PageServiceImpl implements PageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private WorkingDaysRepository workingDaysRepository;

    public MainPage getMain(){
        List<PostDetail> details = new ArrayList<>();
        List<Post> posts = postRepository.findAll(PageRequest.of(0, 4,Sort.by(Sort.Direction.DESC,"createdDate"))).getContent();
        for (Post post : posts) {
            PostDetail detail = PostDetail.builder()
                    .post(post)
                    .commentCount(commentRepository.countByPostId(post.getId()))
                    .build();
            details.add(detail);
        }
        return MainPage.builder()
                .doctors(userRepository.find8BestDoctors(PageRequest.of(0,8)))
                .posts(details)
                .build();
    }

    @Override
    public DoctorPage getDoctor(User user, int doctorId) {
        User doctor;
        if(user.getId() != doctorId){
            doctor = userRepository.getOne(doctorId);
        }else {
            doctor = user;
        }

        return DoctorPage.builder()
                .doctor(doctor)
                .workingDaysList(workingDaysRepository.findAllByDoctorId(doctor.getDoctor().getId()))
                .doctors(userRepository.findAllDoctors(doctorId,PageRequest.of(0,8)))
                .build();
    }

    @Override
    public PostPage getPost(int id) {
        Post post = postRepository.getOne(id);
        return PostPage.builder()
                .post(post)
                .comments(commentRepository.findAllByPostId(id))
                .doctor(post.getUser())
                .build();
    }
}
