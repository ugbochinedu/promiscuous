package africa.semicolon.promiscuous.models;

import jakarta.persistence.*;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, columnDefinition= "MEDIUMTEXT", length = 1000)
    private String content;
    private Long sender;

    @ManyToOne
    private User user;
}
