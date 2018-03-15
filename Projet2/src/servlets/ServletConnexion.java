package servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.formulaires.FormulaireConnexion;
import beans.utilisateur.Utilisateur;
import manager.Manager;
import interfaceRmi.ServeurRmi;
import validation.Validation;

@WebServlet("/ServletConnexion")
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public ServletConnexion() {
		super();
			
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		request.setAttribute("titre", "Connexion");
		request.setAttribute("contenu", "/WEB-INF/connexion/Connexion.jsp");
		request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// on verifier que un formulaire a bien ete envoyer
		if (request.getParameter("connexion") != null) {

			// on recupère les champs
			String email = (String) request.getParameter("email");
			String password = (String) request.getParameter("password");

			Utilisateur utilisateur = new Utilisateur();
			utilisateur.setEmail(email);
			utilisateur.setPassword(password);

			// debut de la validation du formulaire
			FormulaireConnexion formulaireConnexion = new FormulaireConnexion();
			formulaireConnexion.setEmail(email);
			formulaireConnexion.setPassword(password);
			Validation validation = formulaireConnexion.getValidation();

			if (validation.isValide()) {
				// Identification de l'utilisateur
				ServeurRmi serveur=Manager.creer(request).getServeur();
				serveur.ouvrir();

				if (serveur.identification(utilisateur)) {
					Manager manager = Manager.creer(request);
					manager.setUtilisateur(utilisateur);
					manager.setIdentifier(true);
					response.sendRedirect("ServletAccueil");
				} else {
					// redirection vres le formulaire
					request.setAttribute("titre", "Connexion");
					request.setAttribute("contenu", "/WEB-INF/connexion/Connexion.jsp");
					request.setAttribute("validation", validation);
					request.setAttribute("erreur", "Il y a une erreur dans le mail ou le mdp");
					request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);
				}

				serveur.fermer();
			} else {
				// redirection vres le formulaire
				request.setAttribute("titre", "Connexion");
				request.setAttribute("contenu", "/WEB-INF/connexion/Connexion.jsp");
				request.setAttribute("validation", validation);
				request.setAttribute("erreur", "Il y a une erreur dans le formulaire de connexion");
				request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);
			}

		}
	}

}
