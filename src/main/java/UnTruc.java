
public class UnTruc implements UnTrucInterface {

	private String texte = "Hello !!!";

	public void faireUnTruc() {
		System.out.println(texte);
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

}
