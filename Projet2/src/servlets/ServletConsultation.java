package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
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
			Map<Meteo, List<String>> mapMeteo = new HashMap<>();
			String listId = "";
			List<Meteo> listeMeteo = serveur.getMeteo();
			for (Meteo meteo : listeMeteo) {
				listId += meteo.getIdMeteo() + " ";
				List<Image> listeImage = serveur.getlisteImage(meteo.getIdMeteo());
				List<String> listByte = new ArrayList<>();
				for (Image image : listeImage) {
					byte[] encodeBase64 = Base64.getEncoder().encode(image.getImage());
					String base64Encoded = new String(encodeBase64, "UTF-8");
					listByte.add(base64Encoded);
				}
				mapMeteo.put(meteo, listByte);
			}
			serveur.fermer();
			
			request.setAttribute("listId", listId);
			request.setAttribute("mapMeteo", mapMeteo);
			request.setAttribute("titre", "Consulation");
			request.setAttribute("contenu", "/WEB-INF/consulation/Consulation.jsp");
			request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);

		} catch (Exception e) {
			System.out.println("Erreur client RMI :" + e.getMessage());
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
			response.sendRedirect("ServletConsultation");
		}

		if (request.getParameter("filtrerMois") != null) {
			int mois1 = Integer.parseInt((String) request.getParameter("mois"));
			try {
				ServeurRmi serveur = Manager.creer(request).getServeur();
				serveur.ouvrir();
				Map<Meteo, List<String>> mapMeteo = new HashMap<>();
				String listId = "";
				List<Meteo> listeMeteo = serveur.getMeteo();
				for (Meteo meteo : listeMeteo) {
					listId += meteo.getIdMeteo() + " ";
					Calendar calendar1 = Calendar.getInstance();
					calendar1.setTime(meteo.getDate());
					int mois2 = calendar1.get(Calendar.MONTH);
					if (mois1 == mois2) {
						List<Image> listeImage = serveur.getlisteImage(meteo.getIdMeteo());
						List<String> listByte = new ArrayList<>();
						for (Image image : listeImage) {
							byte[] encodeBase64 = Base64.getEncoder().encode(image.getImage());
							String base64Encoded = new String(encodeBase64, "UTF-8");
							listByte.add(base64Encoded);
						}
						mapMeteo.put(meteo, listByte);
					}
				}
				serveur.fermer();
				
				request.setAttribute("listId", listId);
				request.setAttribute("mapMeteo", mapMeteo);
				request.setAttribute("titre", "Consulation");
				request.setAttribute("contenu", "/WEB-INF/consulation/Consulation.jsp");
				request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request,
						response);

			} catch (Exception e) {
				System.out.println("Erreur client RMI :" + e.getMessage());
			}
		}

		if (request.getParameter("filtrerJour") != null) {
			int jour1 = Integer.parseInt((String) request.getParameter("jour"));
			try {
				ServeurRmi serveur = Manager.creer(request).getServeur();
				serveur.ouvrir();
				Map<Meteo, List<String>> mapMeteo = new HashMap<>();
				List<Meteo> listeMeteo = serveur.getMeteo();
				String listId = "";
				for (Meteo meteo : listeMeteo) {
					listId += meteo.getIdMeteo() + " ";
					Calendar calendar1 = Calendar.getInstance();
					calendar1.setTime(meteo.getDate());
					int jour2 = calendar1.get(Calendar.DAY_OF_MONTH);
					if (jour1 == jour2) {
						List<Image> listeImage = serveur.getlisteImage(meteo.getIdMeteo());
						List<String> listByte = new ArrayList<>();
						for (Image image : listeImage) {
							byte[] encodeBase64 = Base64.getEncoder().encode(image.getImage());
							String base64Encoded = new String(encodeBase64, "UTF-8");
							listByte.add(base64Encoded);
						}
						mapMeteo.put(meteo, listByte);
					}
				}
				serveur.fermer();

				request.setAttribute("listId", listId);
				request.setAttribute("mapMeteo", mapMeteo);
				request.setAttribute("titre", "Consulation");
				request.setAttribute("contenu", "/WEB-INF/consulation/Consulation.jsp");
				request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request,
						response);

			} catch (Exception e) {
				System.out.println("Erreur client RMI :" + e.getMessage());
			}
		}

		if (request.getParameter("genererPdf") != null) {

			String ids = (String) request.getParameter("listId");			
			String [] tabId = ids.split(" "); 
			
			for(String id : tabId) {
				System.out.println(id);
			}
			
			response.sendRedirect("ServletConsultation");
		}
	}

}
