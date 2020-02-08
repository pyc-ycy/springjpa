//IntelliJ IDEA
//springjpa
//PersonRepository
//2020/2/8
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.springjpa.dao;

import com.pyc.springjpa.domain.Person;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person,Long> {
    List<Person> findByAddress(String address);

    Person findByNameAndAddress(String name, String address);

    @Query("select p from Person p where p.name= :name and p.address= :address")
    Person withNameAndAddressQuery(@Param("name")String name,@Param("address")String address);
    Person withNameAndAddressNamedQuery(String name, String address);

}
