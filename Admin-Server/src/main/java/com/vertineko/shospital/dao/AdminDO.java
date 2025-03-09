package com.vertineko.shospital.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.vertineko.shospital.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("admin")
public class AdminDO extends BaseDO{

    /**
     * 管理员编号
     */
    private Long id;

    /**
     * 用户名/邮箱
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 联系电话
     */
    private String tele;

    /**
     * 职责
     */
    private Role role;
}
