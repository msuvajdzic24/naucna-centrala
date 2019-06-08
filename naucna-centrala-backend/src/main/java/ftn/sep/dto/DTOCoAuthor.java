package ftn.sep.dto;

public class DTOCoAuthor {
	
	private String name;
	private String surname;
	private String email;
	private String city;
	private String country;
	
	public DTOCoAuthor() {
	}

	public DTOCoAuthor(String name, String surname, String email, String city, String country) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.city = city;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
