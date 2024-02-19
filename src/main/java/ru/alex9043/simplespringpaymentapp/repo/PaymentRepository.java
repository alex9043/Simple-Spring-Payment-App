package ru.alex9043.simplespringpaymentapp.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex9043.simplespringpaymentapp.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
