package examples.dbExamples.models;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="MOVIE_BOOK")
@Entity
@Access(AccessType.PROPERTY)
public class DbMovieBook{
	
	private int movieId;
	private String title;
	private String author;
	private String country;
	private String language;
	private DbMovie movie;
	
	@Id
	@GeneratedValue(generator="SharedPrimaryKeyGenerator")
    @GenericGenerator(name="SharedPrimaryKeyGenerator",strategy="foreign",parameters = @Parameter(name="property", value="movie"))
	@Column(name="MOVIE_ID", unique = true, nullable = false)
	public int getMovieId() {
		return movieId;
	}
	
	@OneToOne
	@PrimaryKeyJoinColumn
	public DbMovie getMovie() {
		return movie;
	}
	
	@Column(name="TITLE", nullable=false)
	public String getTitle() {
		return title;
	}
	
	@Column(name="AUTHOR", nullable=false)
	public String getAuthor() {
		return author;
	}
	
	@Column(name="COUNTRY", nullable=false)
	public String getCountry() {
		return country;
	}
	
	@Column(name="LANGUAGE", nullable=false)
	public String getLanguage() {
		return language;
	}

	@Override
	public String toString() {
		return "DbMovieBook [movieId=" + movieId + ", title=" + title + ", author=" + author + ", country=" + country
				+ ", language=" + language + "]";
	}
}
