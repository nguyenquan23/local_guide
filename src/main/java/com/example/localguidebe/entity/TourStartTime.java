package com.example.localguidebe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tour_start_time")
public class TourStartTime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  @Column(name = "start_time")
  private Time startTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tour_id")
  private Tour tour;
}
