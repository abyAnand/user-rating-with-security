package io.micro.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rating {

    private String ratingId;
    private String userId;

    private String hotelId;

    private int rating;

    private String feedback;

    @Transient
    private Hotel hotel;


}
