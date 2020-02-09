//IntelliJ IDEA
//springjpa
//DataController
//2020/2/8
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.springjpa.web;


import com.pyc.springjpa.dao.PersonRepository;
import com.pyc.springjpa.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataController {
    // Spring Data JPA 已自动注册bean，因此可以自动注入
    @Autowired
    PersonRepository personRepository;
    /*
     *  保存
     *  save 支持批量保存：<S extends T> Iterable<S> save(Iterable<S> entities);
     *  删除
     *  支持使用 id 删除对象、批量删除以及删除全部
     *  void delete(ID id)
     *  void delete(T entity)
     *  void delete(Iterable<? extends T>entities);
     *  void deleteAll();
     */
    @RequestMapping("/save")
    public Person save(String name,String address,Integer age){
        Person p;
        p = personRepository.save(new Person(null, name,age,address));
        return p;
    }
    /*
     * 测试 findByAddress
     */
    @RequestMapping("/q1")
    public List<Person> q1(String address){
        List<Person> people;
        people = personRepository.findByAddress(address);
        return people;
    }
    // 测试 findByNameAndAddress
    @RequestMapping("/q2")
    public Person q2(String name, String address){
        Person person;
        person = personRepository.findByNameAndAddress(name,address);
        return person;
    }
    //测试 withNameAndAddressQuery
    @RequestMapping("/q3")
    public Person q3(String name, String address){
        Person p;
        p = personRepository.withNameAndAddressQuery(name, address);
        return p;
    }
    //测试 withNameAndAddressNamedQuery
    @RequestMapping("/q4")
    public Person q4(String name, String address){
        Person p;
        p = personRepository.withNameAndAddressNamedQuery(name, address);
        return p;
    }
    //排序
    @RequestMapping("/sort")
    public List<Person> sort(){
        List<Person> people;
        people = personRepository.findAll(new Sort(Sort.Direction.ASC, "age"));
        return people;
    }
    // 测试分页
    @RequestMapping("/page")
    public Page<Person> page(){
        Page<Person> pagePeople;
        pagePeople = personRepository.findAll(new PageRequest(1,2));
        return pagePeople;
    }
    @RequestMapping("/auto")
    public Page<Person> auto(Person person){
        Page<Person> pagePeople;
        pagePeople = personRepository.findByAuto(person,new PageRequest(0,10));
        return pagePeople;
    }
}
