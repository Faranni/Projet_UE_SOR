package interfaceRmi;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import beans.meteo.Image;
import beans.meteo.Meteo;
import beans.utilisateur.Utilisateur;

public interface ServeurRmi extends Remote {

	public boolean ouvrir()throws RemoteException;

	public void fermer()throws RemoteException;
	public boolean identification(Utilisateur utilisateur)throws RemoteException;

	public boolean dejaInscrit(Utilisateur utilisateur)throws RemoteException ;

	public void inscription(Utilisateur utilisateur)throws RemoteException;
	

	public List<Meteo> getMeteo()throws RemoteException; 

	public int ajouterMeteo(Meteo meteo)throws RemoteException;

	public void supprimerMeteo(int id)throws RemoteException;

	public int meteoExiste(Meteo meteo) throws RemoteException;

	public void majMeteo(Meteo meteo, int idMeteo) throws RemoteException;

	public void ajouterMeteos(Meteo meteo, Date dateFin)throws RemoteException;

	public void ajouterImage( int idMeteo,Image image)throws RemoteException;
	
	public int supprimerImage( int idImage)throws RemoteException;

	public List<Image> getlisteImage(int id)throws RemoteException;
	
	public void ajouterListeMeteo(List<Meteo> listeMeteo) throws RemoteException;

	public Meteo getMeteo(int idMeteo) throws RemoteException;

	public byte[] generationPdf(int[] idMeteos) throws RemoteException;

}
