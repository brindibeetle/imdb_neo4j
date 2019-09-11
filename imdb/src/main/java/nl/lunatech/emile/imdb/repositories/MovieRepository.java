package nl.lunatech.emile.imdb.repositories;

import java.util.Collection;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nl.lunatech.emile.imdb.domain.Movie;

@Repository
//@RepositoryRestResource(collectionResourceRel = "movies", path = "movies")
public interface MovieRepository extends Neo4jRepository<Movie, String> {
 
	Movie findByTconst(@Param("tconst") String tconst);

	Movie findByPrimaryTitle(@Param("primaryTitle") String primaryTitle);
 
    @Query("MATCH (m:Movie) WHERE m.primaryTitle =~ ('(?i).*'+{primaryTitle}+'.*') RETURN m LIMIT 1000")
    Collection<Movie> findByTitleContaining(@Param("primaryTitle") String primaryTitle);

    @Query("MATCH (m:Movie) WHERE ({genre}) IN m.genres and exists(m.averageRating) RETURN m ORDER BY m.averageRating desc LIMIT 1000")
    Collection<Movie> findByGenreContaining(String genre);
}