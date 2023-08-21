package com.qjp.mybatis;

import com.qjp.mybatis.dao.StudentDao;
import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest(classes = MybatisDemoApplication.class)
public class DaoTest {

    @Resource
    private StudentDao studentDao;

    /**
     * 初始化
     * @throws IOException
     */
    @Before
    public void init() throws IOException {
        studentDao = new StudentDao("mybatis-config.xml");
    }

//    @Test
//    public void insertTest() {
//        Student student = new Student();
//        student.setName("qjpqqqjp");
//        student.setAge(24);
//        student.setGender(1);
//        student.setScore(100);
//        studentDao.addStudent(student);
//    }
//
//    @Test
//    public void findAllTest() {
//        List<Student> all = studentDao.findAll();
//        all.forEach(System.out::println);
//    }
}
