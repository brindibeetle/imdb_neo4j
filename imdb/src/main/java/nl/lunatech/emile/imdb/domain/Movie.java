package nl.lunatech.emile.imdb.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@NodeEntity
public class Movie {

	@Id
	private String tconst;
	private String primaryTitle;
	private String originalTitle;
	private boolean isAdult;
	private int startYear;
	private int endYear;
	private int runtimeMinutes;
	private List<String> genres;
	
	private BigDecimal averageRating;
	private BigInteger numVotes;

	@JsonIgnoreProperties("movie")
	@Relationship(type = "PLAYS_IN", direction = Relationship.INCOMING)
	private List<PlaysIn> roles;

	public List<PlaysIn> getRoles() {
		return roles;
	}

	public void setRoles(List<PlaysIn> roles) {
		this.roles = roles;
	}

	public Movie() {
	}

	public Movie(String tconst, String primaryTitle, int startYear) {
		this.tconst = tconst;
		this.primaryTitle = primaryTitle;
		this.startYear = startYear;
	}

	public String getTconst() {
		return tconst;
	}

	public void setTconst(String tconst) {
		this.tconst = tconst;
	}

	public String getPrimaryTitle() {
		return primaryTitle;
	}

	public void setPrimaryTitle(String primaryTitle) {
		this.primaryTitle = primaryTitle;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public boolean isAdult() {
		return isAdult;
	}

	public void setAdult(boolean isAdult) {
		this.isAdult = isAdult;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public int getRuntimeMinutes() {
		return runtimeMinutes;
	}

	public void setRuntimeMinutes(int runtimeMinutes) {
		this.runtimeMinutes = runtimeMinutes;
	}

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}
	
	public BigDecimal getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(BigDecimal averageRating) {
		this.averageRating = averageRating;
	}

	public BigInteger getNumVotes() {
		return numVotes;
	}

	public void setNumVotes(BigInteger numVotes) {
		
		this.numVotes = numVotes;
	}
}