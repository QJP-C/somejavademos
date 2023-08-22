package com.qjp.mp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qjp.mp.Enum.SexEnum;
import com.qjp.mp.mappers.UserMapper;
import com.qjp.mp.po.User;
import com.qjp.mp.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

	//mapper接口
	@Autowired
	private UserMapper userMapper;
	@Test
	public void testSelectAll() {
		List<User> list = userMapper.selectList(null);
		assertEquals(5, list.size());
		list.forEach(System.out::println);
	}


	//selectMaps
	/**
	 * 只查部分需要的字段
	 * 当某个表的列特别多，而SELECT的时候只需要选取个别列，查询出的结果也没必要封装成Java实体类对象时
	 * （只查部分列时，封装成实体后，实体对象中的很多属性会是null），
	 * 则可以用selectMaps，获取到指定的列后，再自行进行处理即可
	 */
	@Test
	public void testSelectMaps() {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.select("id","name","email").likeRight("name","黄");
		//SELECT id,name,email FROM user WHERE (name LIKE ?)
		List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
		maps.forEach(System.out::println);
	}

	/**
	 * 统计
	 * 按照直属上级进行分组，查询每组的平均年龄，最大年龄，最小年龄
	 */
	@Test
	public void testStatistics() {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.select("manager_id", "avg(age) avg_age", "min(age) min_age", "max(age) max_age")
				.groupBy("manager_id").having("sum(age) < {0}", 500);
		// select avg(age) avg_age ,min(age) min_age, max(age) max_age from user group by manager_id having sum(age) < 500;
		List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
		maps.forEach(System.out::println);
	}

	//selectObjs
	/**
	 * 只会返回第一个字段（第一列）的值，其他字段会被舍弃
	 */
	@Test
	public void testSelectObjs() {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.select("id", "name").like("name", "黄");
		List<Object> objects = userMapper.selectObjs(wrapper);
		objects.forEach(System.out::println);
	}


	//selectCount

	/**
	 * 查询满足条件的总数，注意，使用这个方法，不能调用QueryWrapper的select方法设置要查询的列了。这个方法会自动添加select count(1)
	 */
	@Test
	public void selectCount() {
		LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
		wrapper.like(User::getName, "黄");
		Integer count = userMapper.selectCount(wrapper);
		System.out.println(count);
	}




	//Service 接口			支持了更多的批量化操作，如saveBatch，saveOrUpdateBatch等方法。
	@Autowired
	private UserService userService;
	@Test
	public void testGetOne() {
		LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
		wrapper.gt(User::getAge, 28);
		User one = userService.getOne(wrapper, false); // 第二参数指定为false,使得在查到了多行记录时,不抛出异常,而返回第一条记录
		System.out.println(one);
	}

	/**
	 * 链式调用
	 */
	@Test
	public void testChain() {
		List<User> list = userService.lambdaQuery()
				.gt(User::getAge, 39)
				.likeRight(User::getName, "吴")
				.list();
		list.forEach(System.out::println);
	}

	/**
	 * lambdaUpdate()
	 */
	@Test
	public void testChain1() {
		userService.lambdaUpdate()
				.gt(User::getAge, 39)
				.likeRight(User::getName, "吴")
				.set(User::getEmail, "w39@baomidou.com")
				.update();
	}

	/**
	 * 批量更新部分字段
	 */
	@Test
	public void testChain2() {
		userService.lambdaUpdate()
				.gt(User::getAge, 39)
//				.likeRight(User::getName, "王")
				.set(User::getEmail, "w39@baomidou.com")
				.update();
	}

	/**
	 * 示例
	 * 条件构造器
	 */
	@Test
	public void test(){
		// 案例先展示需要完成的SQL语句，后展示Wrapper的写法

		// 1. 名字中包含佳，且年龄小于25
		// SELECT * FROM user WHERE name like '%佳%' AND age < 25
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.like("name", "佳").lt("age", 25);
		List<User> users = userMapper.selectList(wrapper);
		// 下面展示SQL时，仅展示WHERE条件；展示代码时, 仅展示Wrapper构建部分

		// 2. 姓名为黄姓，且年龄大于等于20，小于等于40，且email字段不为空
		// name like '黄%' AND age BETWEEN 20 AND 40 AND email is not null
		wrapper.likeRight("name","黄").between("age", 20, 40).isNotNull("email");

		// 3. 姓名为黄姓，或者年龄大于等于40，按照年龄降序排列，年龄相同则按照id升序排列
		// name like '黄%' OR age >= 40 order by age desc, id asc
		wrapper.likeRight("name","黄").or().ge("age",40).orderByDesc("age").orderByAsc("id");

		// 4.创建日期为2021年3月22日，并且直属上级的名字为李姓
		// date_format(create_time,'%Y-%m-%d') = '2021-03-22' AND manager_id IN (SELECT id FROM user WHERE name like '李%')
		wrapper.apply("date_format(create_time, '%Y-%m-%d') = {0}", "2021-03-22")  // 建议采用{index}这种方式动态传参, 可防止SQL注入
				.inSql("manager_id", "SELECT id FROM user WHERE name like '李%'");
		// 上面的apply, 也可以直接使用下面这种方式做字符串拼接，但当这个日期是一个外部参数时，这种方式有SQL注入的风险
		wrapper.apply("date_format(create_time, '%Y-%m-%d') = '2021-03-22'");

		// 5. 名字为王姓，并且（年龄小于40，或者邮箱不为空）
		// name like '王%' AND (age < 40 OR email is not null)
		wrapper.likeRight("name", "王").and(q -> q.lt("age", 40).or().isNotNull("email"));

		// 6. 名字为王姓，或者（年龄小于40并且年龄大于20并且邮箱不为空）
		// name like '王%' OR (age < 40 AND age > 20 AND email is not null)
		wrapper.likeRight("name", "王").or(
				q -> q.lt("age",40)
						.gt("age",20)
						.isNotNull("email")
		);

		// 7. (年龄小于40或者邮箱不为空) 并且名字为王姓
		// (age < 40 OR email is not null) AND name like '王%'
		wrapper.nested(q -> q.lt("age", 40).or().isNotNull("email"))
				.likeRight("name", "王");

		// 8. 年龄为30，31，34，35
		// age IN (30,31,34,35)
		wrapper.in("age", Arrays.asList(30,31,34,35));
		// 或
		wrapper.inSql("age","30,31,34,35");

		// 9. 年龄为30，31，34，35, 返回满足条件的第一条记录
		// age IN (30,31,34,35) LIMIT 1
		wrapper.in("age", Arrays.asList(30,31,34,35)).last("LIMIT 1");

		// 10. 只选出id, name 列 (QueryWrapper 特有)
		// SELECT id, name FROM user;
		wrapper.select("id", "name");

		// 11. 选出id, name, age, email, 等同于排除 manager_id 和 create_time
		// 当列特别多, 而只需要排除个别列时, 采用上面的方式可能需要写很多个列, 可以采用重载的select方法，指定需要排除的列
		wrapper.select(User.class, info -> {
			String columnName = info.getColumn();
			return !"create_time".equals(columnName) && !"manager_id".equals(columnName);
		});






	}
	//Condition   前置条件
	@Test
	public void testCondition(){
		String name = "黄"; // 假设name变量是一个外部传入的参数
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.like(StringUtils.hasText(name), "name", name);   //字符串 不是 null ，并且不为空，而且不能是空白字符 才返回true
		User one = userService.getOne(wrapper);
		System.out.println(one);
		// 仅当 StringUtils.hasText(name) 为 true 时, 会拼接这个like语句到WHERE中
		// 其实就是对下面代码的简化
		if (StringUtils.hasText(name)) {
			wrapper.like("name", name);
		}
	}

	//实体对象作为条件
	/**
	 * 会以实体对象中的非空属性，构建WHERE条件（默认构建等值匹配的WHERE条件，这个行为可以通过实体类里各个字段上的@TableField注解中的condition属性进行改变）
	 *
	 * 若希望针对某些属性，改变等值匹配的行为，则可以在实体类中用@TableField注解进行配置，示例如下  @TableField(condition = SqlCondition.LIKE)   // 配置该字段使用like进行拼接
	 */
	@Test
	public void testObjectConditions1(){
		//条件对象
		User user = new User();
//		user.setName("黄主管");
		user.setAge(40);
		//查询
		QueryWrapper<User> wrapper = new QueryWrapper<>(user);
		List<User> users = userService.list(wrapper);
		users.forEach(System.out::println);
	}
	@Test
	public void testObjectConditions2(){
		//条件对象
		User user = new User();
		user.setName("主");
//		user.setAge(40);
		//查询
		QueryWrapper<User> wrapper = new QueryWrapper<>(user);
		List<User> users = userService.list(wrapper);
		users.forEach(System.out::println);
	}
}
