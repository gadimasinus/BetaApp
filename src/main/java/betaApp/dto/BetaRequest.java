package betaApp.dto;

public class BetaRequest {

	private String ticker;
	private String baselineTikcer;
	private String startDate;
	private String endDate;
	private int betaDurationDays;
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getBaselineTikcer() {
		return baselineTikcer;
	}
	public void setBaselineTikcer(String baselineTikcer) {
		this.baselineTikcer = baselineTikcer;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getBetaDurationDays() {
		return betaDurationDays;
	}
	public void setBetaDurationDays(int betaDurationDays) {
		this.betaDurationDays = betaDurationDays;
	}
	
	@Override
	public String toString() {
		return "BetaRequest [ticker=" + ticker + ", baselineTikcer=" + baselineTikcer + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", betaDurationDays=" + betaDurationDays + "]";
	}
	
}
