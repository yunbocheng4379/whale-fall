package com.sea.whale.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 200L;

    private Integer id;

    private String userName;

    private String password;

    private Integer flag;

}
