package dbExamples.models;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="MOVIE")
@Entity
@Access(AccessType.PROPERTY)
public class DbMovie {
	
	static enum MovieGenre {
		Drama, Comedy, Action, Animation, Documentary, Romance, Crime
	}
	
	private int id;
	private String title;
	private String country;
	private String language;
	private MovieGenre genre;
	private Date releasedOn;
	private DbMovieBook movieBook;
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	public int getId() {
		return id;
	}

	@Column(name="TITLE", nullable=false)
	public String getTitle() {
		return title;
	}
	
	@Column(name="COUNTRY", nullable=false)
	public String getCountry() {
		return country;
	}
	
	@Column(name="LANGUAGE", nullable=false)
	public String getLanguage() {
		return language;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name="GENRE")
	public MovieGenre getGenre() {
		return genre;
	}

	@Column(name="RELEASED_ON")
	public Date getReleasedOn() {
		return releasedOn;
	}

	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn(referencedColumnName="MOVIE_ID")
	public DbMovieBook getMovieBook() {
		return movieBook;
	}

	@Override
	public String toString() {
		return "DbMovie [id=" + id + ", title=" + title + ", country=" + country + ", language=" + language + ", genre="
				+ genre + ", releasedOn=" + releasedOn + ", movieBook=" + movieBook + "]";
	}

}
