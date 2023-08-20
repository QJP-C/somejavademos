package com.qjp.mybatis.dao;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.qjp.mybatis.po.Student;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 原生开发版（用的少）
 */
public class StudentDao {
/*  1.编写mapper.xml，书写SQL，并定义好SQL的输入参数，和输出参数
  	2.编写全局配置文件，配置数据源，以及要加载的mapper.xml文件
  	3.通过全局配置文件，创建SqlSessionFactory
  	4.每次进行CRUD时，通过SqlSessionFactory创建一个SqlSession
  	5.调用SqlSession上的selectOne，selectList，insert，delete，update等方法，传入mapper.xml中SQL标签的id，以及输入参数*/
	private SqlSessionFactory sqlSessionFactory;
	/**
	 * 构建SqlSessionFactory
	 */
	public StudentDao(String configPath) throws IOException {
		InputStream inputStream = Resources.getResourceAsStream(configPath);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	public List<Student> findAll() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Student> studentList = sqlSession.selectList("findAll");
		sqlSession.close();
		return studentList;
	}

	public int addStudent(Student student) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int rowsAffected = sqlSession.insert("insert", student);
		sqlSession.commit();
		sqlSession.close();
		return rowsAffected;
	}

	public int deleteStudent(int id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int rowsAffected = sqlSession.delete("delete",id);
		sqlSession.commit();
		sqlSession.close();
		return rowsAffected;
	}
}

