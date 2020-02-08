//IntelliJ IDEA
//springjpa
//Person
//2020/2/8
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.springjpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity //指明这是一个和数据库表映射的实体类
@NamedQuery(name = "Person.withNameAndAddressNamedQuery",
        query = "select p from Person p where p.name=?1 and p.address=?2")
public class Person {
    @Id     // 指明这个属性映射为数据库的主键
    @GeneratedValue     //指明主键的生成方式为自增
    private Long id;
    private String name;
    private Integer age;
    private String address;

    public Person() {
        super();
    }

    public Person(Long id, String name, Integer age, String address){
        super();
        this.address=address;
        this.name=name;
        this.age=age;
        this.id=id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }
}
