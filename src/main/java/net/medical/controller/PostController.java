package net.medical.controller;

import net.medical.form.PostRequestForm;
import net.medical.model.Comment;
import net.medical.model.Post;
import net.medical.model.User;
import net.medical.page.Pages;
import net.medical.service.PageService;
import net.medical.service.PostService;
import net.medical.util.ImageUtil;
import net.medical.util.PageableUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class PostController implements Pages {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int DEFAULT_SIZE = 4;

    @Autowired
    private ImageUtil imageUtil;

    @Autowired
    private PostService postService;

    @Autowired
    private PageService pageService;

    @GetMapping("/post/add")
    public String addPostGet(Model model){
        model.addAttribute("form",new PostRequestForm());
        return ADD_POST;
    }

    @PostMapping("/post/add")
    public String addPostPost(@ModelAttribute("form")@Valid PostRequestForm form, BindingResult result,
                              @RequestAttribute("user")User doctor){
        LOGGER.debug("form : {},error counts : {}",form,result.getFieldErrorCount());
        if(result.hasErrors()){
            return ADD_POST;
        }else if(form.getImage().isEmpty()){
            result.addError(new FieldError("form","image",""));
            return ADD_POST;
        }else if(!imageUtil.isValidData(form.getImage().getContentType())){
            result.addError(new FieldError("form","imageFormatError",""));
            return ADD_POST;
        }else {
            Post post = Post.builder()
                    .title(form.getTitle())
                    .description(form.getDescription())
                    .imgUrl("")
                    .createdDate(new Date())
                    .user(doctor)
                    .build();
            postService.add(post,form.getImage());
            return "redirect:/";
        }
    }

    @GetMapping("/posts")
    public String posts(Pageable pageable,Model model){
        int length = PageableUtil.getLength(postService.countAll(),DEFAULT_SIZE);
        pageable = PageableUtil.checkedPageable(pageable,length);
        model.addAttribute("posts",postService.getAll(pageable));
        model.addAttribute("length",length);
        model.addAttribute("page",pageable.getPageNumber());
        return POSTS;
    }

    @GetMapping("/post/{id}")
    public String post(@PathVariable("id")String strId,Model model){
        LOGGER.debug("id : {}",strId);
        int id = getId(strId);
        if(id == -1){
            return "redirect:/";
        }else {
            model.addAttribute("postPage",pageService.getPost(id));
            return POST;
        }
    }

    @PostMapping("/post/comment/add")
    public @ResponseBody
    Comment commentAdd(@RequestParam("comment")String text,
                       @RequestParam("postId")int postId,@RequestAttribute("user")User user){
        LOGGER.debug("comment text : {},postId : {}",text,postId);
        if(text== null || text.length() < 4){
            return null;
        }else {
            Comment comment = Comment.builder()
                    .comment(text)
                    .post(Post.builder().id(postId).build())
                    .sendDate(new Date())
                    .user(user)
                    .build();
            return postService.addComment(comment);
        }
    }

    @PostMapping("/doctor/post/delete")
    public String delete(@RequestParam("postId")int postId){
        LOGGER.debug("postId : {}",postId);
        postService.deleteByid(postId);
        return "redirect:/";
    }

    @GetMapping("/doctor/posts")
    public String doctorPosts(@RequestAttribute("user")User doctor,
                              Pageable pageable,Model model){
        int length = PageableUtil.getLength(postService.countByDoctorId(doctor.getId()),DEFAULT_SIZE);
        pageable = PageableUtil.checkedPageable(pageable,length);
        model.addAttribute("posts",postService.getAllByDoctorId(doctor.getId(),pageable));
        model.addAttribute("page",pageable.getPageNumber());
        model.addAttribute("length",length);
        model.addAttribute("doctorPage",true);
        return POSTS;
    }

    private int getId(String strId){
        int id;
        try {
            id = Integer.parseInt(strId);
            if(id <= 0 || !postService.existsById(id)){
                id= -1;
            }
        }catch (NumberFormatException e){
            id = -1;
        }
        return id;
    }
}
