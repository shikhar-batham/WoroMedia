package com.boroMediaLLP.boromedia.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Roles {

    @Id
    private int id;

    @Column(name = "name")
    private String name;

}
