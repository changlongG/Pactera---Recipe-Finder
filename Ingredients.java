package recipesfinder;

/**
 * class Ingredients representing ingredients, an InnerClass of Recipes
 */
public class Ingredients {

	private String item;
	private int amount;
	private EnumUnit unit;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit.toString();
	}

	public void setUnit(String units) {
		for (EnumUnit e : EnumUnit.values()) {
			if (e.toString().equals(units)) {
				this.unit = e;
				break;
			}
		}
	}
}
