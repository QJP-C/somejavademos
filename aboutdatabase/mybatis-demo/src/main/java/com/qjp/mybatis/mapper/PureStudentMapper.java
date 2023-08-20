package com.qjp.mybatis.mapper;

import com.qjp.mybatis.po.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * 注解版  使用注解的开发方式，也还是得有一个全局配置的xml文件，不过mapper.xml就可以省掉了
 */
public interface PureStudentMapper {

	@Select("SELECT * FROM student")
	List<Student> findAll();

	/**
	 * #{age}从传入的student中取age属性
	 * @param student
	 * @return
	 */
	@Insert("INSERT INTO student (name,age,score,gender) VALUES (#{name},#{age},#{score},#{gender})")
	int insert(Student student);

	/**
	 * 当使用注解开发时，若需要传入多个参数，可以结合@Param注解，示例如下
	 * @param name
	 * @param gender
	 * @return
	 */
	@Select("SELECT * FROM student WHERE name like '%${name}%' AND gender= '%${gender}%'")
	List<Student> find(@Param("name") String name, @Param("gender") Integer gender);
}
