package vn.heyzoo.heyzoosb.form;

public class PersonForm {
	private String firstName;
	private String lastName;
	public PersonForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PersonForm(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
