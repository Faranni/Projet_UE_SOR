package beans.utilisateur;

import java.io.Serializable;

public class Utilisateur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private int id;
	

	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	@Override
	public String toString() {
		return "Utilisateur [email=" + email + ", password=" + password + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null) {
			return false;
		}

		if (!(o instanceof Utilisateur)) {
			return false;
		}

		Utilisateur utilisateur = (Utilisateur) o;
		return this.email.equals(utilisateur.getEmail()) && this.password.equals(utilisateur.getPassword());
	}

}
