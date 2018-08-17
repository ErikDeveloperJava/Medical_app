package net.medical.form;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorRegisteredResponseForm {

    private boolean titleError;

    private boolean dateError;

    private boolean messageError;

    private boolean success;

    private boolean exists;
}
