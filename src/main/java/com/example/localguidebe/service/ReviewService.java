package com.example.localguidebe.service;

import com.example.localguidebe.dto.TourDTO;
import com.example.localguidebe.dto.requestdto.ReviewRequestDTO;
import com.example.localguidebe.dto.responsedto.ReviewResponseDTO;
import com.example.localguidebe.entity.Review;
import com.example.localguidebe.entity.User;
import java.util.List;

public interface ReviewService {
  Review addReviewForGuide(ReviewRequestDTO reviewRequestDTO, Long guideId, User traveler);

  List<ReviewResponseDTO> addReviewForTour(ReviewRequestDTO reviewRequestDTO, Long tourId, String email);

  List<ReviewResponseDTO> getReviewForTour(Long tourId);

  List<Review> getReviewsOfGuide(Long guideId);

  List<ReviewResponseDTO> editReviewForTour(Long reviewId, ReviewRequestDTO reviewRequestDTO);

  boolean checkReviewByTraveler(Long reviewId, String email);

  List<ReviewResponseDTO> deleteReviewForTour(Long reviewId);

  boolean updateReviewForGuide(ReviewRequestDTO reviewRequestDTO, User traveler, Long reviewId);

  boolean deleteReviewForGuide(User traveler, Long reviewId);
}
