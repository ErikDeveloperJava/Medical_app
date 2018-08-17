package net.medical.form;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequestForm {

    @Length(min = 2,max = 255)
    private String name;

    @Length(min = 4,max = 255)
    private String surname;

    @Length(min = 4,max = 255)
    private String username;

    @Length(min = 6,max = 255)
    private String oldPassword;

    @Length(min = 6,max = 255)
    private String newPassword;
}
