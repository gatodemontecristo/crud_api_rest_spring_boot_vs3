package com.mitocode.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
/*@Getter
@Setter
@EqualsAndHashCode
@ToString*/
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idPatient;

    @Column(nullable = false,length = 70) //,name = "xyz")
    private String firstName;

    @Column(nullable = false,length = 70)
    private String lastName;

    @Column(nullable = false,length = 8)
    private String dni;

    @Column(length = 150)
    private String address;

    @Column(length = 9,nullable = false)
    private String phone;

    @Column(length = 55 ,nullable = false)
    private String email;


}
