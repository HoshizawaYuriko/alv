package com.example.alv.domain.listentry;

import com.example.alv.domain.anime.Anime;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ListentryRepository extends JpaRepository<Listentry, Long> {
    boolean existsByAnime(Anime anime);

    List<Listentry> findByStatus(Status status);

    @Query("SELECT le FROM Listentry le JOIN le.anime a JOIN a.genres g WHERE g.name = :genreName")
    List<Listentry> findByGenreName(@Param("genreName") String genreName);

    @Query("SELECT le FROM Listentry le WHERE le.rating.value > :minRating")
    List<Listentry> findByRatingGreaterThan(@Param("minRating") int minRating);
}
