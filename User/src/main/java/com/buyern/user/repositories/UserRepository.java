package com.buyern.user.repositories;

import com.buyern.user.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from users u where u.id = ?1")
    Optional<User> findById(Long id);

    Optional<User> findByUid(String uid);

    @Query("select u from users u where u.contact.email = ?1")
    Optional<User> findByEmail(String email);

    @Query("select u from users u where u.contact.phone = ?1")
    Optional<User> findByPhone(String phone);

    @Query("select u from users u where u.location.state = ?1")
    List<User> findByState(Long state, Pageable pageable);

    @Query("select u from users u where u.location.country = ?1")
    List<User> findByCountry(Long country, Pageable pageable);
}