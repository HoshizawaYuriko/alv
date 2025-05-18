package com.example.alv.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

import com.example.alv.domain.listentry.Status;

@Getter
@Setter
public class ListentryDTO {
    private Long animeId;
    private Status status;
    private int progress = 0;
    private Timestamp startdate;
    private Timestamp enddate;
    private Integer rating;
}
