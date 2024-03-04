package com.example.localguidebe.controller;

import com.example.localguidebe.converter.ReviewToReviewDtoConverter;
import com.example.localguidebe.dto.requestdto.ReviewRequestDTO;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.security.service.CustomUserDetails;
import com.example.localguidebe.service.ReviewService;
import com.example.localguidebe.service.TourService;
import com.example.localguidebe.service.UserService;
import com.example.localguidebe.system.Result;
import com.example.localguidebe.utils.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
  private final ReviewService reviewService;
  private final UserService userService;
  private final ReviewToReviewDtoConverter reviewToReviewDtoConverter;
  private final TourService tourService;

  public ReviewController(
      ReviewService reviewService,
      UserService userService,
      ReviewToReviewDtoConverter reviewToReviewDtoConverter,
      TourService tourService) {
    this.tourService = tourService;
    this.reviewService = reviewService;
    this.userService = userService;
    this.reviewToReviewDtoConverter = reviewToReviewDtoConverter;
  }

  @PostMapping("guide-reviews/{guideId}")
  public ResponseEntity<Result> addReviewForGuide(
      Authentication authentication,
      @RequestBody ReviewRequestDTO reviewRequestDTO,
      @PathVariable("guideId") Long guideId) {
    return AuthUtils.checkAuthentication(
        authentication,
        () -> {
          String travelerEmail = ((CustomUserDetails) authentication.getPrincipal()).getEmail();
          User traveler = userService.findUserByEmail(travelerEmail);
          if (!userService.isTravelerCanAddReviewForGuide(traveler, guideId)) {
            return ResponseEntity.status(HttpStatus.OK)
                .body(
                    new Result(
                        false, HttpStatus.OK.value(), "You can't not add review for this guide"));
          }
          return ResponseEntity.status(HttpStatus.OK)
              .body(
                  new Result(
                      true,
                      HttpStatus.OK.value(),
                      "Thank you for giving feedback.",
                      reviewToReviewDtoConverter.convert(
                          reviewService.addReviewForGuide(reviewRequestDTO, guideId, traveler))));
        });
  }

  @PostMapping("tour-reviews/{tourId}")
  public ResponseEntity<Result> addReviewForTour(
      Authentication authentication,
      @RequestBody ReviewRequestDTO reviewRequestDTO,
      @PathVariable("tourId") Long tourId) {
    return AuthUtils.checkAuthentication(
        authentication,
        () -> {
          if (!tourService.checkBookingByTraveler(
              tourId, ((CustomUserDetails) authentication.getPrincipal()).getEmail())) {
            return new ResponseEntity<>(
                new Result(true, HttpStatus.CONFLICT.value(), "You haven't booked a tour yet so you can't review it", null),
                HttpStatus.CONFLICT);
          } else {
            try {
              return new ResponseEntity<>(
                  new Result(
                      true,
                      HttpStatus.OK.value(),
                      "Added busy day list successfully",
                      reviewService.addReviewForTour(
                          reviewRequestDTO,
                          tourId,
                          ((CustomUserDetails) authentication.getPrincipal()).getEmail())),
                  HttpStatus.OK);
            } catch (Exception e) {
              return new ResponseEntity<>(
                  new Result(
                      false, HttpStatus.CONFLICT.value(), "Added review for failed tour", null),
                  HttpStatus.CONFLICT);
            }
          }
        });
  }
}