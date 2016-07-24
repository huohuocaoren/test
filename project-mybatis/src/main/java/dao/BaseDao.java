package dao;

import java.util.List;

import po.Person;


public interface BaseDao {
	public List<Person> getAll();
	
	public Person getPersonById(String id);
	
	public int addOnePerson(Person person);
}
