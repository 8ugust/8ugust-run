package com.august.run.Repository;


import java.util.Optional;
import com.august.run.Model.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    public Optional<User> findByUserId(String userId);

}
