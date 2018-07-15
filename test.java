package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.csvreader.CsvReader;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class test {
	static ArrayList<Fridge> fridgelist = new ArrayList<Fridge>();
	static ArrayList<Recipes> recipeslist = new ArrayList<Recipes>();

	public static void display(ArrayList<String> lst) {
		for (String s : lst)
			System.out.println(s);
	}

	private static String getStrFromJson(String name) {
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

	public static void readCsvFile(String filePath) {
		try {
			ArrayList<String[]> csvList = new ArrayList<String[]>();
			CsvReader reader = new CsvReader(filePath, ',', Charset.forName("GBK"));

			while (reader.readRecord()) {
				csvList.add(reader.getValues());
			}
			reader.close();

			for (int row = 0; row < csvList.size(); row++) {
				Fridge fridge = new Fridge();
				fridge.setItem(csvList.get(row)[0]);
				fridge.setAmount(Integer.parseInt(csvList.get(row)[1]));
				fridge.setUnit(csvList.get(row)[2]);
				fridge.setUseby(csvList.get(row)[3]);
				fridgelist.add(fridge);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getResult() {
		ArrayList<String> resultlist = new ArrayList<String>();

		for (Recipes r : recipeslist) {
			int signal = 0;
			for (Ingredients i : r.getIngredients()) {
				for (Fridge f : fridgelist) {
					if (i.getItem().equals(f.getItem()) && i.getUnit().equals(f.getUnit())
							&& i.getAmount() <= f.getAmount()) {
						signal += 1;
						// System.out.println(f.getItem());
						break;
					}
				}
			}
			if (signal == r.getIngredients().size()) {
				resultlist.add(r.getName());
			}
		}
		System.out.println(resultlist.size());
		display(resultlist);
		// System.out.println(a.getItem() + "," + a.getAmount() + "," +
		// a.getUnit());

	}

	public static void main(String[] args) {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
		// Date date = sdf.parse(dateString);
		/*
		 * Date day = new Date(); String dateStr = (new
		 * SimpleDateFormat("dd/MM/yyyy")).format(day);
		 * System.out.println(dateStr);
		 */
		JsonParser parser = new JsonParser();
		JsonArray jsonArray = parser.parse(getStrFromJson("C:/Users/ChangLong/Desktop/recipes2.json")).getAsJsonArray();
		Gson gson = new Gson();

		for (JsonElement user : jsonArray) {
			Recipes userBean = gson.fromJson(user, Recipes.class);
			recipeslist.add(userBean);
		}
		String csvfilePath = "C:/Users/ChangLong/Desktop/fridge.csv";
		readCsvFile(csvfilePath);
		// display(fridgelist);
		getResult();
	}
}