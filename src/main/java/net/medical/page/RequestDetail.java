package net.medical.page;

import lombok.*;
import net.medical.model.DoctorRegistered;
import net.medical.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDetail {

    private User user;

    private DoctorRegistered registered;
}
