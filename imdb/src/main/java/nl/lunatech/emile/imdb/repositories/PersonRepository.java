package nl.lunatech.emile.imdb.repositories;

import java.util.Collection;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nl.lunatech.emile.imdb.domain.Person;

@Repository
public interface PersonRepository extends Neo4jRepository <Person, String> {
	
	Person findByNconst(@Param("nconst")String nconst);

	Person findByPrimaryName(@Param("primaryName") String primaryName);

    @Query("MATCH (p:Person) WHERE p.primaryName =~ ('(?i).*'+{primaryName}+'.*') RETURN p LIMIT 1000")
	Collection<Person> findByNameContaining(@Param("primaryName") String primaryName);

    @Query("MATCH (p1:Person), (p2:Person) "
    		+ ", p =shortestPath((p1)-[:PLAYS_IN*0..10]-(p2))"
    		+ " WHERE p1.nconst = ({nconst1}) and p2.nconst = ({nconst2})"
    		+ " return length(p) ")
    Integer findTheDegreeOfSeparation(String nconst1, String nconst2);
}
