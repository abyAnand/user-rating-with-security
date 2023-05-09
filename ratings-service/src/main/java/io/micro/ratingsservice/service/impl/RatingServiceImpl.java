package io.micro.ratingsservice.service.impl;

import io.micro.ratingsservice.entity.Rating;
import io.micro.ratingsservice.repository.RatingRepository;
import io.micro.ratingsservice.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository repository;
    @Override
    public Rating create(Rating rating) {
        String randomUserId = UUID.randomUUID().toString();
        rating.setRatingId(randomUserId);
        return repository.save(rating);
    }

    @Override
    public List<Rating> getRatings() {
        return repository.findAll();
    }

    @Override
    public List<Rating> getRatingByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<Rating> getRatingByHotelId(String hotelId) {
        return repository.findByHotelId(hotelId);
    }
}
