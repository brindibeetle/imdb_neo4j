package nl.lunatech.emile.imdb.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.lunatech.emile.imdb.domain.Movie;
import nl.lunatech.emile.imdb.services.MovieService;

@RestController
@RequestMapping("/movie")
public class MovieController {

	// @Autowired
	private final MovieService movieService;

	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping("/findByTconst")
	public Movie findByTconst(@RequestParam(value = "tconst", defaultValue = "tt0000000") String tconst) {
		return movieService.findByTconst(tconst);
	}

	@GetMapping("/findByPrimaryTitle")
	public Movie findByPrimaryTitle(@RequestParam(value = "title", defaultValue = "cabaret") String primaryTitle) {
		return movieService.findByPrimaryTitle(primaryTitle);
	}

	@GetMapping("/findByTitleContaining")
	public Collection<Movie> findByTitleContaining(
			@RequestParam(value = "title", defaultValue = "cabaret") String primaryTitle) {
		return movieService.findByTitleContaining(primaryTitle);
	}

	@GetMapping("/findByGenreContaining")
	public Collection<Movie> findByGenreContaining(
			@RequestParam(value = "genre", defaultValue = "cabaret") String genre) {
		return movieService.findByGenreContaining(genre);
	}

}
