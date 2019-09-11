package nl.lunatech.emile.imdb.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.lunatech.emile.imdb.domain.Movie;
import nl.lunatech.emile.imdb.repositories.MovieRepository;

@Service
public class MovieService {

    private final static Logger LOG = LoggerFactory.getLogger(MovieService.class);

	private final MovieRepository movieRepository;
	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put(key1, value1);
		result.put(key2, value2);
		return result;
	}

    @Transactional(readOnly = true)
	public Movie findByTconst(String tconst) {
        Movie result = movieRepository.findByTconst(tconst);
        return result;
	}
    
    @Transactional(readOnly = true)
    public Movie findByPrimaryTitle(String primaryTitle) {
        Movie result = movieRepository.findByPrimaryTitle(primaryTitle);
        return result;
    }

    @Transactional(readOnly = true)
    public Collection<Movie> findByTitleContaining(String primaryTitle) {
        Collection<Movie> result = movieRepository.findByTitleContaining(primaryTitle);
        return result;
    }

    @Transactional(readOnly = true)
	public Collection<Movie> findByGenreContaining(String genre) {
        Collection<Movie> result = movieRepository.findByGenreContaining(genre);
        
        return result;
	}

}
