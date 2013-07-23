package tradeloggers;

public interface CsvLogger<T> {
	public void logObject(T object, String fileName);
}
