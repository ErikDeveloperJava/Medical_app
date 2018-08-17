package net.medical.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisteredInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String message;
}
