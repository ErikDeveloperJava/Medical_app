package net.medical.page;

import javafx.geometry.Pos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.medical.model.Post;
import net.medical.model.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainPage {

    private List<User> doctors;

    private List<PostDetail> posts;
}
