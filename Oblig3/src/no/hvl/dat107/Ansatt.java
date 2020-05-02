package no.hvl.dat107;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ansatt", schema = "oblig3")
public class Ansatt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ansatt_id;
	
	private String brukernavn;
	private String fornavn;
	private String etternavn;
	private LocalDate ansatt_dato;
	private String stilling;
	private int manedslonn;
	private String avdeling;
	
	public Ansatt(String brukernavn, String fornavn, String etternavn, LocalDate ansatt_dato, String stilling,
			int manedslonn, String avdeling) {
		this.brukernavn = brukernavn;
		this.fornavn = fornavn;
		this.etternavn = etternavn;
		this.ansatt_dato = ansatt_dato;
		this.stilling = stilling;
		this.manedslonn = manedslonn;
		this.avdeling = avdeling;
	}
	
	public int getAnsatt_id() {
		return ansatt_id;
	}
	
	public void setAnsatt_id(int ansatt_id) {
		this.ansatt_id = ansatt_id;
	}
	
	public String getBrukernavn() {
		return brukernavn;
	}
	
	public void setBrukernavn(String brukernavn) {
		this.brukernavn = brukernavn;
	}
	
	public String getFornavn() {
		return fornavn;
	}
	
	public void setFornavn(String fornavn) {
		this.fornavn = fornavn;
	}
	
	public String getEtternavn() {
		return etternavn;
	}
	
	public void setEtternavn(String etternavn) {
		this.etternavn = etternavn;
	}
	
	public LocalDate getAnsatt_dato() {
		return ansatt_dato;
	}
	
	public void setAnsatt_dato(LocalDate ansatt_dato) {
		this.ansatt_dato = ansatt_dato;
	}
	
	public String getStilling() {
		return stilling;
	}
	
	public void setStilling(String stilling) {
		this.stilling = stilling;
	}
	
	public int getManedslonn() {
		return manedslonn;
	}
	
	public void setManedslonn(int manedslonn) {
		this.manedslonn = manedslonn;
	}
	
	public String getAvdeling() {
		return avdeling;
	}
	
	public void setAvdeling(String avdeling) {
		this.avdeling = avdeling;
	}
	
	public String skrivUt() {
		return "Ansatt {ID : " + this.ansatt_id + " | Brukernavn : " + this.brukernavn + " | Navn : " + this.fornavn + " " + this.etternavn + " | Ansettelsedato : " + this.ansatt_dato + " | Stilling : " + this.stilling + " | Månedslønn : " + this.manedslonn
				+ " | Avdeling : " + this.avdeling + " }";
	}
}
