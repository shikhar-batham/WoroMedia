package com.boroMediaLLP.boromedia.payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private Integer id;
    private String firstName;
    private String password;
    private String email;
    private RolesDto roles;
}
