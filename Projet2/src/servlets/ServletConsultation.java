package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.meteo.Image;
import beans.meteo.Meteo;
import interfaceRmi.ServeurRmi;
import manager.Manager;

@WebServlet("/ServletConsultation")
public class ServletConsultation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletConsultation() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			ServeurRmi serveur = Manager.creer(request).getServeur();

			serveur.ouvrir();
			Map<Meteo,List<Image>> mapMeteo = new HashMap<>(); 
			List<Meteo> listeMeteo = serveur.getMeteo();
			for(Meteo  meteo : listeMeteo) {
				List<Image> listeImage = serveur.getlisteImage(meteo.getIdMeteo());
				System.out.println(listeImage.toString());
				mapMeteo.put(meteo, listeImage);
			}
			
			serveur.fermer();
			
			
			request.setAttribute("mapMeteo", mapMeteo);
			request.setAttribute("titre", "Consulation");
			request.setAttribute("contenu", "/WEB-INF/consulation/Consulation.jsp");
			request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);

		} catch (Exception e) {
			System.out.println("Erreur client RMI");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("supprimer") != null) {
			int idMeteo = Integer.parseInt((String) request.getParameter("idMeteo"));
			try {
				ServeurRmi serveur = Manager.creer(request).getServeur();
				serveur.ouvrir();
				serveur.supprimerMeteo(idMeteo);
				serveur.fermer();
			} catch (Exception e) {
				System.out.println("Erreur client RMI");
			}
		}
		response.sendRedirect("ServletConsultation");
		return;
	}

}
