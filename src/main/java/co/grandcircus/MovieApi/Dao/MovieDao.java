package co.grandcircus.MovieApi.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.grandcircus.MovieApi.Entity.Movie;

public interface MovieDao extends JpaRepository<Movie, Long>{

	List<Movie> findByTitleContaining(String title);
	List<Movie> findByCategoryContaining(String category);
	@Query(value = "SELECT category FROM Movie")
	List<String> listofCategories();
}
