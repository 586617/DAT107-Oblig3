package no.hvl.dat107;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import java.util.Iterator;


public class Main {

	public static void main(String[] args) {
		boolean ferdig = false;

		while(!ferdig) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Meny:");
			System.out.println("0  = Avslutt");
			System.out.println("1  = Søk etter ansatt med ID eller brukernavn");
			System.out.println("2  = Skriv ut alle ansatte");
			System.out.println("3  = Oppdater en ansatt lønn/stilling");
			System.out.println("4  = Legg til ansatt");
			System.out.println("5  = Søk etter avdeling med ID eller navn");
			System.out.println("6  = Oppdater en ansatt avdeling");
			System.out.println("7  = Skriv ut alle ved avdelingen");
			System.out.println("8  = Legg til avdeling");
			

			switch(scanner.nextLine()) {

				case "0": {
					ferdig = true;
					break;
				}
				case "1": {
					System.out.println("Søk etter ansatt med ID eller brukernavn:");
					Ansatt funnet = finnAnsatt(scanner);
					if(funnet == null) {
						System.out.println("Fant ikke ansatte");
					}
					System.out.println(funnet);
					break;
				}
				case "2": {
					AnsattDAO ansatt = new AnsattDAO();
					ansatt.skrivUtAnsatte();
					break;
				}
				case "3": {
					System.out.println("Søk etter ansatt med ID eller brukernavn:");
					AnsattDAO ansatt = new AnsattDAO();
					Ansatt funnet = finnAnsatt(scanner);
					if(funnet == null) {
						System.out.println("Fant ikke ansatte");
						break;
					}
					System.out.println("1 : Endre lønn");
					System.out.println("2 : Endre stilling");
					System.out.println("3 : Endre lønn og stilling");
					switch(scanner.nextLine()) {
						case "1": {
							System.out.println("Skriv inn månedslønn:");
							funnet.setManedslonn(Integer.valueOf(scanner.nextLine()));
							ansatt.oppdaterAnsatt(funnet);
							break;
						}
						case "2": {
							System.out.println("Skriv inn stilling:");
							funnet.setStilling(scanner.nextLine());
							ansatt.oppdaterAnsatt(funnet);
							break;
						}
						case "3": {
							System.out.println("Skriv inn månedslønn:");
							funnet.setManedslonn(Integer.valueOf(scanner.nextLine()));
							System.out.println("Skriv inn stilling:");
							funnet.setStilling(scanner.nextLine());
							ansatt.oppdaterAnsatt(funnet);
							break;
						}
					}
					
					break;
				}
				case "4": {
					System.out.println("Oppgi brukernavn, maks 4 bokstaver:");
					String brukernavn = scanner.nextLine();
					System.out.println("Oppgi fornavn:");
					String fornavn = scanner.nextLine();
					System.out.println("Oppgi etternavn:");
					String etternavn = scanner.nextLine();
					System.out.println("Skriv inn ansettelsesdato, ÅÅÅÅ-MM-DD:");
					LocalDate ansattDato = LocalDate.parse(scanner.nextLine());
					System.out.println("Oppgi stilling:");
					String stilling = scanner.nextLine();
					System.out.println("Oppgi månedslønn:");
					int mdlonn = Integer.valueOf(scanner.nextLine());
					System.out.println("Oppgi avdeling:");
					String avdeling = scanner.nextLine();

					AnsattDAO ansatt = new AnsattDAO();
					ansatt.leggTilAnsatt(brukernavn, fornavn, etternavn, ansattDato, stilling, mdlonn, avdeling);
					ansatt.skrivUtAnsatte();
					break;
				}
				case "5": {
					System.out.println("Søk etter avdeling med ID eller navn:");
					Avdeling funnet = finnAvdeling(scanner);
					if(funnet == null) {
						System.out.println("Fant ikke den ansatte");
					}
					System.out.println(funnet);
					break;
				}
				case "6": {
					System.out.print("Søk etter ansatt med ID eller brukernavn:");
					AnsattDAO ansatt = new AnsattDAO();
					Ansatt funnet = finnAnsatt(scanner);
					
					if(funnet == null) {
						System.out.print("Fant ikke ansatt");
						break;
					}
					
					if (!Sjef(funnet)) {
						System.out.println("Velg ny avdeling med ID:");
						int nyAvdeling = Integer.valueOf(scanner.nextLine());
						
						oppdaterAvdelingForAnsatt(funnet, nyAvdeling);
					} else {
						System.out.println("Kan ikke oppdatere avdeling fordi " + funnet.getBrukernavn() + " er sjef på en annen avdeling");
					}
					break;
				}
				case "7": {
					System.out.println("Søk opp avdeling med ID:");
					skrivAlleVedAvdeling(scanner);
					break;
				}
				case "8": {
					System.out.println("Skriv inn ID eller brukernavn på sjef i den nye avdelingen");
					Ansatt funnet = finnAnsatt(scanner);
					if(funnet == null) {
						System.out.println("Fant ikke den ansatte");
						break;
					}
					
					if (!Sjef(funnet)) {
						System.out.println("Skriv inn navn på ny avdeling");
						String avdelingsNavn = scanner.nextLine();
						leggTilAvdeling(avdelingsNavn, funnet.getAnsatt_id());
						
					} else {
						System.out.println("Kan ikke oppdatere avdeling fordi " + funnet.getBrukernavn() + " er sjef på en annen avdeling.");
					}
					break;
				}	
			}
		}
	}


	public static Ansatt finnAnsatt(Scanner sc) {
		String idornavn = sc.nextLine();
		AnsattDAO ansatt = new AnsattDAO();
		try {
			int id = Integer.parseInt(idornavn);
			return ansatt.finnAnsattMedID(id);
		} catch(NumberFormatException e) {
			return ansatt.finnAnsattMedBrukernavn(idornavn);
		}

	}
	
	public static Avdeling finnAvdeling(Scanner sc) {
		String input = sc.nextLine();
		AvdelingDAO avdeling = new AvdelingDAO();
		try {
			int id = Integer.parseInt(input);
			return avdeling.finnAvdelingMedID(id);
		} catch(NumberFormatException e) {
			return avdeling.finnAvdelingMedNavn(input);
		}
	}
	
	public static void skrivAlleVedAvdeling(Scanner sc) {
		AvdelingDAO avdeling = new AvdelingDAO();
		String avdelingNavn = finnAvdeling(sc).getAvdeling_navn();
		
		List<Ansatt> ansatte = avdeling.alleAnsatteVedAvdeling(avdelingNavn);
		Iterator<Ansatt> ansattopprams = ansatte.iterator();
		while (ansattopprams.hasNext()) {
			Ansatt ansatt = ansattopprams.next();
			if (Sjef(ansatt)) {
				System.out.println("'" + ansatt + "'");
			} else {
				System.out.println(ansatt);
			}
		}
	}
	private static boolean Sjef(Ansatt a) {
		boolean sjef = false;
		AvdelingDAO avdeling = new AvdelingDAO();
		Avdeling avd = avdeling.finnAvdelingMedNavn(a.getAvdeling());

		if (avd.getSjef_id() == a.getAnsatt_id() && avd != null) {
			sjef = true;
		}

		return sjef;
	}
	
	public static void oppdaterAvdelingForAnsatt(Ansatt a, int nyAvdelingId) {
		
		if (!Sjef(a)) {	
			String avdelingNavn = finnAvdelingmedID(nyAvdelingId).getAvdeling_navn();
			
			a.setAvdeling(avdelingNavn);
			AvdelingDAO avdeling = new AvdelingDAO();
			avdeling.oppdaterAvdelingForAnsatt(a);
			System.out.println("Ansatt " + a.getAnsatt_id() + ", " + a.getBrukernavn() + " har fått ny avdeling: "
					+ avdeling.finnAvdelingMedID(nyAvdelingId));
		} else {
			System.out.println(
					"Kan ikke oppdatere avdeling fordi " + a.getBrukernavn() + " er sjef på en annen avdeling.");
		}
	}
	
	public static Avdeling finnAvdelingmedID(int avdelingid) {
		AvdelingDAO avdeling = new AvdelingDAO();
		return avdeling.finnAvdelingMedID(avdelingid);
	}
	
	public static void leggTilAvdeling(String avdelingsnavn, int sjefid) {
		try {
			AnsattDAO ansatt = new AnsattDAO();
			Ansatt a = ansatt.finnAnsattMedID(sjefid);
			if (!Sjef(a) && a != null) {
				Avdeling avd = new Avdeling(avdelingsnavn, sjefid);
				AvdelingDAO avdeling = new AvdelingDAO();
				avdeling.leggTilNyAvdeling(avd);
				
				a.setStilling("CFO");
				ansatt.oppdaterAnsatt(a);

				a.setAvdeling(avd.getAvdeling_navn());
				avdeling.oppdaterAvdelingForAnsatt(a);
				
				System.out.println("Ansatt " + a.getAnsatt_id() + ", " + a.getBrukernavn() + " er nå sjef i den nye avdelingen: " + avdelingsnavn);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}
