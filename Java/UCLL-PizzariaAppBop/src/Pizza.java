
public class Pizza {
	private String grootte, naam, ingredient1, ingredient2, ingredient3;
	private boolean veggie;
	private double prijs;
	
	public Pizza(String grootte, String naam, String ingredient1, String ingredient2, String ingredient3, boolean veggie, double prijs){
		this.grootte = grootte;
		this.naam = naam;
		this.ingredient1 = ingredient1;
		this.ingredient2 = ingredient2;
		this.ingredient3 = ingredient3;
		this.veggie = veggie;
		setPrijs(prijs);
	}
	
	
	public void setPrijs(double prijs){
		if(prijs <= 0){
			throw new IllegalArgumentException("Prijs mag niet kleiner of gelijk zijn aan 0");
		}
		this.prijs = prijs;
	}
	
	public String getInfo(){
		return naam + " "+ grootte + ": " + ingredient1 + ", " + ingredient2 + ", " + ingredient3 + " - " + prijs;
	}
}
