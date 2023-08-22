package com.merveadler.repository;

import com.merveadler.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth,Long> {

    Optional<Auth> findOptionalByUsername(String username);

    Optional<Auth> findOptionalByUsernameAndPassword(String username,String password);

}
