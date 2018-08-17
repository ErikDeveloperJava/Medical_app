package net.medical.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import net.medical.model.enums.UserRole;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"doctors","doctor"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private String username;

    private String password;

    private String imgUrl;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @JsonIgnore
    @OneToMany(mappedBy = "doctor")
    private List<User> doctors;
}