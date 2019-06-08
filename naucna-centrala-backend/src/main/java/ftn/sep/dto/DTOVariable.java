package ftn.sep.dto;

import java.io.Serializable;

public class DTOVariable implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String id;
	public String name;
	
	public DTOVariable() {}

	public DTOVariable(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
