package no.hvl.dat107;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;


public class AnsattDAO {
	
	private EntityManagerFactory firma;

	public AnsattDAO() {
		firma = Persistence.createEntityManagerFactory("ansattUnit");
	}
	
	public Ansatt finnAnsattMedID(int ansatt_id) {

		EntityManager em = firma.createEntityManager();

		Ansatt a;
		try {
			a = em.find(Ansatt.class, ansatt_id);

		} finally {
			em.close();
		}
		
		return a;
	}
	
	public Ansatt finnAnsattMedBrukernavn(String brukernavn) {

		EntityManager em = firma.createEntityManager();
		Ansatt ansatt = null;
		
		try {
			TypedQuery<Ansatt> query = em.createQuery("SELECT t FROM Ansatt t WHERE t.brukernavn = :brukernavn", Ansatt.class);
			query.setParameter("brukernavn", brukernavn);
			ansatt = query.getSingleResult();

		} catch (NoResultException e) {
			
		} finally {
			em.close();
		}

		return ansatt;
	}
	
	public boolean leggTilAnsatt(String brukernavn, String fornavn, String etternavn, LocalDate ansatt_dato,
			String stilling, int manedslonn, String avdeling) {

		EntityManager em = firma.createEntityManager();
		EntityTransaction input = em.getTransaction();

		boolean done = false;
		try {
			input.begin();
			
			Ansatt a = new Ansatt(brukernavn, fornavn, etternavn, ansatt_dato, stilling, manedslonn, avdeling);
			
			em.persist(a);

			input.commit();
			
			done = true;
			
		} catch (NoResultException e) {
			
		} catch(RollbackException e) {

		} catch(Exception e) {

			e.printStackTrace();
			input.rollback();

		} finally {
			em.close();
		}
		
		return done;
	}
	
	public void skrivUtAnsatte() {

		EntityManager em = firma.createEntityManager();

		List<Ansatt> ansatte;
		try {
			TypedQuery<Ansatt> query = em.createQuery("SELECT t FROM Ansatt t", Ansatt.class);
			ansatte = query.getResultList();
			for(Ansatt a : ansatte) {
				System.out.println(a);
			}
			
		} finally {
			em.close();
		}
	}
	public void oppdaterAnsatt(Ansatt ansatt) {
		
		EntityManager em = firma.createEntityManager();
		EntityTransaction input = em.getTransaction();
		try {
			input.begin();

			em.merge(ansatt);

			input.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			input.rollback();
		} finally {
			em.close();
		}
	}
}
