package com.mentoring.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author ivanovaolyaa
 * @version 7/13/2017
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(exclude = "userPk")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_pk")
    private Long userPk;

    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Address> addresses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Phone> phones;

    public void addPhone(Phone phone) {
        phones.add(phone);
        phone.setUser(this);
    }

    public void addAddress(Address address) {
        addresses.add(address);
        address.setUser(this);
    }

}
