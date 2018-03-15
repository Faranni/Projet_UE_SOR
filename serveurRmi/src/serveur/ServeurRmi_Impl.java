package serveur;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import beans.meteo.Image;
import beans.meteo.Meteo;
import beans.meteo.Temps;
import beans.utilisateur.Utilisateur;
import interfaceRmi.ServeurRmi;
import jdk.internal.dynalink.beans.StaticClass;

public class ServeurRmi_Impl implements ServeurRmi {
	Connection connection = null;
	
	private final int BORDURE_HAUT = 810;
	private final int BORDURE_BAS = 30;
	private final int PAS_TITRE_Y=60;
	private final int PAS_TITRE_X=200;
	private final int PAS_DONNEES_Y=30;
	private final int BORDURE_GAUCHE=25;
	private final int BORDURE_DROITE=565;
	private final int PAS_IMAGE_Y=50;
	private final int PAS_IMAGE_X=50;
	private final int HAUTEUR_MAX=40;
	private final int LARGEUR_MAX=40;
	private final int TAILLE_POLICE_DONNEES=12;
	private final int TAILLE_POLICE_TITRE=20;
	private final int PAS_BLANC=10;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public boolean ouvrir()throws RemoteException{
		try {
			ResourceBundle rs = ResourceBundle.getBundle("properties/config");
			String url = rs.getString("url");
			String user = rs.getString("user");
			String password = rs.getString("password");
			this.connection = DriverManager.getConnection(url, user, password);			
		} catch (Exception e) {
			System.out.println("Erreur Base.ouvrir " + e.getMessage());
			return false;
		}
		System.out.println("Nouvelle Connexion");
		return true;
	}

	@Override
	public void fermer()throws RemoteException {
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (Exception e) {
				System.out.println("Erreur Base.fermer " + e.getMessage());
			}
		}
		System.out.println("Fermeture de la Connexion");
		
	}

	@Override
	public boolean identification(Utilisateur utilisateur)throws RemoteException {
		try {
			String req = "SELECT * FROM `t_connexion`";
			PreparedStatement preparedStatement = this.connection.prepareStatement(req);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Utilisateur utilisateur2 = new Utilisateur();
				utilisateur2.setEmail(resultSet.getString("mail"));
				utilisateur2.setPassword(resultSet.getString("mdp"));

				if (utilisateur.equals(utilisateur2)) {
					return true;
				}
			}

			resultSet.close();
			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("Erreur Base.identification " + e.getMessage());
		}
		System.out.println("Nouvelle authentification");
		return false;
	}

	@Override
	public boolean dejaInscrit(Utilisateur utilisateur)throws RemoteException {
		try {
			String req = "SELECT * FROM `t_connexion`";
			PreparedStatement preparedStatement = this.connection.prepareStatement(req);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Utilisateur utilisateur2 = new Utilisateur();
				utilisateur2.setEmail(resultSet.getString("mail"));
				utilisateur2.setPassword(resultSet.getString("mdp"));

				if (utilisateur.getEmail().equals(utilisateur2.getEmail())) {
					return true;
				}

			}
			resultSet.close();
			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("Erreur Base.dejaInscrit " + e.getMessage());
		}
		System.out.println("Recherche d'un utilisateur");
		return false;
	}

	@Override
	public void inscription(Utilisateur utilisateur)throws RemoteException {
		try {
			String req = "INSERT INTO `t_connexion` (`idConnexion`, `mail`, `mdp`) VALUES (NULL, ?, ?)";
			PreparedStatement preparedStatement = this.connection.prepareStatement(req);

			preparedStatement.setString(1, utilisateur.getEmail());
			preparedStatement.setString(2, utilisateur.getPassword());

			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			System.out.println("Erreur Base.inscription " + e.getMessage());
		}
		System.out.println("Nouvelle Incription");
	}

	

	@Override
	public List<Meteo> getMeteo() throws RemoteException{
		List<Meteo> meteos = new ArrayList<>();

		try {

			String req = "SELECT * FROM `t_meteo`";
			PreparedStatement preparedStatement = this.connection.prepareStatement(req);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Meteo meteo = new Meteo();
				meteo.setLieu(resultSet.getString("lieu"));
				meteo.setTemps(Temps.valueOf(resultSet.getString("type")));
				meteo.setDate(resultSet.getDate("date"));
				meteo.setIdMeteo(resultSet.getInt("idMeteo"));
				meteo.setMax(resultSet.getDouble("maximum"));
				meteo.setMoy(resultSet.getDouble("moyenne"));
				meteo.setMin(resultSet.getDouble("minimum"));
				System.out.println(meteo.toString());
				meteos.add(meteo);
			}
			resultSet.close();
			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("Erreur Base.getMeteo " + e.getMessage());
		}
		System.out.println("Recuperation des donnees meteo");
		return meteos;
	}
	
	@Override
	public Meteo getMeteo(int idMeteo) throws RemoteException{
		Meteo meteo = null;

		try {

			String req = "SELECT * FROM `t_meteo` WHERE idMeteo=?";
			PreparedStatement preparedStatement = this.connection.prepareStatement(req);
			preparedStatement.setInt(1, idMeteo);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				meteo = new Meteo();
				meteo.setLieu(resultSet.getString("lieu"));
				meteo.setTemps(Temps.valueOf(resultSet.getString("type")));
				meteo.setDate(resultSet.getDate("date"));
				meteo.setIdMeteo(resultSet.getInt("idMeteo"));
				meteo.setMax(resultSet.getDouble("maximum"));
				meteo.setMoy(resultSet.getDouble("moyenne"));
				meteo.setMin(resultSet.getDouble("minimum"));
				System.out.println(meteo.toString());
			}
			resultSet.close();
			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("Erreur Base.getMeteo " + e.getMessage());
		}
		System.out.println("Recuperation des donnees meteo");
		return meteo;
	}


	@Override
	public int ajouterMeteo(Meteo meteo)throws RemoteException {
		int key=-1;
		try {

			int idMeteo;
			if((idMeteo = this.meteoExiste(meteo)) == -1 ) {
				String req = "INSERT INTO `t_meteo` (`idMeteo`, `lieu`, `type`, `date`, `minimum`, `maximum`, `moyenne`) VALUES (NULL, ?,?,?,?,?,?)";
				PreparedStatement preparedStatement = this.connection.prepareStatement(req,PreparedStatement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, meteo.getLieu());
				preparedStatement.setString(2, meteo.getTemps().toString());
				preparedStatement.setDate(3, meteo.getDate());
				preparedStatement.setDouble(4, meteo.getMin());
				preparedStatement.setDouble(5, meteo.getMax());
				preparedStatement.setDouble(6, meteo.getMoy());
				preparedStatement.executeUpdate();
				ResultSet rs =preparedStatement.getGeneratedKeys();
				
				if (rs != null && rs.next()) {
				    key = rs.getInt(1);
				}
				preparedStatement.close();
			}else {
				this.majMeteo(meteo, idMeteo);
			}

		} catch (SQLException e) {
			System.out.println("Erreur Base.addMeteo " + e.getMessage());
		}
		System.out.println("Ajout d'une meteo");
		return key;
		
	}

	@Override
	public void supprimerMeteo(int id)throws RemoteException {
		try {

			String req = "DELETE FROM `t_meteo` WHERE t_meteo.idMeteo= ?;";
			PreparedStatement preparedStatement = this.connection.prepareStatement(req);
			preparedStatement.setInt(1, id);

			preparedStatement.executeUpdate();

			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("Erreur Base.addMeteo " + e.getMessage());
		}
		System.out.println("Supprimer une meteo");
	}

	@Override
	public int meteoExiste(Meteo meteo)throws RemoteException {
		try {

			String req = "SELECT * FROM `t_meteo`";
			PreparedStatement preparedStatement = this.connection.prepareStatement(req);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Meteo res = new Meteo();
				int idMeteo = resultSet.getInt("idMeteo");
				res.setLieu(resultSet.getString("lieu"));
				res.setTemps(Temps.valueOf(resultSet.getString("type")));
				res.setDate(resultSet.getDate("date"));
				if (meteo.equals(res)) {
					return idMeteo;
				}
			}
			resultSet.close();
			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("Erreur Base.getMeteo " + e.getMessage());
		}
		System.out.println("Recherche existence d'une meteo");
		return -1;
	}

	@Override
	public void majMeteo(Meteo meteo, int idMeteo)throws RemoteException {
		try {

			String req = "UPDATE `t_meteo` SET `lieu` = ?, `type` = ?, `date` = ? ,`minimum` = ?, `maximum` = ?, `moyenne` = ? WHERE `t_meteo`.`idMeteo` = ?";
			PreparedStatement preparedStatement = this.connection.prepareStatement(req);
			preparedStatement.setString(1, meteo.getLieu());
			preparedStatement.setString(2, meteo.getTemps().toString());
			preparedStatement.setDate(3, meteo.getDate());
			preparedStatement.setDouble(4, meteo.getMin());
			preparedStatement.setDouble(5, meteo.getMax());
			preparedStatement.setDouble(6, meteo.getMoy());
			preparedStatement.setInt(7, idMeteo);

			preparedStatement.executeUpdate();

			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("Erreur Base.updateMeteo " + e.getMessage());
		}
		System.out.println("Mise a jour d'une meteo");
	}

	@Override
	public void ajouterMeteos(Meteo meteo, Date dateFin)throws RemoteException {
		Calendar calendar = Calendar.getInstance();
		
		for (Date date = meteo.getDate(); !date.equals(dateFin);) {
			
			this.ajouterMeteo(meteo);
			
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);
			date = new Date(calendar.getTimeInMillis());
			meteo.setDate(date);
		}
		System.out.println("Ajout de plusieurs meteos");
	}		
	
	
	
	
	
	@Override
	public void ajouterImage(int idMeteo,Image image) throws RemoteException {
		try {	
			
				//table image
				String req = "INSERT INTO `t_image` (`idImage`,`image`) VALUES (NULL, ?)";
				PreparedStatement preparedStatement = this.connection.prepareStatement(req,PreparedStatement.RETURN_GENERATED_KEYS);
				InputStream flux = new ByteArrayInputStream(image.getImage());
				preparedStatement.setBinaryStream(1,flux );
				preparedStatement.executeUpdate();
				ResultSet rs =preparedStatement.getGeneratedKeys();
				int key=-1;
				if (rs != null && rs.next()) {
				    key = rs.getInt(1);
				}
				
				this.ajouterJointure(idMeteo, key);		

		} catch (SQLException e) {
			
		}
		System.out.println("Ajout d'une image");
	}	

	
	private void ajouterJointure(int idMeteo,int key) {
		try {	
			//table de jointure
			String req = "INSERT INTO `t_meteo_image` (`idMeteoImage`,`idMeteo`,`idImage`) VALUES (NULL, ?, ?)";
			PreparedStatement preparedStatement = this.connection.prepareStatement(req);
			preparedStatement.setInt(1, idMeteo);
			preparedStatement.setInt(2, key);
			preparedStatement.executeUpdate();				
		
			preparedStatement.close();
		} catch (SQLException e) {
			
		}
		System.out.println("Ajout jointure");
	}

	@Override
	public int supprimerImage(int idImage) throws RemoteException {
		int key=-1;
		try {

			String req = "DELETE FROM `t_image` WHERE t_image.idImage= ?;";
			PreparedStatement preparedStatement = this.connection.prepareStatement(req,PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idImage);

			preparedStatement.executeUpdate();
			ResultSet rs =preparedStatement.getGeneratedKeys();
			
			if (rs != null && rs.next()) {
			    key = rs.getInt(1);
			}
			preparedStatement.close();
			this.supprimerJointure(key);
		} catch (SQLException e) {
			System.out.println("Erreur serveur.SupprimerImage " + e.getMessage());
		}
		System.out.println("Supprimer une meteo");
		
		return key;
	}
	
	private void supprimerJointure(int id) {
		try {
			String req = "DELETE FROM `t_meteo_image` WHERE t_meteo_image.idImage= ?;";
			PreparedStatement preparedStatement = this.connection.prepareStatement(req);
			preparedStatement.setInt(1, id);

			preparedStatement.executeUpdate();

			preparedStatement.close();		
			
		} catch (SQLException e) {
			System.out.println("Erreur serveur.supprimerJointure " + e.getMessage());
		}

		
	}

	@Override
	public List<Image> getlisteImage(int id) throws RemoteException {
		
		List<Image> images=new ArrayList<Image>();
		try {		
			String req = "select t_image.image,t_image.idImage from t_meteo_image,t_image where t_image.idImage=t_meteo_image.idImage AND t_meteo_image.idMeteo=?";
			PreparedStatement preparedStatement = this.connection.prepareStatement(req);

			preparedStatement.setInt(1,id);
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				System.out.println("image");
				byte[] image =rs.getBytes("image");
				int idImage=rs.getInt("idImage");
				Image img=new Image();
				img.setIdImage(idImage);
				img.setImage(image);
				System.out.println(img.toString());
				images.add(img);
			}

			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("Erreur serveur.listeImages " + e.getMessage());
		}
		System.out.println("Recuperation de la liste d'image d'une meteo :"+images.size()+" element(s)");
		return images;
	}

	

	@Override
	public void ajouterListeMeteo(List<Meteo> listeMeteo) throws RemoteException {
		for (int i=0;i<listeMeteo.size();i++) {
			this.ajouterMeteo(listeMeteo.get(i));
		}
		System.out.println("Ajout d'une liste de meteo : "+listeMeteo.size()+" element(s)");
	}

	@Override
	public byte[] generationPdf( int[] idMeteos) throws RemoteException {
		PDDocument document = new PDDocument();
		PDPage nouvellePage = new PDPage(PDRectangle.A4);
		System.out.println(PDRectangle.A4.getWidth()+" "+PDRectangle.A4.getHeight());
		byte[] pdf = null;	
		
		try {			
			
			PDPageContentStream contentStream = new PDPageContentStream(document, nouvellePage,PDPageContentStream.AppendMode.APPEND,true,true);

		    contentStream.beginText();	     
		    contentStream.setFont(PDType1Font.TIMES_ROMAN, TAILLE_POLICE_TITRE);
		    int positionY=BORDURE_HAUT;
		    contentStream.newLineAtOffset(PAS_TITRE_X, positionY);
		    String titre = "DonnÈes MÈtÈo";		    	
	    	contentStream.showText(titre);
	    	System.out.println("titre");
	    	
	    	contentStream.setFont(PDType1Font.TIMES_ROMAN, TAILLE_POLICE_DONNEES);
	    	
	    	contentStream.newLineAtOffset(-(PAS_TITRE_X)+BORDURE_GAUCHE, -PAS_TITRE_Y);
	    	positionY-=PAS_TITRE_Y;
		    
		    List<Meteo> listeMeteo=new ArrayList<Meteo>();
		    for(int i=0;i<idMeteos.length;i++) {
		    	listeMeteo.add(this.getMeteo(idMeteos[i]));
		    }
		    for(int i=0;i<listeMeteo.size();i++) {
		    	if( positionY<(BORDURE_BAS+(2*PAS_DONNEES_Y))) {
		    		contentStream.endText();
		    		contentStream.close();
		    		document.addPage(nouvellePage);
		    		nouvellePage = new PDPage(PDRectangle.A4);
		    		contentStream = new PDPageContentStream(document, nouvellePage,PDPageContentStream.AppendMode.APPEND,true,true);
		    		contentStream.beginText();	     
				    contentStream.setFont(PDType1Font.TIMES_ROMAN, TAILLE_POLICE_DONNEES);
				    positionY=BORDURE_HAUT;
				    contentStream.newLineAtOffset(BORDURE_GAUCHE, positionY);
		    	}
		    	
		    	Meteo m=listeMeteo.get(i);
		    	String ligne1 = m.getLieu()+"          ";
		    	ligne1 +=m.getDate().toString()+"         "+m.getTemps().toString();
		    
		    	String ligne2="Minimum: "+m.getMin()+" ∞c         ";
		    	ligne2 +="Maximum: "+m.getMax()+" ∞c           ";
		    	ligne2 +="Moyenne:"+m.getMoy()+" ∞c           ";
		    	
		    	contentStream.showText(ligne1);
		    	contentStream.newLineAtOffset(0,-PAS_DONNEES_Y);
		    	contentStream.showText(ligne2);		    	
		    	contentStream.newLineAtOffset(0,-(PAS_DONNEES_Y+PAS_BLANC));
		    	
		    	positionY-=(PAS_DONNEES_Y*2)+PAS_BLANC;
		    	
		    	List<Image> listeImage=this.getlisteImage(m.getIdMeteo());
		    	PDPageContentStream contentImage = new PDPageContentStream(document, nouvellePage,PDPageContentStream.AppendMode.APPEND,true,true);
		    	for(int y=0;y<listeImage.size();y++) {		    		
		    		
		    		PDImageXObject image = JPEGFactory.createFromByteArray(document,listeImage.get(y).getImage());
		    		PDImageXObject image2 = LosslessFactory.createFromImage(document, image.getImage());
		    		
		    		System.out.println("test 1 : "+image2.getHeight()+" X "+image2.getWidth());
		    		
		    		double coef=this.coeficientReductionImage(image2.getWidth(), image2.getHeight());		    		
		    		
		    		contentImage.drawImage(image2, BORDURE_GAUCHE+(PAS_IMAGE_X*y),positionY-PAS_BLANC,(int)(image2.getWidth()/coef),(int)(image2.getHeight()/coef));

		    	}
		    	if(listeImage.size()>0) {
		    		contentStream.newLine();		    	
		    		contentStream.newLineAtOffset(0, -PAS_IMAGE_Y);
		    		positionY-=PAS_IMAGE_Y;
		    	}else {
		    		contentStream.newLine();		    	
		    		contentStream.newLineAtOffset(0, -PAS_BLANC);
		    		positionY-=PAS_BLANC;
		    	}
		    	contentImage.close();
		    	
		    }
		    
		      

		    contentStream.endText();
		    System.out.println("Content added");
		    contentStream.close();
		      
		      
		} catch (IOException e) {
			System.out.println("Erreur PDf");
			e.printStackTrace();
		}
	      
		 
		 System.out.println("document :"+document.getNumberOfPages());
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
		 try {			
			document.save(out);           
			pdf=out.toByteArray();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		 
		try {
			document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pdf;
	}
	
	private float  coeficientReductionImage(int larg, int haut) {
		if(larg>haut) {
			return larg/LARGEUR_MAX;
		}else {
			return haut/HAUTEUR_MAX;
		}
	}
	
public static void main(String [] args) {
		
		int port = 3000;
		
		Registry registry = null;
		
		try {
			LocateRegistry.createRegistry(port);
			registry = LocateRegistry.getRegistry(port);
		}
		catch (Exception e) {
			System.out.println("Erreur createRegistry");
		}
		
		ServeurRmi_Impl si = new ServeurRmi_Impl();
		ServeurRmi serveurRMI = null;
		
		try {
			serveurRMI =
					(ServeurRmi)UnicastRemoteObject.
						exportObject(si,0);
		}
		catch (Exception e) {
			System.out.println("Erreur exportObject");
		}
		
		try {
			registry.rebind("serveurRmi", serveurRMI);
		}
		catch (Exception e) {
			System.out.println("Erreur rebind");
		}

		
		System.out.println("Serveur RMI lanc√©");
	}


	

}
