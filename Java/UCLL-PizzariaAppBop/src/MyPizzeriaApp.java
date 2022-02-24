
public class MyPizzeriaApp {

	public static void main(String[] args) {
		Pizza pepperoni, hawai, vierKazen, foutePizza;
		
		try{
			
			 pepperoni = new Pizza("S", "Pepperoni", "ham", "kaas", "chorizzo", false,7.50);
			 hawai = new Pizza("M", "Hawai", "ananas", "ham", "tomaten", false,9.50);
			 vierKazen = new Pizza("L", "vierKazen", "mozarella", "blauwe kaas", "geitenkaas", true,12.00);
			 
			 foutePizza = new Pizza("M", "Hawai", "ananas", "ham", "tomaten", false,-2);
			 hawai.setPrijs(5);
				
			 Menu menu = new Menu("Lekker Menu!", pepperoni, hawai, foutePizza);
				
			 System.out.println(menu.getInfo());
		}
		catch(IllegalArgumentException fout){
			System.out.println("Er is een fout gebeurd: opgepast!"); 
		}

		
	}
	

}
