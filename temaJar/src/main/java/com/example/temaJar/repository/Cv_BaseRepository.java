package com.example.temaJar.repository;

import com.example.temaJar.models.Cv_Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Cv_BaseRepository extends JpaRepository<Cv_Base, Long> {
}
