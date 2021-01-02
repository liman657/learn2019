package com.liman.learn.pmp.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

//用户实体
@Data
@TableName("sys_user")
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@TableId
	private Long userId;

	@NotBlank(message="用户名不能为空")
	private String username;

	private String name;

	//@NotBlank(message="密码不能为空")
	private String password;

	private String salt;

	@NotBlank(message="邮箱不能为空!")
	private String email;

	@NotBlank(message="手机号不能为空!")
	private String mobile;

	private Integer status;

	//@TableField：字段属性不为数据库表字段，但又是必须使用的
	@TableField(exist=false)
	private List<Long> roleIdList;

	private Date createTime;

	@NotNull(message="部门Id不能为空!")
	private Long deptId;

	@TableField(exist=false)
	private String deptName;

	@TableField(exist=false)
	private List<Long> postIdList;

	@TableField(exist=false)
	private String postName;
}
