package bo;

public class SubscribeMarketDataResponse {
	private String specificInstrument;
	private long creationTimeStamp;
	private int errorId;
	private String errorMsg;
	
	public SubscribeMarketDataResponse(){
		this.creationTimeStamp = System.currentTimeMillis();
	}

	public String getSpecificInstrument() {
		return specificInstrument;
	}

	public void setSpecificInstrument(String specificInstrument) {
		this.specificInstrument = specificInstrument;
	}

	public int getErrorId() {
		return errorId;
	}

	public void setErrorId(int errorId) {
		this.errorId = errorId;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public long getCreationTimeStamp() {
		return creationTimeStamp;
	}
}
