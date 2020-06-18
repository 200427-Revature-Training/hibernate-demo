package com.revature.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Entity - Defines that a class can have its persistence managed by a
 *         JPA/Hibernate
 * @Table (optional) - Provide table level configuration
 * @Id - Defines a property as a primary key
 * @GeneratedValue - Configure auto generated value
 * @Column (optional) - Provide column level configuration
 * @Transient - Marks a column to be ignored for persistence
 */

@NamedQueries(value = {
		@NamedQuery(name = "getBearsByBreed", query="FROM Bear b WHERE breed = :breed")
})
@Entity
@Table(name = "bears")
public class Bear {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String gender;

	@Column(length = 300)
	private String breed;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	/** weight in kilograms */
	@Transient
	private double weight;

	// The one side of a relationship by default loads eagerly
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cave_id")
	private Cave cave;
	
	@ManyToMany
	@JoinTable(name="bear_cubs", joinColumns = { @JoinColumn(name="parent_id") },
						inverseJoinColumns = { @JoinColumn(name="cub_id")})
	private List<Bear> cubs;

	@ManyToMany
	@JoinTable(name="bear_cubs", joinColumns = { @JoinColumn(name="cub_id") },
						inverseJoinColumns = { @JoinColumn(name="parent_id")})
	private List<Bear> parents;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Cave getCave() {
		return cave;
	}

	public void setCave(Cave cave) {
		this.cave = cave;
	}
	
	public List<Bear> getCubs() {
		return cubs;
	}

	public void setCubs(List<Bear> cubs) {
		this.cubs = cubs;
	}

	public List<Bear> getParents() {
		return parents;
	}

	public void setParents(List<Bear> parents) {
		this.parents = parents;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((breed == null) ? 0 : breed.hashCode());
		result = prime * result + ((cave == null) ? 0 : cave.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bear other = (Bear) obj;
		if (breed == null) {
			if (other.breed != null)
				return false;
		} else if (!breed.equals(other.breed))
			return false;
		if (cave == null) {
			if (other.cave != null)
				return false;
		} else if (!cave.equals(other.cave))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bear [id=" + id + ", gender=" + gender + ", breed=" + breed + ", dateOfBirth=" + dateOfBirth
				+ ", weight=" + weight + "]";
	}

	public Bear(int id, String gender, String breed, LocalDate dateOfBirth, double weight) {
		super();
		this.id = id;
		this.gender = gender;
		this.breed = breed;
		this.dateOfBirth = dateOfBirth;
		this.weight = weight;
	}

	public Bear() {
		super();
		// TODO Auto-generated constructor stub
	}
}
