package co.grandcircus.MovieApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import co.grandcircus.MovieApi.Dao.MovieDao;
import co.grandcircus.MovieApi.Entity.Movie;

@RestController
public class MovieApiController {

	@Autowired
	private MovieDao movieDao;

	@GetMapping("/movies")
	List<Movie> listOfAllMovies(@RequestParam(required = false) String title,
			@RequestParam(required = false) String category) {

		if ((title == null || title.isEmpty()) && (category == null || category.isEmpty())) {
			return movieDao.findAll();
		} else if (!title.isEmpty()) {
			return movieDao.findByTitleContaining(title);
		} else { 
			return movieDao.findByCategoryContaining(category);
		}
	}

	@GetMapping("/movies/{id}")
	Movie singleMovieSearch(@PathVariable Long id) {
		
		return movieDao.findById(id)
				.orElseThrow(
				()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such product")
				);
	}
	
	@GetMapping("/movies/random")
	Movie singleRandomMovie(@RequestParam(required = false) String category) {
		Random randomMovie = new Random();
		if ((category == null || category.isEmpty())) {
		List<Movie> movies = movieDao.findAll();
		return movies.get(randomMovie.nextInt(movies.size()));
		} else {
			List<Movie> movies = movieDao.findByCategoryContaining(category);
			return movies.get(randomMovie.nextInt(movies.size()));
		}
	}
	
	@GetMapping("/movies/randomList")
	List<Movie> randomMovieList(@RequestParam Integer num) {
		Random randomMovie = new Random();
		List<Movie> movies = movieDao.findAll();
		
		List<Movie> randomMovies = new ArrayList<>();
		
		for (int i = 0; i < num; i++) {
			int randomIndex = randomMovie.nextInt(movies.size());
			Movie movie = movies.get(randomIndex);
			randomMovies.add(movie);
			movies.remove(movie);
		}
		
		return randomMovies;
	}
	
	@GetMapping("/movies/categories")
	List<String> listOfMovieCategories() {
		return movieDao.listofCategories();
	}

}
