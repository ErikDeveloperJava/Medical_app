package net.medical.page;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestPage {

    private List<RequestDetail> details;
}
