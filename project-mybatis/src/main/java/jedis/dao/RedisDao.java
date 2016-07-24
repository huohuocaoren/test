package jedis.dao;

import org.springframework.stereotype.Component;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import po.Person;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
	
	private JedisPool jPool;
	
	private RuntimeSchema<Person> schema = RuntimeSchema.createFrom(Person.class);
	
	
	public RedisDao(String ip,int port){
		jPool= new JedisPool(ip,port);
	}
	 
	public Person getPersonById(String id){
		Jedis jedis = jPool.getResource();
		jedis.auth("root");
		String key = "person:"+id;
		
		byte[] bytes = jedis.get(key.getBytes());
		
		if(null != bytes ){
			
			Person message = schema.newMessage();
			ProtostuffIOUtil.mergeFrom(bytes, message , schema);
			
			return message;
		}else{
			return null;
		}
	}
	
	public String setPerson(Person p){
		Jedis jedis = jPool.getResource();
		jedis.auth("root");
		String key = "person:"+p.getId();
		byte [] bytes = ProtostuffIOUtil.toByteArray(p, schema, 
				LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
		
		int timeout = 60*5;
		String result = jedis.setex(key.getBytes(), timeout, bytes);
		return result;
	}
	
}
