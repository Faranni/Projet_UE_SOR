package serveur;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import beans.meteo.Meteo;
import beans.meteo.Temps;
import beans.utilisateur.Utilisateur;
import interfaceRmi.ServeurRmi;

public class ServeurRmi_Impl implements ServeurRmi {
	Connection connection = null;

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
			return true;
		} catch (Exception e) {
			System.out.println("Erreur Base.ouvrir " + e.getMessage());
			return false;
		}
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
		
	}

	@Override
	public void enregistrerImage(int idImage, String nomFichier)throws RemoteException {
		try {
			String req = "SELECT image FROM t_image WHERE idImage=?";

			PreparedStatement preparedStatement = connection.prepareStatement(req);
			preparedStatement.setInt(1, idImage);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			File file = new File(nomFichier);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(file);

			System.out.println("Writing to file " + file.getAbsolutePath());
			while (resultSet.next()) {
				InputStream input = resultSet.getBinaryStream("image");
				byte[] buffer = new byte[1024];
				while (input.read(buffer) > 0) {
					fileOutputStream.write(buffer);
				}
			}
			fileOutputStream.close();

	} catch (SQLException | IOException e) {
		System.out.println(e.getMessage());
	}

		
	}

	@Override
	public List<Meteo> getMeteo() throws RemoteException{
		List<Meteo> meteos = new ArrayList<>();

		try {

			String req = "SELECT `lieu`,`type`,`date` FROM `t_meteo`";
			PreparedStatement preparedStatement = this.connection.prepareStatement(req);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Meteo meteo = new Meteo();
				meteo.setLieu(resultSet.getString("lieu"));
				meteo.setTemps(Temps.valueOf(resultSet.getString("type")));
				meteo.setDate(resultSet.getDate("date"));
				meteos.add(meteo);
			}
			resultSet.close();
			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("Erreur Base.getMeteo " + e.getMessage());
		}

		return meteos;
	}

	@Override
	public void ajouterMeteo(Meteo meteo)throws RemoteException {
		try {

			int idMeteo;
			if((idMeteo = this.meteoExiste(meteo)) == -1 ) {
				String req = "INSERT INTO `t_meteo` (`idMeteo`, `lieu`, `type`, `date`) VALUES (NULL, ?, ?, ?)";
				PreparedStatement preparedStatement = this.connection.prepareStatement(req);
				preparedStatement.setString(1, meteo.getLieu());
				preparedStatement.setString(2, meteo.getTemps().toString());
				preparedStatement.setDate(3, meteo.getDate());
				preparedStatement.executeUpdate();
				preparedStatement.close();
			}else {
				this.majMeteo(meteo, idMeteo);
			}

		} catch (SQLException e) {
			System.out.println("Erreur Base.addMeteo " + e.getMessage());
		}
		
	}

	@Override
	public void supprimerMeteo(Meteo meteo)throws RemoteException {
		try {

			String req = "DELETE FROM `t_meteo` WHERE `t_meteo`.`lieu` = ? and `t_meteo`.`type` = ? and `t_meteo`.`date` = ?;";
			PreparedStatement preparedStatement = this.connection.prepareStatement(req);
			preparedStatement.setString(1, meteo.getLieu());
			preparedStatement.setString(2, meteo.getTemps().toString());
			preparedStatement.setDate(3, meteo.getDate());

			preparedStatement.executeUpdate();

			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("Erreur Base.addMeteo " + e.getMessage());
		}
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
		return -1;
	}

	@Override
	public void majMeteo(Meteo meteo, int idMeteo)throws RemoteException {
		try {

			String req = "UPDATE `t_meteo` SET `lieu` = ?, `type` = ?, `date` = ? WHERE `t_meteo`.`idMeteo` = ?";
			PreparedStatement preparedStatement = this.connection.prepareStatement(req);
			preparedStatement.setString(1, meteo.getLieu());
			preparedStatement.setString(2, meteo.getTemps().toString());
			preparedStatement.setDate(3, meteo.getDate());
			preparedStatement.setInt(4, idMeteo);

			preparedStatement.executeUpdate();

			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("Erreur Base.updateMeteo " + e.getMessage());
		}
		
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

		
		System.out.println("Serveur RMI lancé");
	}

	@Override
	public int ajouterImage(InputStream inputStream) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int supprimerImage(int idImage) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getImageName(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
