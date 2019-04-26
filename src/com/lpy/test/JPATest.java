package com.lpy.test;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lpy.model.Customer;
import com.lpy.model.Order;

/**
 * @author lipengyu
 * @date 2019/4/26 14:58
 */
public class JPATest {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;


    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa-1");
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
    }

    @After
    public void destory() {
        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    /**
     * 若是双向 1-n 的关联关系, 执行保存时
     * 若先保存 n 的一端, 再保存 1 的一端, 默认情况下, 会多出 n 条 UPDATE 语句.
     * 若先保存 1 的一端, 则会多出 n 条 UPDATE 语句
     * 在进行双向 1-n 关联关系时, 建议使用 n 的一方来维护关联关系, 而 1 的一方不维护关联系, 这样会有效的减少 SQL 语句.
     * 注意: 若在 1 的一端的 @OneToMany 中使用 mappedBy 属性, 则 @OneToMany 端就不能再使用 @JoinColumn 属性了.
     *
     * 单向 1-n 关联关系执行保存时, 一定会多出 UPDATE 语句.
     * 因为 n 的一端在插入时不会同时插入外键列.
     */
    @Test
    public void testOneToManyPersist() {
        Customer customer = new Customer();
        customer.setAge(18);
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());
        customer.setEmail("mm@163.com");
        customer.setLastName("MM");

        Order order1 = new Order();
        order1.setOrderName("O-MM-1");

        Order order2 = new Order();
        order2.setOrderName("O-MM-2");

        //建立关联关系
        customer.getOrders().add(order1);
        customer.getOrders().add(order2);

        order1.setCustomer(customer);
        order2.setCustomer(customer);

        //执行保存操作
        entityManager.persist(customer);

        entityManager.persist(order1);
        entityManager.persist(order2);
    }

    /**
     * EAGER 直接left join查询出来修改
     * LAZY 分两次查询再修改
     */
    @Test
    public void testManyToOneUpdate(){
        Order order = entityManager.find(Order.class, 2);
        order.getCustomer().setLastName("FFF");
    }

    /**
     * 不能直接删除 1 的一端, 因为有外键约束.
     *
     * 还有其他的Order 在 关联 Customer
     */
    @Test
    public void testManyToOneRemove(){
//		Order order = entityManager.find(Order.class, 1);
//		entityManager.remove(order);

        Customer customer = entityManager.find(Customer.class, 7);
        entityManager.remove(customer);
    }

    /** 默认情况下, 使用左外连接的方式来获取 n 的一端的对象和其关联的 1 的一端的对象.
     *  可使用 @ManyToOne 的 fetch 属性来修改默认的关联属性的加载策略
     *
     *  多对一 查询多的一方时 直接查询出一的一方
     *  一对多 查询一的一方时 直接查询出多的一方
     *  多对多 互相查
     */
     @Test
    public void testManyToOneFind(){
        Order order = entityManager.find(Order.class, 1);
        System.out.println(order.getOrderName());

        System.out.println(order.getCustomer().getLastName());
    }

    /**
     * 保存多对一时, 建议先保存 1 的一端, 后保存 n 的一端, 这样不会多出额外的 UPDATE 语句.
     * 外键还没生成，会导致保存后还要更新外键
     * 配置了@JoinColumn(name = "customer_id") 会自动生成外键列
     */
    @Test
    public void testManyToOnePersist(){
        Customer customer = new Customer();
        customer.setAge(18);
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());
        customer.setEmail("gg@163.com");
        customer.setLastName("GG");

        Order order1 = new Order();
        order1.setOrderName("G-GG-1");

        Order order2 = new Order();
        order2.setOrderName("G-GG-2");

        //设置关联关系
        order1.setCustomer(customer);
        order2.setCustomer(customer);

        //执行保存操作
        entityManager.persist(customer);
        entityManager.persist(order1);
        entityManager.persist(order2);
    }

    /**
     * 同 hibernate 中 Session 的 flush 方法.
     */
    @Test
    public void testFlush() {
        Customer customer = entityManager.find(Customer.class, 2);
        System.out.println(customer);

        customer.setLastName("AA");

        entityManager.flush();
    }

    //若传入的是一个游离对象, 即传入的对象有 OID.
    //1. 若在 EntityManager 缓存中有对应的对象
    //2. JPA 会把游离对象的属性复制到查询到EntityManager 缓存中的对象中.
    //3. EntityManager 缓存中的对象执行 UPDATE.
    @Test
    public void testMerge4() {
        Customer customer = new Customer();
        customer.setAge(18);
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());
        customer.setEmail("dd@163.com");
        customer.setLastName("DD");

        customer.setId(4);
        Customer customer2 = entityManager.find(Customer.class, 4);

        entityManager.merge(customer);

        System.out.println(customer == customer2); //false
    }


    //若传入的是一个游离对象, 即传入的对象有 OID.
    //1. 若在 EntityManager 缓存中没有该对象
    //2. 若在数据库中也有对应的记录
    //3. JPA 会查询对应的记录, 然后返回该记录对一个的对象, 再然后会把游离对象的属性复制到查询到的对象中.
    //4. 对查询到的对象执行 update 操作.
    @Test
    public void testMerge3() {
        Customer customer = new Customer();
        customer.setAge(18);
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());
        customer.setEmail("ee@163.com");
        customer.setLastName("EE");

        customer.setId(4);

        Customer customer2 = entityManager.merge(customer);

        System.out.println(customer == customer2); //false
    }

    //若传入的是一个游离对象, 即传入的对象有 OID.
    //1. 若在 EntityManager 缓存中没有该对象
    //2. 若在数据库中也没有对应的记录
    //3. JPA 会创建一个新的对象, 然后把当前游离对象的属性复制到新创建的对象中
    //4. 对新创建的对象执行 insert 操作.
    @Test
    public void testMerge2() {
        Customer customer = new Customer();
        customer.setAge(18);
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());
        customer.setEmail("dd@163.com");
        customer.setLastName("DD");

        customer.setId(100);

        Customer customer2 = entityManager.merge(customer);

        System.out.println("customer#id:" + customer.getId());
        System.out.println("customer2#id:" + customer2.getId());
    }

    /**
     * 总的来说: 类似于 hibernate Session 的 saveOrUpdate 方法.
     */
    //1. 若传入的是一个临时对象
    //会创建一个新的对象, 把临时对象的属性复制到新的对象中, 然后对新的对象执行持久化操作. 所以
    //新的对象中有 id, 但以前的临时对象中没有 id.
    @Test
    public void merge() {
        Customer customer = new Customer();
        customer.setAge(18);
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());
        customer.setEmail("cc@163.com");
        customer.setLastName("CC");

        Customer customer2 = entityManager.merge(customer);

        System.out.println("customer#id:" + customer.getId());
        System.out.println("customer2#id:" + customer2.getId());
    }


    //类似于 hibernate 中 Session 的 delete 方法. 把对象对应的记录从数据库中移除
    //但注意: 该方法只能移除 持久化 对象. 而 hibernate 的 delete 方法实际上还可以移除 游离对象.
    @Test
    public void testRemove() {
        Customer customer = entityManager.find(Customer.class, 1);
        System.out.println(customer);
        entityManager.remove(customer);
    }

    //类似于 hibernate 的 save 方法. 使对象由临时状态变为持久化状态.
    //和 hibernate 的 save 方法的不同之处: 若对象有 id, 则不能执行 insert 操作, 而会抛出异常.
    @Test
    public void testPersist() {
        Customer customer = new Customer();
        customer.setAge(10);
        customer.setBirth(new Date());
        customer.setCreatedTime(new Date());
        customer.setEmail("333@qq.com");
        customer.setLastName("pp");

        Customer customer1 = new Customer();
        customer1.setAge(11);
        customer1.setBirth(new Date());
        customer1.setCreatedTime(new Date());
        customer1.setEmail("444@qq.com");
        customer1.setLastName("qq");

        entityManager.persist(customer);
        entityManager.persist(customer1);
        System.out.println(customer.getId());
        System.out.println(customer1.getId());
    }

    //类似于 hibernate 中 Session 的 load 方法
    @Test
    public void testGetReference() {
        Customer customer = entityManager.getReference(Customer.class, 1);
        System.out.println(customer.getClass().getName());
    }

    //类似于 hibernate 中 Session 的 get 方法.
    @Test
    public void testFind() {
        Customer customer = entityManager.find(Customer.class, 1);
        System.out.println(customer);
    }





































}
