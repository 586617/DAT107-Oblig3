package no.hvl.dat107;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "avdeling", schema = "oblig3")
public class Avdeling {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int avdeling_id;
	
	private String avdeling_navn;
	private int sjef_id;
	
	private List<Ansatt> avdelingListe;
	
	public Avdeling(String avdeling_navn, int sjef_id) {
		
		this.avdeling_navn = avdeling_navn;
		this.sjef_id = sjef_id;
	}

	public int getAvdeling_id() {
		return avdeling_id;
	}

	public void setAvdeling_id(int avdeling_id) {
		this.avdeling_id = avdeling_id;
	}

	public String getAvdeling_navn() {
		return avdeling_navn;
	}

	public void setAvdeling_navn(String avdeling_navn) {
		this.avdeling_navn = avdeling_navn;
	}

	public int getSjef_id() {
		return sjef_id;
	}

	public void setSjef_id(int sjef_id) {
		this.sjef_id = sjef_id;
	}

	public List<Ansatt> getAvdelingListe() {
		return avdelingListe;
	}

	public void setAvdelingListe(List<Ansatt> avdelingListe) {
		this.avdelingListe = avdelingListe;
	}
	
	public void addAnsatt(Ansatt ansatt) {
		avdelingListe.add(ansatt);
	}

	public void removeAnsatt(Ansatt ansatt) {
		avdelingListe.remove(ansatt);
	}
	
}
