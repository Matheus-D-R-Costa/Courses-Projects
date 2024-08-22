package com.simple.bank_system.repositories.user;

import com.simple.bank_system.models.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    boolean existsByUsername(String username);

}
