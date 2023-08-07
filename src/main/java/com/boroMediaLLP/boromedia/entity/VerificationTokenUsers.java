package com.boroMediaLLP.boromedia.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VerificationTokenUsers {

    @Id
    @SequenceGenerator(name = "verificationTokenSeeker_seq", sequenceName = "verificationTokenSeeker_seq", allocationSize = 1)
    @GeneratedValue(generator = "verificationTokenSeeker_seq", strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "expiration")
    private Date expiration;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", unique = true)
    private Users users;
}
