package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import beans.formulaires.FormulaireInscription;
import beans.utilisateur.Utilisateur;
import interfaceRmi.ServeurRmi;
import manager.Manager;
import validation.Validation;

@WebServlet("/ServletInscription")
public class ServletInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	public ServletInscription() {
		super();
		 	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("titre", "Inscription");
		request.setAttribute("contenu", "/WEB-INF/inscription/Inscription.jsp");
		request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// on verifier que un formulaire a bien ete envoyer
		if (request.getParameter("inscription") != null) {

			// on recupère les champs
			String email = (String) request.getParameter("email");
			String password = (String) request.getParameter("password");
			String confirm = (String) request.getParameter("confirm");

			Utilisateur utilisateur = new Utilisateur();
			utilisateur.setEmail(email);
			utilisateur.setPassword(password);

			// debut de la validation du formulaire
			FormulaireInscription formulaireInscription = new FormulaireInscription();
			formulaireInscription.setEmail(email);
			formulaireInscription.setPassword(password);
			formulaireInscription.setConfirm(confirm);
			Validation validation = formulaireInscription.getValidation();

			if (validation.isValide()) {
				// partie base de donné
				ServeurRmi serveur=Manager.creer(request).getServeur();
				serveur.ouvrir();

				// deja inscrit ?
				if (serveur.dejaInscrit(utilisateur)) {
					// redirection vres le formulaire
					request.setAttribute("validation", validation);
					request.setAttribute("erreur", "vous ete deja inscit");
					request.setAttribute("titre", "Inscription");
					request.setAttribute("contenu", "/WEB-INF/inscription/Inscription.jsp");
					request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request,
							response);
				} else {
					// inscription
					serveur.inscription(utilisateur);
					response.sendRedirect("ServletConnexion");
				}

				serveur.fermer();

			} else {
				// redirection vres le formulaire
				request.setAttribute("validation", validation);
				request.setAttribute("erreur", "Il y a une erreur dans le formulaire d'incription");
				request.setAttribute("titre", "Inscription");
				request.setAttribute("contenu", "/WEB-INF/inscription/Inscription.jsp");
				request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request,
						response);
			}

		}
	}

}
