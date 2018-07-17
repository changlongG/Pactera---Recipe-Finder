package recipesfinder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import com.csvreader.CsvReader;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Logic implementation class RecipesFinder
 */
public class RecipesFinder {
	static ArrayList<Fridge> fridgelist = new ArrayList<Fridge>();
	static ArrayList<Recipes> recipeslist = new ArrayList<Recipes>();
	static ArrayList<Fridge> ingredientlist = new ArrayList<Fridge>();
	static ArrayList<Recipes> resultlist = new ArrayList<Recipes>();

	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	static Date dateToday = new Date();

	public void setDate(Date d) {
		this.dateToday = d;
	}

	// Comparator for sorting by use-by(date)
	static Comparator<Fridge> comparator = new Comparator<Fridge>() {
		public int compare(Fridge s1, Fridge s2) {
			Date temp1 = new Date();
			Date temp2 = new Date();
			try {
				temp1 = parseDate(s1.getUseby());
				temp2 = parseDate(s2.getUseby());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (temp1.before(temp2)) {
				return -1;
			} else if (temp1.equals(temp2)) {
				return 0;
			} else {
				return 1;
			}

		}
	};

	public static Date parseDate(String s) throws ParseException {
		Date temp = sdf.parse(s);
		return temp;
	}

	// Parse Json file
	public static String getStrFromJson(String name) {
		String strData = null;
		try {
			InputStream inputStream = new FileInputStream(name);
			byte buf[] = new byte[1024];
			inputStream.read(buf);
			strData = new String(buf);
			strData = strData.trim();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return strData;

	}

	// Parse CSV file. saved as Arraylist(object) fridgelist
	public static void readCsvFile(String filePath) {
		try {
			ArrayList<String[]> csvList = new ArrayList<String[]>();
			CsvReader reader = new CsvReader(filePath, ',', Charset.forName("GBK"));

			while (reader.readRecord()) {
				csvList.add(reader.getValues());
			}
			reader.close();

			for (int row = 0; row < csvList.size(); row++) {
				Date temp1 = parseDate(csvList.get(row)[3]);
				if (temp1.after(dateToday)) {
					Fridge fridge = new Fridge();
					fridge.setItem(csvList.get(row)[0]);
					fridge.setAmount(Integer.parseInt(csvList.get(row)[1]));
					fridge.setUnit(csvList.get(row)[2]);
					fridge.setUseby(csvList.get(row)[3]);
					fridgelist.add(fridge);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Get final result
	public static String getResult() {
		String result = "";
		for (Recipes r : recipeslist) {
			int signal = 0;
			for (Ingredients i : r.getIngredients()) {
				for (Fridge f : fridgelist) {
					if (i.getItem().equals(f.getItem()) && i.getUnit().equals(f.getUnit())
							&& i.getAmount() <= f.getAmount()) {
						signal += 1;
						break;
					}
				}
			}
			if (signal == r.getIngredients().size()) {
				resultlist.add(r);
			}
		}
		for (Recipes r : resultlist) {
			for (Ingredients i : r.getIngredients()) {
				Fridge fridge = new Fridge();
				fridge.setItem(i.getItem());
				fridge.setAmount(i.getAmount());
				fridge.setUnit(i.getUnit());
				for (Fridge f : fridgelist) {
					if (i.getItem().equals(f.getItem())) {
						fridge.setUseby(f.getUseby());
					}
				}
				if (ingredientlist.isEmpty()) {
					ingredientlist.add(fridge);
				} else {
					int signal = 0;
					for (Fridge f1 : ingredientlist) {
						if (fridge.getItem().equals(f1.getItem())) {
							signal = 1;
							break;
						}
					}
					if (signal == 0) {
						ingredientlist.add(fridge);
					}
				}
			}
		}

		Collections.sort(ingredientlist, comparator);
		if (resultlist.size() == 1) {
			result = resultlist.get(0).getName();
		} else if (resultlist.size() == 0) {
			result = "Order Takeout";
		} else {
			for (Recipes r : resultlist) {
				for (Ingredients i : r.getIngredients()) {
					if (i.getItem().equals(ingredientlist.get(0).getItem())) {
						result = r.getName();

					}
				}

			}
		}

		return result;
	}

	public String execute() {
		fridgelist.clear();
		recipeslist.clear();
		ingredientlist.clear();
		resultlist.clear();
		UploadServlet us = new UploadServlet();

		// Put Json object into Arraylist(object) recipeslist
		JsonParser parser = new JsonParser();
		JsonArray jsonArray = parser.parse(getStrFromJson(us.getJsonfilePath())).getAsJsonArray();
		Gson gson = new Gson();

		for (JsonElement user : jsonArray) {
			Recipes userBean = gson.fromJson(user, Recipes.class);
			recipeslist.add(userBean);
		}
		readCsvFile(us.getCSVfilePath());
		return getResult();
	}
}