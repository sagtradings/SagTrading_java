package tradeloggers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import bo.OrderActionRequest;

public class OrderActionRequestLogger implements CsvLogger<OrderActionRequest> {
	private static StringBuffer headerBuffer = new StringBuffer();
	static{
		headerBuffer.append("actionFlag,");
		headerBuffer.append("brokerId,");
		headerBuffer.append("creationTimeStamp,");
		headerBuffer.append("exchangeID,");
		headerBuffer.append("frontId,");
		headerBuffer.append("instrumentID,");
		headerBuffer.append("investorID,");
		headerBuffer.append("limitPrice,");
		headerBuffer.append("orderActionRef,");
		headerBuffer.append("orderRef,");
		headerBuffer.append("orderSysId,");
		headerBuffer.append("requestId,");
		headerBuffer.append("sessionId,");
		headerBuffer.append("userId,");
		headerBuffer.append("volumeChange\n");
	}
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); 
	public void logObject(OrderActionRequest object, String fileName) {
		File file = new File(formatter.format(Calendar.getInstance().getTime())+"\\"+fileName+".csv");
		try {
			if(!file.exists()){
				new File(formatter.format(Calendar.getInstance().getTime())).mkdir();
				file.createNewFile();
				FileWriter writer = new FileWriter(file,true);
				BufferedWriter bufferWriter = new BufferedWriter(writer);
				bufferWriter.write(headerBuffer.toString());
				bufferWriter.close();
			}
			FileWriter writer = new FileWriter(file,true);
			BufferedWriter bufferWriter = new BufferedWriter(writer);
			StringBuffer dataBuffer = new StringBuffer();
			dataBuffer.append(object.getActionFlag()).append(",");
			dataBuffer.append(object.getBrokerID()).append(",");
			dataBuffer.append(object.getCreationTimeStamp()).append(",");
			dataBuffer.append(object.getExchangeID()).append(",");
			dataBuffer.append(object.getFrontID()).append(",");
			dataBuffer.append(object.getInstrumentID()).append(",");
			dataBuffer.append(object.getInvestorID()).append(",");
			dataBuffer.append(object.getLimitPrice()).append(",");
			dataBuffer.append(object.getOrderActionRef()).append(",");
			dataBuffer.append(object.getOrderRef()).append(",");
			dataBuffer.append(object.getOrderSysID()).append(",");
			dataBuffer.append(object.getRequestID()).append(",");
			dataBuffer.append(object.getSessionID()).append(",");
			dataBuffer.append(object.getUserID()).append(",");
			dataBuffer.append(object.getVolumeChange()).append("\n");
			bufferWriter.write(dataBuffer.toString());
			bufferWriter.close();
			
			
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
