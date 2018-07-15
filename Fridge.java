package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fridge {
	private Date useby;
	private String item;
	private int amount;
	private EnumUnit unit;

	public String getUseby() {
		String dateStr = (new SimpleDateFormat("dd/MM/yyyy")).format(useby);
		return dateStr;
	}

	public void setUseby(String useby1) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.useby = sdf.parse(useby1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

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

	public String toString() {
		return this.item + "," + this.amount + "," + this.unit + ","
				+ (new SimpleDateFormat("dd/MM/yyyy")).format(this.useby);

	}
}
