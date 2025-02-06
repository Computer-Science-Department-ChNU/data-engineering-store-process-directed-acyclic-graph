package ua.edu.chnu.kkn.dag.familytree.sql

import org.springframework.data.jpa.repository.JpaRepository

interface ConnectionSqlRepository : JpaRepository<ConnectionEntity, Long>