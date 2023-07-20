package africa.semicolon.promiscuous.models;

import jakarta.persistence.*;

@Entity
@Table
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String houseNumber;
    private String street;
    private String state;
    private String country;
}
