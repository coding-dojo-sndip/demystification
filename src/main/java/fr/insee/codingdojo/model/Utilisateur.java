package fr.insee.codingdojo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Utilisateur {

	private String nom, prenom;
	private int qi;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	public Utilisateur() {
		
	}
	
	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public int getQi() {
		return qi;
	}
	public void setQi(int qi) {
		this.qi = qi;
	}

}
