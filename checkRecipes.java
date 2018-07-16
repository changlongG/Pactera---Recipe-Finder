package recipesfinder;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class checkRecipes extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		RecipesFinder rf = new RecipesFinder();
		String recipesjson = req.getParameter("recipes");
		String fridge = req.getParameter("fridge");
		JsonParser parser = new JsonParser();
		// JsonArray jsonArray =
		// parser.parse(rf.getStrFromJson(recipesjson)).getAsJsonArray();
		Gson gson = new Gson();
		/*
		 * for (JsonElement user : jsonArray) { Recipes userBean =
		 * gson.fromJson(user, Recipes.class); rf.recipeslist.add(userBean); }
		 */
		System.out.println(recipesjson);
		return;
	}
}
