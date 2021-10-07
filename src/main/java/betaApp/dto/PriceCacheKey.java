package betaApp.dto;

import java.util.Objects;

public class PriceCacheKey {

	private String date;
	private String ticker;
	
	public PriceCacheKey(String ticker, String date ) {
		this.ticker = ticker;
		this.date = date;
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
	
	@Override
	public int hashCode() {
		return Objects.hash(date, ticker);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PriceCacheKey other = (PriceCacheKey) obj;
		return Objects.equals(date, other.date) && Objects.equals(ticker, other.ticker);
	}
}
