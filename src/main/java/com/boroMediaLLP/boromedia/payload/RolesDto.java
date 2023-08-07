package com.boroMediaLLP.boromedia.payload;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RolesDto {

    private Integer roleId;
    private String name;
}
