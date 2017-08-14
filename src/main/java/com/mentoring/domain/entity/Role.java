package com.mentoring.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author ivanovaolyaa
 * @version 8/10/2017
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@EqualsAndHashCode(exclude = "rolePk")
@ToString(exclude = "rolePk")
public class Role implements GrantedAuthority {

    public static final String USER = "ROLE_USER";
    public static final String ADMIN = "ROLE_ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_pk")
    private Long rolePk;

    @Column(name = "name")
    private String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
