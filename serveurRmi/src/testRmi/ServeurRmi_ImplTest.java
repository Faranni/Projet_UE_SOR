package testRmi;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.meteo.Meteo;
import beans.meteo.Temps;
import interfaceRmi.ServeurRmi;


class ServeurRmi_ImplTest {
	
	Registry registry ;
	int port ;
	ServeurRmi serveur;
	
	@Before
	public  void init() {		
		try {
			this.port = 3000;
			this.registry =	LocateRegistry.getRegistry(this.port);			
			this.serveur =(ServeurRmi) registry.lookup("serveurRmi");
			System.out.println(this.serveur.ouvrir());
			this.serveur.ouvrir();	
	}
		catch (Exception e) {
			System.out.println("Erreur client RMI");
		}
	}

	/*@After
	public void fin() {
		try {
			serveur.fermer();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	



	@Test
	void testAjouterMeteo() {
		
		Meteo meteo = new Meteo();
		meteo.setLieu("Brest");
		meteo.setTemps(Temps.valueOf("NUAGE"));
		meteo.setDate("10/2/2018");
		double min =(int)(Math.random() * 30);
		double moy =(int)(Math.random() * 60);
		double max =(int)(Math.random() * 100);
		meteo.setMin(min);
		meteo.setMax(moy);
		meteo.setMoy(max);		
		try {
			int id =this.serveur.ajouterMeteo(meteo);
			assertTrue(id!=-1);
			List<Meteo> meteoBis=this.serveur.getMeteo();
			for(int i=0;i<meteoBis.size();i++) {
				Meteo m=meteoBis.get(i);
				if(m.getIdMeteo()==id) {
					assertTrue(m.getDate().toString().equals("2018-10-2"));
					assertTrue(m.getLieu().equals("Brest"));
					assertTrue(m.getMax()==max);
					assertTrue(m.getMin()==min);
					assertTrue(m.getMoy()==moy);
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/*@Test
	void testSupprimerMeteo() {
		fail("Not yet implemented");
	}

	@Test
	void testMeteoExiste() {
		fail("Not yet implemented");
	}

	@Test
	void testMajMeteo() {
		fail("Not yet implemented");
	}

	@Test
	void testAjouterMeteos() {
		fail("Not yet implemented");
	}

	@Test
	void testAjouterImage() {
		fail("Not yet implemented");
	}

	@Test
	void testSupprimerImage() {
		fail("Not yet implemented");
	}

	@Test
	void testGetlisteImage() {
		fail("Not yet implemented");
	}

	@Test
	void testIdentification() {
		fail("Not yet implemented");
	}

	@Test
	void testDejaInscrit() {
		fail("Not yet implemented");
	}

	@Test
	void testInscription() {
		fail("Not yet implemented");
	}

	@Test
	void testEnregistrerImage() {
		fail("Not yet implemented");
	}
	*/



}
