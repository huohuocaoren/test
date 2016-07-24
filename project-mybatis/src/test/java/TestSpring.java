import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.BaseDao;
import jedis.dao.RedisDao;
import po.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring-dao.xml")
public class TestSpring {

	@Autowired
	private BaseDao basedao;
	
	private RedisDao redisDao = new RedisDao("192.168.1.103", 6379);
	
	@Test
	public void testPo(){
		List<Person> all = basedao.getAll();
		for (Person person : all) {
			System.out.println(person.getName());
		}
		
	}
	
	@Test
	public void testPo2(){
		Person p = basedao.getPersonById("001");
		String result = redisDao.setPerson(p);
		System.out.println(result);
	}
	
	@Test
	public void getRedisPerson(){
		Person p = redisDao.getPersonById("001");
		System.out.println(p.getName());
		
	}
	
	
	@Test
	public void testAdd() throws Exception{
		Person p = new Person();
		p.setId("005");
		p.setName("李四");
		p.setAge(22);
		SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    String time="1970-01-06 11:45:55";  
	    Date date = format.parse(time);  
		p.setBirthday(date);
		p.setAddress("天津");
		
		basedao.addOnePerson(p);
	}
	
}
