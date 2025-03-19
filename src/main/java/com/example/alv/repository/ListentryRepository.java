package com.example.alv.repository;

import com.example.alv.model.Listentry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListentryRepository extends JpaRepository<Listentry, Long> {

}
