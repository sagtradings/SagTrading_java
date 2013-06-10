package bo;

public class ErrorResult {
	private long creationTimeStamp;
	public int getErrorId() {
		return errorId;
	}

	public void setErrorId(int errorId) {
		this.errorId = errorId;
	}

	private int errorId;
	private String errorMessage;
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ErrorResult(){
		this.creationTimeStamp = System.currentTimeMillis();
	}

	public long getCreationTimeStamp() {
		return creationTimeStamp;
	}
}
