package betaApp.dto;

public class TickerPrice {
	private String date;
	private String ticker;
	private double price;
	private final static String seperator="~";
	
	public TickerPrice(String date, String ticker, double price) {
		this.date = date;
		this.ticker = ticker;
		this.price = price;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getKey() {
		return this.ticker + seperator + this.date;
	}
	@Override
	public String toString() {
		return "TickerPrice [date=" + date + ", ticker=" + ticker + ", price=" + price + "]";
	}
	
	
	
}
