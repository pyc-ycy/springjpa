//IntelliJ IDEA
//springjpa
//PersonRepository
//2020/2/8
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.springjpa.dao;

import com.pyc.springjpa.domain.Person;
import com.pyc.springjpa.support.CustomRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends CustomRepository<Person,Long> {
    // 根据地址查询，返回值为列表
    List<Person> findByAddress(String address);
    // 根据姓名和地址查询，返回值为单个对象
    Person findByNameAndAddress(String name, String address);
    // 使用 @Query 查询，参数按照名称绑定
    @Query("select p from Person p where p.name= :name and p.address= :address")
    Person withNameAndAddressQuery(@Param("name")String name,@Param("address")String address);
    //使用 @NamedQuery 查询，在实体类中已注解
    Person withNameAndAddressNamedQuery(String name, String address);

}
