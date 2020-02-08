//IntelliJ IDEA
//springjpa
//CustomRepository
//2020/2/8
// Author:御承扬
//E-mail:2923616405@qq.com


package com.pyc.springjpa.support;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface CustomRepository<T, ID extends Serializable>extends JpaRepository<T, ID>,
        JpaSpecificationExecutor<T> {
    Page<T> findByAuto(T example, Pageable pageable);
}
