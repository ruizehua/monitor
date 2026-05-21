package com.nari.monitor.repository;

import com.nari.monitor.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByClientName(String clientName);

    Optional<Client> findByClientNameAndIsDeleted(String clientName, Integer isDeleted);

    List<Client> findByIsDeleted(Integer isDeleted);

    Optional<Client> findByIdAndIsDeleted(Long id, Integer isDeleted);

    boolean existsByClientName(String clientName);
}
