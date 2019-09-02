package io.javabrains.io.moviecatalogservice.resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.javabrains.moviecatalogservice.model.CatalogItem;
import io.javabrains.moviecatalogservice.model.Movie;
import io.javabrains.moviecatalogservice.model.Rating;
import io.javabrains.moviecatalogservice.model.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder webClientBuilder;

	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		// Movie movie = restTemplate.getForObject("http://localhost:8083/movies/saj",
		// Movie.class);

		// WebClient.Builder builder = WebClient.builder();
	
//		List<Rating> ratings = Arrays.asList(
//				new Rating("1234", 4),
//				new Rating("5678", 3)
//		);
		// All rated movies Id
		UserRating ratings = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/" + userId,
				UserRating.class);

		// For each movie Id call movie info service and get detail
		return ratings.getUserRating().stream().map(rating -> {
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
			/*
			 * Movie movie = webClientBuilder.build() .get()
			 * .uri("http://localhost:8083/movies/" + rating.getMovieId()) .retrieve()
			 * .bodyToMono(Movie.class) .block();
			 */

			// pull them all together
			return new CatalogItem(movie.getMovieName(), "Desc", rating.getRating());
		}).collect(Collectors.toList());

		// return Collections.singletonList(new CatalogItem("Transformaer", "Test", 4));
	}

}
