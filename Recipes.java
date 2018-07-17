package recipesfinder;

import java.util.List;

/**
 * class Recipes representing recipes from Json file, has an InnerClass
 * Ingredients
 */
public class Recipes {
	private String name;
	private List<Ingredients> ingredients;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ingredients> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredients> ingredients) {
		this.ingredients = ingredients;
	}

}
