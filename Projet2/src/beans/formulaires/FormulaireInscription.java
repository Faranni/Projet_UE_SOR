package beans.formulaires;

import annotations.Regexp;
import validation.Validation;

public class FormulaireInscription {
	@Regexp(expr = ".{2,}@.{2,}[.].{2,}", value = "Il faut une adresse mail")
	private String email;

	@Regexp(expr = ".{5,}", value = "Il faut au moins 5 lettres pour le mdp")
	private String password;

	@Regexp(expr = ".{5,}", value = "Il faut au moins 5 lettres pour le mdp")
	private String confirm;

	
	
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



	public String getConfirm() {
		return confirm;
	}



	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}



	public Validation getValidation() {
		// debut de la validation du formulaire
		Validation validation = new Validation();

		validation.regexp(FormulaireInscription.class, "email", this.getEmail());
		validation.regexp(FormulaireInscription.class, "password", this.getPassword());
		validation.regexp(FormulaireInscription.class, "confirm", this.confirm);

		if (!this.getPassword().equals(this.confirm)) {
			validation.getErreurs().put("confirm", "les deux mot de passe ne sont pas identique");
			validation.setValide(false);
		}
		
		return validation;
	}
}
