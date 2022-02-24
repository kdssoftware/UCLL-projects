
public class Menu {
	String naam;
	Pizza pizza1, pizza2, pizza3;

	public Menu(String naam, Pizza pizza1, Pizza pizza2, Pizza pizza3){
		this.naam = naam;
		this.pizza1 = pizza1;
		this.pizza2 = pizza2;
		this.pizza3 = pizza3;
	}
	
	public String getInfo(){
		return naam + ": \n" + pizza1.getInfo() + "\n" + pizza2.getInfo() + "\n" + pizza3.getInfo();
	}
}
