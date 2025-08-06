package com.product.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.product.entity.UserLog;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {
}