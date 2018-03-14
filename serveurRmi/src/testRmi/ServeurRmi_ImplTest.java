package testRmi;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.util.ArrayList;
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
	
	public Registry registry ;
	public int port ;
	public ServeurRmi serveur;
	
	@BeforeEach
	public  void init() {		
		try {
			this.port = 3000;
			this.registry =	LocateRegistry.getRegistry(this.port);			
			this.serveur =(ServeurRmi) registry.lookup("serveurRmi");
			this.serveur.ouvrir();			
	}
		catch (Exception e) {
			System.out.println("Erreur client RMI");
		}
	}

	@AfterEach
	public void fin() {
		try {
			serveur.fermer();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	


	/**
	 * Test communication avec le serveur rmi : Meteo
	 * ajouterMeteo()
	 * getMeteo()
	 * meteoExiste()
	 * supprimerMeteo()
	 */
	@Test
	void testMeteo() {
		
		Meteo meteo = new Meteo();
		meteo.setLieu("Brest");
		meteo.setTemps(Temps.valueOf("NUAGE"));
		meteo.setDate("10/2/2018");
		double min =(int)(Math.random() * 30);
		double moy =(int)(Math.random() * 60);
		double max =(int)(Math.random() * 100);
		meteo.setMin(min);
		meteo.setMax(max);
		meteo.setMoy(moy);		
		try {
			int id =this.serveur.ajouterMeteo(meteo);
			System.err.println("id:"+id);
			assertTrue(id!=-1);
			
			List<Meteo> meteoBis=this.serveur.getMeteo();
			for(int i=0;i<meteoBis.size();i++) {
				Meteo m=meteoBis.get(i);
				if(m.getIdMeteo()==id) {
					assertTrue(m.getDate().toString().equals("2018-02-10"));
					assertTrue(m.getLieu().equals("Brest"));
					assertTrue(m.getMax()==max);
					assertTrue(m.getMin()==min);
					assertTrue(m.getMoy()==moy);
				}
			}
			assertTrue(this.serveur.meteoExiste(meteo)==id);
			
			meteo.setMax(100.0);
			meteo.setMin(0.0);
			this.serveur.majMeteo(meteo, id);
			
			meteoBis=this.serveur.getMeteo();
			for(int i=0;i<meteoBis.size();i++) {
				Meteo m=meteoBis.get(i);
				if(m.getIdMeteo()==id) {
					assertTrue(m.getDate().toString().equals("2018-02-10"));
					assertTrue(m.getLieu().equals("Brest"));
					assertTrue(m.getMax()==100.0);
					assertTrue(m.getMin()==0.0);
					assertTrue(m.getMoy()==moy);
				}
			}
			
			serveur.supprimerMeteo(id);
			
			meteoBis=this.serveur.getMeteo();
			
			for(int i=0;i<meteoBis.size();i++) {
				Meteo m=meteoBis.get(i);
				assertTrue(m.getIdMeteo()!=id);
			}
			assertTrue(this.serveur.meteoExiste(meteo)==-1);
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Erreur ");
		}		
	}
	
	/**
	 * Test communication avec le serveur rmi : Meteo
	 * ajouterMeteos()
	 * getMeteo()
	 * meteoExiste()
	 * supprimerMeteo()
	 */
	@Test
	void testMeteos() {
		Meteo meteo = new Meteo();
		meteo.setLieu("Brest");
		meteo.setTemps(Temps.valueOf("NUAGE"));
		meteo.setDate("25/2/2018");
		Date dateFin= meteo.getDate();
		meteo.setDate("20/2/2018");
		
		double min =(int)(Math.random() * 30);
		double moy =(int)(Math.random() * 60);
		double max =(int)(Math.random() * 100);
		meteo.setMin(min);
		meteo.setMax(max);
		meteo.setMoy(moy);		
		try {
			this.serveur.ajouterMeteos(meteo,dateFin);
			List<Integer> listeId=new ArrayList<Integer>();
			Meteo meteoBis=meteo;
			for(int i=0;i<6;i++) {
				meteoBis.setDate("2"+i+"/2/2018");
				listeId.add(this.serveur.meteoExiste(meteoBis));
			}
			List<Meteo> meteos=this.serveur.getMeteo();
			for(int i=0;i<meteos.size();i++) {
				Meteo m=meteos.get(i);
				for(int y=0;y<listeId.size();y++) {
					if(m.getIdMeteo()==listeId.get(y)) {
						assertTrue(m.getDate().toString().equals("2018-02-2"+i));
						assertTrue(m.getLieu().equals("Brest"));
						assertTrue(m.getMax()==max);
						assertTrue(m.getMin()==min);
						assertTrue(m.getMoy()==moy);
					}	
				}										
			}
			
			for(int i=0;i<listeId.size();i++) {
				this.serveur.supprimerMeteo(listeId.get(i));
			}
			meteoBis=meteo;
			for(int i=0;i<listeId.size();i++) {
				meteoBis.setDate("2"+i+"/2/2018");
				assertTrue(this.serveur.meteoExiste(meteo)==-1);
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
			fail("Erreur ");
		}
	}

	/*@Test
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
