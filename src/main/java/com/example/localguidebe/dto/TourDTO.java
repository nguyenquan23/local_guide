package com.example.localguidebe.dto;

import com.example.localguidebe.entity.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TourDTO {

  private Long id;

  private String name;

  private String description;

  private String transportation;

  private String includeService;

  private Integer duration;

  private String unit;

  private String estimatedLocalCashNeeded;

  private Double pricePerTraveler;

  private Integer limitTraveler;

  private Double extraPrice;

  private Double overallRating;

  private String itinerary;
  private boolean isDeleted;
  private String address;

  private UserDTO guide;

  //    //TODO meeting_point_id associate to location
  //
  //    private Location province;

  //    private List<TourStartTime> tourStartTimes = new ArrayList<>();

  private Set<CategoryDTO> categories = new HashSet<>();

  private List<ReviewDTO> reviewDTOS = new ArrayList<>();
  //
  //    private List<Booking> bookings = new ArrayList<>();
  //
  private List<Image> images = new ArrayList<>();
  //
  private Set<LocationDTO> locations;
  //
  //    private Location location;
}
