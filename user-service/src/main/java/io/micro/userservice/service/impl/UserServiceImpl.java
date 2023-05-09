package io.micro.userservice.service.impl;

import io.micro.userservice.entity.Hotel;
import io.micro.userservice.entity.Rating;
import io.micro.userservice.entity.User;
import io.micro.userservice.exception.ResourceNotFoundException;
import io.micro.userservice.external.service.HotelService;
import io.micro.userservice.repository.UserRepository;
import io.micro.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private HotelService hotelService;


    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        //get user from database with the help of user repo
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with given Id is not found on server: "+userId));
        //fetch rating of the given user from RATING-SERVICE
        //http://localhost:8083/ratings/users/042de237-bfa2-46dc-9950-dea9e24a4d53
        Rating[] ratingsOfUser =
                restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{} ",ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList
                = ratings.stream()
                 .map(rating -> {
           //api call to get the hotel
            //http://localhost:8082/hotels/654baa53-6143-4091-bc76-2f998506c6ad
//            ResponseEntity<Hotel> forEntity =
//                    restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
            //set the hotel to rating
            //Hotel hotel = forEntity.getBody();
            Hotel hotel = hotelService.getHotel(rating.getHotelId());

//            logger.info("response status code:{}", forEntity.getStatusCode());
            rating.setHotel(hotel);

            //return the rating
            return rating;
        }).toList();

        user.setRatings(ratingList);

        return user;
    }
}
