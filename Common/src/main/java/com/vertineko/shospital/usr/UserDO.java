package com.vertineko.shospital.usr;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDO {

    private Long id;
    private String username;
    private String role;
}
