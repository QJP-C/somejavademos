package com.qjp.mybatis.mapper;

import com.qjp.mybatis.po.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class DemoMapperTest {
    private SqlSessionFactory sqlSessionFactory;
    private DemoMapper demoMapper;
    private SqlSession sqlSession;
     @Before
    public void init() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        sqlSession = sqlSessionFactory.openSession();
        demoMapper = sqlSession.getMapper(DemoMapper.class);
    }
    @Test
    public void insert1() {
        Student student = new Student(-1, "Podman", 130, 15, 0);
        demoMapper.insert1(student);
        sqlSession.commit();
        System.out.println(student.getId());
    }

    @Test
    public void insert2() {
        Student student = new Student(-1, "Podmanss", 130, 15, 0);
        demoMapper.insert2(student);
        sqlSession.commit();
        System.out.println(student.getId());
    }

    @Test
    public void batchFind() {
        List<Student> students = demoMapper.batchFind(Arrays.asList(1, 2, 3, 5, 9));
        students.forEach(System.out::println);
    }
}