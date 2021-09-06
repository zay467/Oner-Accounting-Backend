package com.thamardaw.oner.repository;

import com.thamardaw.oner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Long, User> {
}
