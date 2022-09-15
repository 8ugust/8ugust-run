package com.august.run.Repository;

import com.august.run.Model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findById(String id);

}
