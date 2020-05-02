package no.hvl.dat107;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class AvdelingDAO {

	private EntityManagerFactory firma;

	public AvdelingDAO() {
		firma = Persistence.createEntityManagerFactory("ansattUnit");
	}
	
	public Avdeling finnAvdelingMedID(int avdeling_id) {

		EntityManager em = firma.createEntityManager();

		Avdeling a;
		try {
			a = em.find(Avdeling.class, avdeling_id);

		} finally {
			em.close();
		}
		
		return a;
	}
	
	public Avdeling finnAvdelingMedNavn(String avdeling_navn) {

		String queryString = "SELECT a FROM Ansatt a WHERE a.brukernavn = :navn";

		EntityManager em = firma.createEntityManager();

		Avdeling a = null;
		try {
			TypedQuery<Avdeling> query = em.createQuery(queryString, Avdeling.class);
			query.setParameter("navn", avdeling_navn);
			a = query.getSingleResult();

		} catch (NoResultException e) {
			
		} finally {
			em.close();
		}

		return a;
	}
	
	public List<Ansatt> alleAnsatteVedAvdeling(String avdeling_navn) {
		String queryString = "SELECT a FROM Ansatt a WHERE a.avdeling = :navn";
		
		EntityManager em = firma.createEntityManager();
		List<Ansatt> a = null;

		try {
			TypedQuery<Ansatt> query = em.createQuery(queryString, Ansatt.class);
			query.setParameter("navn", avdeling_navn);
			a = query.getResultList();
		} catch (NoResultException e) {
			
		} finally {
			em.close();
		}

		return a;
	}
	
	public void oppdaterAvdelingForAnsatt(Ansatt ansatt) {
		EntityManager em = firma.createEntityManager();
		EntityTransaction input = em.getTransaction();

		try {
			input.begin();
			
			em.merge(ansatt);
			
			input.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			if (input.isActive()) {
				input.rollback();
			}
		} finally {
			em.close();
		}
	}
	
	public void leggTilNyAvdeling(Avdeling avdeling) {
		EntityManager em = firma.createEntityManager();
		EntityTransaction input = em.getTransaction();
		

		try {
			input.begin();
			
			em.persist(avdeling);
			
			input.commit();

		} catch (Throwable e) {
			e.printStackTrace();
			if (input.isActive()) {
				input.rollback();
			}
		} finally {
			em.close();
		}
	}
}
