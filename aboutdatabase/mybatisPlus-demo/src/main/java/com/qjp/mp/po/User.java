package com.qjp.mp.po;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@TableName("user") 	//@Tablename注解注解用来将指定的数据库表和 JavaBean 进行映射。多用于项目中entity包下 实体类中
public class User {
	@TableId(value = "id",type = IdType.ASSIGN_ID)     //@TableId(value=“数据库主键字段”,type = IdType.六种类型之一)
	private Long id;
	/*	局部主键策略                                  全局主键策略可在配置文件设置
		Auto	数据库ID自增，依赖于数据库。在插入操作生成SQL语句时，不会插入主键这一列
		Input	自行输入	需要手动设置主键，若不设置。插入操作生成SQL语句时，主键这一列的值会是null。oracle的序列主键需要使用这种方式
		ID_Worker	分布式全局唯一ID 长整型类型
		UUID	32位UUID字符串
		NONE	无状态  未设置主键类型。若在代码中没有手动设置主键，则会根据主键的全局策略自动生成（默认的主键全局策略是基于雪花算法的自增ID）
		ID_WORKER_STR	分布式全局唯一ID 字符串类型
		ASSIGN_ID  	当没有手动设置主键，即实体类中的主键属性为空时，才会自动填充，使用雪花算法
		ASSIGN_UUID   当实体类的主键属性为空时，才会自动填充，使用UUID		*/

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
