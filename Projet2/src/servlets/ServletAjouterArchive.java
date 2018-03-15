package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import beans.formulaires.FormulaireAjouterArchive;
import beans.meteo.Meteo;
import beans.meteo.Temps;
import interfaceRmi.ServeurRmi;
import manager.Manager;
import validation.Validation;

@WebServlet("/ServletAjouterArchive")
public class ServletAjouterArchive extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletAjouterArchive() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("titre", "Ajouter Archive");
		request.setAttribute("contenu", "/WEB-INF/ajouter/AjouterArchive.jsp");
		request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (ServletFileUpload.isMultipartContent(request)) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				Map<String, List<FileItem>> itemsMap = upload.parseParameterMap(request);

				// on verifie que le form a bien ete envoyer
				if (itemsMap.get("ajouterArchive").get(0).getFieldName() != null) {
					// debut de la verification du formulaire

					// creation de l'objet form
					FormulaireAjouterArchive formulaireAjouterArchive = new FormulaireAjouterArchive();
					formulaireAjouterArchive.setArchiveName(itemsMap.get("archiveName").get(0).getString());

					Validation validation = formulaireAjouterArchive.getValidation();

					if (validation.isValide()) {
						// itemsMap.get("archiveFile").get(0).getInputStream()

						// debut du parsage de l'archive
						List<Meteo> meteos = new ArrayList<>();
						try {
							DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
							DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
							Document doc = dBuilder.parse(itemsMap.get("archiveFile").get(0).getInputStream());
							doc.getDocumentElement().normalize();
							NodeList nList = doc.getElementsByTagName("meteo");							
							for (int temp = 0; temp < nList.getLength(); temp++) {
								Node nNode = nList.item(temp);
								Meteo meteo = new Meteo();
								if (nNode.getNodeType() == Node.ELEMENT_NODE) {
									Element eElement = (Element) nNode;
									String lieu = eElement.getElementsByTagName("lieu").item(0).getTextContent();
									String type = eElement.getElementsByTagName("temp").item(0).getTextContent();
									String date = eElement.getElementsByTagName("date").item(0).getTextContent();
									String minimum = eElement.getElementsByTagName("minimum").item(0).getTextContent();
									String maximum = eElement.getElementsByTagName("maximum").item(0).getTextContent();
									String moyenne = eElement.getElementsByTagName("moyenne").item(0).getTextContent();
									meteo.setLieu(lieu);
									meteo.setTemps(Temps.valueOf(type));
									meteo.setDate(date);
									meteo.setMin(Double.parseDouble(minimum));
									meteo.setMax(Double.parseDouble(maximum));
									meteo.setMoy(Double.parseDouble(moyenne));
									meteos.add(meteo);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						// ajout a la bdd
						ServeurRmi serveur=Manager.creer(request).getServeur();
						serveur.ouvrir();
						serveur.ajouterListeMeteo(meteos);
						serveur.fermer();
						
						// redirection vers la servlet consultation
						response.sendRedirect("ServletConsultation");
						
					} else {
						request.setAttribute("validation", validation);
						request.setAttribute("erreur", "Il y a une erreur dans le formulaire d'ajoue d'archive");
						request.setAttribute("titre", "Ajouter Archive");
						request.setAttribute("contenu", "/WEB-INF/ajouter/AjouterArchive.jsp");
						request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request,
								response);
					}

				}

			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		}
	}

}
