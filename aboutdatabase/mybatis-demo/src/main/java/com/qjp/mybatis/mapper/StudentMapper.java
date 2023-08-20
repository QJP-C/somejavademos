package com.qjp.mybatis.mapper;



import com.qjp.mybatis.po.Student;

import java.util.List;

/**
 * Mapper代理版
 * 全局配置文件 mybatis-config.xml 和 mapper.xml 文件是最基本的配置，仍然需要
 */
public interface StudentMapper {

	/*  1.编写mapper.xml，书写SQL，并定义好SQL的输入参数，和输出参数
		2.编写全局配置文件，配置数据源，以及要加载的mapper.xml文件
		3.通过全局配置文件，创建SqlSessionFactory
		4.每次进行CRUD时，通过SqlSessionFactory创建一个SqlSession
		5.调用SqlSession上的selectOne，selectList，insert，delete，update等方法，传入mapper.xml中SQL标签的id，以及输入参数*/


	List<Student> findAll();

	int insert(Student student);

	int delete(Integer id);

	int updateById(Integer id);

	List<Student> findByName(String value);
}
