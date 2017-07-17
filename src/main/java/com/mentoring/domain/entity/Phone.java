package com.mentoring.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "phones")
@Getter
@Setter
@EqualsAndHashCode(exclude = "phonePk")
@ToString
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_pk")
    private Long phonePk;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private PhoneType phoneType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk")
    @JsonIgnore
    private User user;

    public enum PhoneType {
        HOME, MOBILE, WORK
    }

}
