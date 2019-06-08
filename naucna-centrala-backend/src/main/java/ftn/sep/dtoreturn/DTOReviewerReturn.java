package ftn.sep.dtoreturn;

import ftn.sep.model.Editor;
import ftn.sep.model.Reviewer;
import ftn.sep.model.User;

public class DTOReviewerReturn {
	
	private Long id;
	private String name;
	private String surname;
	private String username;
	private String email;
	private String city;
	private String country;
	
	public DTOReviewerReturn() {}
	
	public DTOReviewerReturn(Long id, String name, String surname, String username, String email, String city,
			String country) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.email = email;
		this.city = city;
		this.country = country;
	}

	public static DTOReviewerReturn convert(User user) {
		DTOReviewerReturn dtoRR = new DTOReviewerReturn();
		dtoRR.setId(user.getId());
		dtoRR.setUsername(user.getUsername());
		dtoRR.setEmail(user.getEmail());
		if (user instanceof Editor) {
			dtoRR.setName(((Editor) user).getName());
			dtoRR.setSurname(((Editor) user).getSurname());
			dtoRR.setCity(((Editor) user).getCity());
			dtoRR.setCountry(((Editor) user).getCountry());
		} else if (user instanceof Reviewer) {
			dtoRR.setName(((Reviewer) user).getName());
			dtoRR.setSurname(((Reviewer) user).getSurname());
			dtoRR.setCity(((Reviewer) user).getCity());
			dtoRR.setCountry(((Reviewer) user).getCountry());
		}
		return dtoRR;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
