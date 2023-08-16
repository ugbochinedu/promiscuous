package africa.semicolon.promiscuous.models;



import jakarta.persistence.*;
import lombok.*;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "users")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate dateOfBirth;
    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Enumerated(value = EnumType.STRING)
    private Role authority;

    private boolean isActive;
    private LocalDateTime createdAt;


    @PrePersist
    public void setCreatedAt(){
        createdAt = LocalDateTime.now();
    }

}
