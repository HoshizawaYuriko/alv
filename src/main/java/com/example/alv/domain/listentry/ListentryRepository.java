package com.example.alv.domain.listentry;

import com.example.alv.domain.anime.Anime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListentryRepository extends JpaRepository<Listentry, Long> {
    boolean existsByAnime(Anime anime);
}
