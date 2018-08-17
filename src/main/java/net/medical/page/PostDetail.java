package net.medical.page;

import lombok.*;
import net.medical.model.Post;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetail {

    private Post post;

    private int commentCount;
}
