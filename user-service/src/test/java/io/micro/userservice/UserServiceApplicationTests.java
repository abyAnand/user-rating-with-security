package io.micro.userservice;

import io.micro.userservice.entity.Rating;
import io.micro.userservice.external.service.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}


	@Autowired
	private RatingService ratingService;

	@Test
	void createRating(){
		Rating rating = Rating.builder().rating(10)
						.userId("").hotelId("")
						.feedback("This is created using Feign Client")
						.build();
		Rating savedRating = ratingService.createRating(rating).getBody();

		System.out.println("New Rating Created");
	}

}
