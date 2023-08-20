package com.qjp.mybatis;

import com.qjp.mybatis.mapper.PureStudentMapper;
import com.qjp.mybatis.po.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@SpringBootTest
public class PureMapperTest {

	private SqlSessionFactory sqlSessionFactory;

	private PureStudentMapper pureStudentMapper;
	@Before
	public void init() throws IOException {
		InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		pureStudentMapper = sqlSession.getMapper(PureStudentMapper.class);
	}

	@Test
	public void test() {
//
//		mapper.insert(new Student(10,"Tomcat",120,60,0));
//        sqlSession.commit();
		List<Student> studentList = pureStudentMapper.findAll();
		studentList.forEach(System.out::println);
	}
}
