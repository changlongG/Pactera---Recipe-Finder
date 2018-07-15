package test;

import java.nio.charset.Charset;
import java.util.ArrayList;

import com.csvreader.CsvReader;

public class ReadCsvFile {
	public static void readCsvFile(String filePath) {
		try {
			ArrayList<String[]> csvList = new ArrayList<String[]>();
			CsvReader reader = new CsvReader(filePath, ',', Charset.forName("GBK"));

			while (reader.readRecord()) {
				csvList.add(reader.getValues());
			}
			reader.close();

			for (int row = 0; row < csvList.size(); row++) {
				System.out.println("-----------------");
				System.out.print(csvList.get(row)[0] + ",");
				System.out.print(csvList.get(row)[1] + ",");
				System.out.print(csvList.get(row)[2] + ",");
				System.out.println(csvList.get(row)[3] + ",");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String filePath = "C:/Users/ChangLong/Desktop/fridge.csv";
		readCsvFile(filePath);
	}
}