package nl.lunatech.emile.imdb.domain;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Person {

	@Id
	private String nconst;
	private String primaryName;
	private int birthYear;
	private int deathYear;
	private List<String> primaryProfession;
	private List<String> knownForTitles;

	@Relationship(type = "PLAYS_IN")
	private List<Movie> movies = new ArrayList<>();

	public Person() {
	}

	public Person(String nconst, String primaryName, int birthYear) {
		this.nconst = nconst;
		this.primaryName = primaryName;
		this.birthYear = birthYear;
	}

	public String getNconst() {
		return nconst;
	}

	public void setNconst(String nconst) {
		this.nconst = nconst;
	}

	public String getPrimaryName() {
		return primaryName;
	}

	public void setPrimaryName(String primaryName) {
		this.primaryName = primaryName;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public int getDeathYear() {
		return deathYear;
	}

	public void setDeathYear(int deathYear) {
		this.deathYear = deathYear;
	}

	public List<String> getPrimaryProfession() {
		return primaryProfession;
	}

	public void setPrimaryProfession(List<String> primaryProfession) {
		this.primaryProfession = primaryProfession;
	}

	public List<String> getKnownForTitles() {
		return knownForTitles;
	}

	public void setKnownForTitles(List<String> knownForTitles) {
		this.knownForTitles = knownForTitles;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	@Override
	public String toString() {
		return "Person [nconst=" + nconst + ", primaryName=" + primaryName + ", birthYear=" + birthYear + ", deathYear="
				+ deathYear + ", primaryProfession=" + primaryProfession + ", knownForTitles=" + knownForTitles
				+ ", movies=" + movies + "]";
	}

}