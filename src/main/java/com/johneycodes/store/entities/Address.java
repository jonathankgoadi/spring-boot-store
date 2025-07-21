package com.johneycodes.store.entities;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "street")
    private String street;
    @Column(name = "city")
    private String city;
    @Column(name="province")
    private String province;
    @Column(name = "zipcode")
    private String zipcode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private  User user;
}
