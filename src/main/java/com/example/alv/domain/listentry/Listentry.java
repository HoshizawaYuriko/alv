package com.example.alv.domain.listentry;

import com.example.alv.domain.anime.Anime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "listentry")
public class Listentry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "anime_id")
    private Anime anime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column
    private int progress;

    @Column(nullable = true)
    private Timestamp startdate;

    @Column(nullable = true)
    private Timestamp enddate;

    @Embedded
    private Rating rating;

    // Domain-specific Logic
    public void updateProgress(int newProgress) {
        int max = anime.getMaxEpisodes();

        if (newProgress < 0 || newProgress > max) {
            throw new IllegalArgumentException("Progress must be between 0 and " + max);
        }

        this.progress = newProgress;

        autoAdjustStatusBasedOnProgress();
        validateStatusConsistency();
    }

    public void incrementProgress() {
        updateProgress(this.progress + 1);
    }

    // Check if new status can be set and update it accordingly
    public void updateStatus(Status newStatus) {
        int maxEpisodes = anime.getMaxEpisodes();

        if (newStatus == Status.COMPLETED && progress < maxEpisodes) {
            throw new IllegalArgumentException("Cannot mark as COMPLETED if not all episodes watched.");
        }

        if (progress == maxEpisodes && newStatus != Status.COMPLETED) {
            throw new IllegalArgumentException("Progress is max, status must be COMPLETED.");
        }

        if (progress == 0 && newStatus != Status.PLANNED && newStatus != Status.WATCHING) {
            throw new IllegalArgumentException("Progress is 0, cannot set status to " + newStatus);
        }

        this.status = newStatus;
    }

    public void validateInitialState() {
        // Check for valid start and end dates
        if (startdate != null && enddate != null && enddate.before(startdate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }

        validateStatusConsistency();
    }

    // Check if status is valid the given progress
    private void validateStatusConsistency() {
        int max = anime.getMaxEpisodes();

        if (status == Status.PLANNED && progress > 0) {
            throw new IllegalStateException("PLANNED status cannot have progress.");
        }

        if (status == Status.COMPLETED && progress < max) {
            throw new IllegalStateException("COMPLETED requires full progress.");
        }

        if ((status == Status.DROPPED || status == Status.ON_HOLD) && progress == max) {
            throw new IllegalStateException("Cannot be " + status + " if all episodes are watched.");
        }
    }

    // Automatically updates the status when progress is added
    private void autoAdjustStatusBasedOnProgress() {
        int max = anime.getMaxEpisodes();

        // When all episodes have been watched, automatically set status to COMPLETED
        if (this.progress == max) {
            this.status = Status.COMPLETED;
        // When progress is added, advance status from PLANNED/ON_HOLD to WATCHING
        } else if (this.status == Status.PLANNED || this.status == Status.ON_HOLD) {
            this.status = Status.WATCHING;
        }
    }
}
