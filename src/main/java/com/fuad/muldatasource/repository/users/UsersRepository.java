package com.fuad.muldatasource.repository.users;

import com.fuad.muldatasource.model.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findByEmail(String email);
}
