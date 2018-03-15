package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.formulaires.FormulaireAjouter;
import beans.meteo.Meteo;
import beans.meteo.Temps;
import interfaceRmi.ServeurRmi;
import manager.Manager;
import validation.Validation;

@WebServlet("/ServletEdition")
public class ServletEdition extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletEdition() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(!Manager.creer(request).isIdentifier()) {
			response.sendRedirect("ServletConnexion");
			return ;
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(!Manager.creer(request).isIdentifier()) {
			response.sendRedirect("ServletConnexion");
			return ;
		}
		// ajouter dans le formulaire des bonnes valeur
		if (request.getParameter("edition") != null) {
			int idMeteo = Integer.parseInt((String) request.getParameter("idMeteo"));
			Meteo meteo = new Meteo();
			try {
				ServeurRmi serveur = Manager.creer(request).getServeur();
				serveur.ouvrir();
				meteo = serveur.getMeteo(idMeteo);
				serveur.fermer();
			} catch (Exception e) {
				System.out.println("Erreur client RMI");
			}
			
			FormulaireAjouter formulaireAjouter = new FormulaireAjouter();
			String [] date = meteo.getDate().toString().split("-");
			formulaireAjouter.setDate(date[2]+"/"+date[1]+"/"+date[0]);
			formulaireAjouter.setLieu(meteo.getLieu());
			formulaireAjouter.setType(meteo.getTemps().toString());
			formulaireAjouter.setMinimum(meteo.getMin().toString());
			formulaireAjouter.setMaximum(meteo.getMax().toString());
			formulaireAjouter.setMoyenne(meteo.getMoy().toString());
			Validation validation = formulaireAjouter.getValidation();
			
			request.setAttribute("idMeteo", meteo.getIdMeteo());
			request.setAttribute("validation", validation);
			request.setAttribute("titre", "Edition");
			request.setAttribute("contenu", "/WEB-INF/edition/Edition.jsp");
			request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);
		}

		// modification de la donné dans la bdd
		if (request.getParameter("modification") != null) {
			System.out.println("modification");

			int idMeteo = Integer.parseInt((String) request.getParameter("idMeteo"));
			String lieu = (String) request.getParameter("lieu");
			String type = (String) request.getParameter("type");
			String date = (String) request.getParameter("date");
			String minimum = (String) request.getParameter("minimum");
			String maximum = (String) request.getParameter("maximum");
			String moyenne = (String) request.getParameter("moyenne");

			// debut de la validation du formulaire
			FormulaireAjouter formulaireAjouter = new FormulaireAjouter();
			formulaireAjouter.setDate(date);
			formulaireAjouter.setLieu(lieu);
			formulaireAjouter.setType(type);
			formulaireAjouter.setMinimum(minimum);
			formulaireAjouter.setMaximum(maximum);
			formulaireAjouter.setMoyenne(moyenne);
			Validation validation = formulaireAjouter.getValidation();

			if (validation.isValide()) {
				// creation de l'obkjet meteo
				Meteo meteo = new Meteo();
				meteo.setLieu(lieu);
				meteo.setTemps(Temps.valueOf(type));
				meteo.setDate(date);
				meteo.setMin(Double.parseDouble(minimum));
				meteo.setMax(Double.parseDouble(maximum));
				meteo.setMoy(Double.parseDouble(moyenne));
				

				ServeurRmi serveur=Manager.creer(request).getServeur();
				serveur.ouvrir();
				serveur.majMeteo(meteo, idMeteo);
				serveur.fermer();

				// redirection vers la servlet consultation
				response.sendRedirect("ServletConsultation");

			} else {
				System.out.println("erreur");
				// redirection vers le formulaire
				request.setAttribute("idMeteo", idMeteo);
				request.setAttribute("validation", validation);
				request.setAttribute("erreur", "Il y a une erreur dans le formulaire d'edition");
				request.setAttribute("titre", "Edition");
				request.setAttribute("contenu", "/WEB-INF/edition/Edition.jsp");
				request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);
			}

		}
	}

}
