package com.qjp.mybatis;

import com.qjp.mybatis.mapper.StudentMapper;
import com.qjp.mybatis.po.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@SpringBootTest
public class MapperProxyTest {
	private SqlSessionFactory sqlSessionFactory;
	private StudentMapper studentMapper;
	@Before
	public void init() throws IOException {
		InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		studentMapper = sqlSession.getMapper(StudentMapper.class);
	}

	@Test
	public void test() {
		List<Student> all = studentMapper.findAll();
		all.forEach(System.out::println);
	}

	@Test
	public void testUpdate() {
		System.out.println(studentMapper.updateById(1));
	}
}
