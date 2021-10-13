package com.lcl.cloud.alibaba.provider01.repository;

import com.lcl.cloud.alibaba.provider01.bean.Depart;
import org.springframework.data.jpa.repository.JpaRepository;
//持久层：在中国Mybatis非常流行灵活，但是Hibernate纯ORM
public interface DepartRepository extends JpaRepository<Depart, Integer> {
}
