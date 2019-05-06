package com.lpy.jpa.spring;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lpy.jpa.entities.Person;
import com.lpy.jpa.service.PersonService;

/**
 * @author lipengyu
 * @date 2019/5/6 10:21
 */
public class JPATest {
    private ApplicationContext ctx = null;
    private PersonService personService = null;

    {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        personService = ctx.getBean(PersonService.class);
    }

    @Test
    public void testPersonService(){
        Person p1 = new Person();
        p1.setAge(11);
        p1.setEmail("aa@163.com");
        p1.setLastName("AA");

        Person p2 = new Person();
        p2.setAge(12);
        p2.setEmail("bb@163.com");
        p2.setLastName("BB");

        System.out.println(personService.getClass().getName());
        personService.savePersons(p1, p2);
    }

    @Test
    public void testDataSource() throws SQLException {
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource.getConnection());
    }
}
