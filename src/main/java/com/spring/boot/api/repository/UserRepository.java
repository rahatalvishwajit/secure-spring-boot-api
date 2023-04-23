package com.spring.boot.api.repository;

import com.spring.boot.api.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface which extends CrudRepository providing support
 * for basic CRUD operations to be performed on user data
 *
 * @author Vishwajit
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}