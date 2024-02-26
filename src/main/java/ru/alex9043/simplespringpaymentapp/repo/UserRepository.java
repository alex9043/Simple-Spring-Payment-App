package ru.alex9043.simplespringpaymentapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex9043.simplespringpaymentapp.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
