package com.august.run.Repository;

import java.util.List;
import com.august.run.Model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    List<User> findAll();
    List<User> findByLoginId(String loginId);

}
