package net.medical.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkingDays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "day_id")
    private Days days;

    private String workingHours;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}
