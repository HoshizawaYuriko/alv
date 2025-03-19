package com.example.alv.dto;

import com.example.alv.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

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
