package net.medical.form;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorRequestForm {

    @Length(min = 2,max = 255)
    private String name;

    @Length(min = 4,max = 255)
    private String surname;

    @Length(min = 4,max = 255)
    private String username;

    @Length(min = 6,max = 255)
    private String password;

    @Length(min = 6,max = 255)
    private String repeatPassword;

    private MultipartFile image;

    private String usernameExists;

    private String passwordsNotMatch;

    private String imageFormatError;

    @Length(min = 10)
    private String information;

    private int department;

    private List<String> days;
}
