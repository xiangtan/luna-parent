package com.luna.serializer.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.luna.common.serializer.Hessian2Serializer;
import com.luna.common.serializer.KryoSerializer;

public class SerializerTest
{
	private static Random random = new Random();

	public static void main(String[] args)
	{
		User user = new User();
		user.setUsername("yicai.liu");
		user.setPwd("888");
		user.setAge(28);
		Address address = new Address();
		address.setProvince("湖南");
		address.setCountry("中国");
		address.setCity("湘潭");
		user.setAddress(address);
		List<Book> books = new ArrayList<>();
		for (int i = 0; i < 10; i++)
		{
			Book book = new Book();
			book.setName("book " + i);
			book.setPrice(random.nextInt(i + 1));
			book.setDesc("book desc:" + random.nextInt(i + 1));
			books.add(book);
		}
		user.setBooks(books);
		System.out.println("------------------------------------serialize------------------------------------");
		byte[] jdkBytes = serialize(new JdkSerializationRedisSerializer(), user);
		byte[] hessianBytes = serialize(new Hessian2Serializer(), user);
		//byte[] kyroBytes = serialize(new KryoSerializer(), user);
		System.out.println("------------------------------------deserialize------------------------------------");
		User jdkUser = (User) deserialize(new JdkSerializationRedisSerializer(), jdkBytes);
		User hessianUser = (User) deserialize(new Hessian2Serializer(), hessianBytes);
		//User kyroUser = (User) deserialize(new KryoSerializer(), kyroBytes);
		System.out.println("------------------------------------users------------------------------------");
		//System.out.println(jdkUser);
		//System.out.println(hessianUser);
		//System.out.println(kyroUser);
	}

	private static byte[] serialize(RedisSerializer<Object> serializer, User user)
	{
		long start = System.currentTimeMillis();
		byte[] bytes = serializer.serialize(user);
		System.out.println(serializer.getClass().getName() + " serialize costs:" + (System.currentTimeMillis() - start)
				+ "ms, per object length:" + bytes.length + " bytes");
		return bytes;
	}

	private static Object deserialize(RedisSerializer<Object> serializer, byte[] bytes)
	{
		long start = System.currentTimeMillis();
		Object obj = serializer.deserialize(bytes);
		System.out.println(
				serializer.getClass().getName() + " deserialize costs:" + (System.currentTimeMillis() - start) + "ms");
		return obj;
	}
}

class User implements Serializable
{

	private static final long	serialVersionUID	= 1L;
	private String				username;
	private String				pwd;
	private int					age;
	private Address				address;
	private List<Book>			books;

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public Address getAddress()
	{
		return address;
	}

	public void setAddress(Address address)
	{
		this.address = address;
	}

	@Override
	public String toString()
	{
		return "User [username=" + username + ", pwd=" + pwd + ", age=" + age + ", address=" + address + ", books="
				+ books + "]";
	}

	public List<Book> getBooks()
	{
		return books;
	}

	public void setBooks(List<Book> books)
	{
		this.books = books;
	}

}

class Address implements Serializable
{

	private static final long	serialVersionUID	= 1L;
	private String				country;
	private String				province;
	private String				city;

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getProvince()
	{
		return province;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	@Override
	public String toString()
	{
		return "Address [country=" + country + ", province=" + province + ", city=" + city + "]";
	}

}

class Book implements Serializable
{
	private static final long	serialVersionUID	= 1L;
	private String				name;
	private double				price;
	private String				desc;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	@Override
	public String toString()
	{
		return "Book [name=" + name + ", price=" + price + ", desc=" + desc + "]";
	}

}