package net.medical.form;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequestForm {

    @Length(min = 4,max = 255)
    private String title;

    @Length(min = 10)
    private String description;

    private String imageFormatError;

    private MultipartFile image;
}
