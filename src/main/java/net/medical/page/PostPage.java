package net.medical.page;

import lombok.*;
import net.medical.model.Comment;
import net.medical.model.Post;
import net.medical.model.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostPage {

    private Post post;

    private List<Comment> comments;

    private User doctor;
}
