package com.mentoring.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@EqualsAndHashCode(exclude = "addressPk")
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_pk")
    private Long addressPk;

    @Column
    private String city;

    @Column
    private String street;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "flat_number")
    private String flatNumber;

    @Column(name = "postal_code")
    private String postalCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk")
    @JsonIgnore // to avoid infinite recursion with Jackson JSON and Hibernate JPA
    private User user;

}
