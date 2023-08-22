package com.qjp.mp.po;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@TableName("user") 	//@Tablename注解注解用来将指定的数据库表和 JavaBean 进行映射。多用于项目中entity包下 实体类中
public class User {
	@TableId(value = "id",type = IdType.AUTO)     //@TableId(value=“数据库主键字段”,type = IdType.六种类型之一)
	private Long id;
	/*	id六种类型
		Auto	数据库自增
		Input	自行输入
		ID_Worker	分布式全局唯一ID 长整型类型
		UUID	32位UUID字符串
		NONE	无状态
		ID_WORKER_STR	分布式全局唯一ID 字符串类型	*/

	@TableField(condition = SqlCondition.LIKE)   // 配置该字段使用like进行拼接(直接使用对象作为查询条件时生效)  EQUAL NOT_EQUAL LIKE LIKE_LEFT LIKE_RIGHT 五种  也可以自定义
	private String name;
	private Integer age;
	private String email;
	@TableField(value = "manager_id",exist = true) //主要用来解决实体类的字段名与数据库中的字段名不匹配的问题 (managerId  manager_id)
													//是否为数据库表字段 默认为true           其他属性查看 REDEME.md
	private Long managerId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
	private LocalDateTime createTime;

//	@TableLogic注解
//	逻辑删除注解: 一般用于项目中的逻辑删除字段上添加该注解		其他属性查看 REDEME.md
}
