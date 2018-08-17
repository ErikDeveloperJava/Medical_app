package net.medical.form;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorRegisteredRequestForm {

    @Length(min = 4,max = 255)
    private String title;

    private String date;

    @Length(min = 10)
    private String message;

    private int doctorId;
}
