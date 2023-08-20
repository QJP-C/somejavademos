package com.qjp.mybatis.mapper;

import com.qjp.mybatis.po.Student;

import java.util.List;

/**
 *
 */
public interface DemoMapper {
    /**
     * 主键自增1
     * @param student
     * @return
     */
    Integer insert1(Student student);

    /**
     * 主键自增2
     * @param student
     * @return
     */
    Integer insert2(Student student);

    /**
     * 批量查询 （id列表）
     * @param params
     * @return
     */
    List<Student> batchFind(List<Integer> params);
}
