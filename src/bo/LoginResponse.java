package bo;

public class LoginResponse {
	private int maxOrder;
	private long creationTimeStamp;
	public LoginResponse(){
		this.creationTimeStamp = System.currentTimeMillis();
	}
	
	public long getCreationTimeStamp() {
		return creationTimeStamp;
	}

	public int getMaxOrder() {
		return maxOrder;
	}

	public void setMaxOrder(int maxOrder) {
		this.maxOrder = maxOrder;
	}
}
