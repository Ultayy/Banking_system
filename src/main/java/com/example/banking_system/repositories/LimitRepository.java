package com.example.banking_system.repositories;

import com.example.banking_system.entities.Limit;
import com.example.banking_system.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface LimitRepository extends JpaRepository<Limit, Long> {
    Limit save(Limit limit);

    Limit findByType(Type type);

    List<Limit> findByTypeOrderBySetDateDesc(Type type);

}
