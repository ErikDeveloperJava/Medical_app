package net.medical.page;

import lombok.*;
import net.medical.model.User;
import net.medical.model.WorkingDays;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorPage {

    private User doctor;

    private List<WorkingDays> workingDaysList;

    private List<User> doctors;
}
