package servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;


import beans.formulaires.FormulaireAjouterPhoto;

import validation.Validation;

@WebServlet("/ServletAjouterPhoto")
public class ServletAjouterPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletAjouterPhoto() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("titre", "Ajouter Photo");
		request.setAttribute("contenu", "/WEB-INF/ajouter/AjouterPhoto.jsp");
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
				if (itemsMap.get("ajouterPhoto").get(0).getFieldName() != null) {
					// debut de la verification du formulaire

					// creation de l'objet form
					FormulaireAjouterPhoto formulaireAjouterPhoto = new FormulaireAjouterPhoto();
					formulaireAjouterPhoto.setIdMeteo(itemsMap.get("idMeteo").get(0).getString());
					formulaireAjouterPhoto.setImageName(itemsMap.get("imageName").get(0).getString());

					Validation validation = formulaireAjouterPhoto.getValidation();

					if (validation.isValide()) {
						/*ServeurRmi serveur=Manager.creer(request).getServeur();
						serveur.ouvrir();
						
						int idMeteo = Integer.parseInt(formulaireAjouterPhoto.getIdMeteo());
						int idImage = serveur.addImage(idMeteo, itemsMap.get("imageName").get(0).getInputStream());
						serveur.saveImage(idImage, "path");
						serveur.delImage(idImage);
--------------------------------------------------------------------------------------------------------------------------------------
						
						System.out.println(idImage);
						
						serveur.fermer();
						*/
					} else {
						request.setAttribute("validation", validation);
						request.setAttribute("erreur", "Il y a une erreur dans le formulaire d'ajoue de photo");
						request.setAttribute("titre", "Ajouter Photo");
						request.setAttribute("contenu", "/WEB-INF/ajouter/AjouterPhoto.jsp");
						request.getServletContext().getRequestDispatcher("/WEB-INF/models/model.jsp").forward(request, response);
					}

				}

			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		}
	}

}
