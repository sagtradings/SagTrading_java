package tradeloggers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import bo.OrderInsertResponse;
import bo.TradeRequest;

public class OrderRequestLogger implements CsvLogger<TradeRequest> {

	private static StringBuffer headerBuffer = new StringBuffer();
	static{
		
		headerBuffer.append("AutoSuspend,");
		headerBuffer.append("BrokerID,");
		headerBuffer.append("BusinessUnit,");
		headerBuffer.append("CombHedgeFlag,");
		headerBuffer.append("CombOffsetFlag,");
		
		headerBuffer.append("ContingentCondition,");
		headerBuffer.append("CreationTimeStamp,");
		headerBuffer.append("CutOffPrice,");
		headerBuffer.append("Direction,");
		headerBuffer.append("ForceCloseReason,");
		
		headerBuffer.append("GtdDate,");
		headerBuffer.append("InstrumentID,");
		headerBuffer.append("InvestorID,");
		headerBuffer.append("limitPrice,");
		headerBuffer.append("MinVolume,");
		
		headerBuffer.append("OrderPriceType,");
		headerBuffer.append("OrderRef,");
		headerBuffer.append("OriginatingOrderRef,");
		headerBuffer.append("RequestID,");
		headerBuffer.append("StopPrice,");
		
		headerBuffer.append("SwapOrder,");
		headerBuffer.append("TimeCondition,");
		headerBuffer.append("UserForceClose,");
		headerBuffer.append("UserID,");
		headerBuffer.append("VolumeCondition,");
		
		headerBuffer.append("VolumeTotalOriginal\n");
		
	}
	
	@Override
	public void logObject(TradeRequest object, String fileName) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); 
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
			
			dataBuffer.append(object.getAutoSuspend()).append(",");
			dataBuffer.append(object.getBrokerID()).append(",");
			dataBuffer.append(object.getBusinessUnit()).append(",");
			dataBuffer.append(object.getCombHedgeFlag()).append(",");
			dataBuffer.append(object.getCombOffsetFlag()).append(",");
			
			dataBuffer.append(object.getContingentCondition()).append(",");
			dataBuffer.append(object.getCreationTimeStamp()).append(",");
			dataBuffer.append(object.getCutOffPrice()).append(",");
			dataBuffer.append(object.getDirection()).append(",");
			dataBuffer.append(object.getForceCloseReason()).append(",");
			
			dataBuffer.append(object.getGtdDate()).append(",");
			dataBuffer.append(object.getInstrumentID()).append(",");
			dataBuffer.append(object.getInvestorID()).append(",");
			dataBuffer.append(object.getLimitPrice()).append(",");
			dataBuffer.append(object.getMinVolume()).append(",");
			
			dataBuffer.append(object.getOrderPriceType()).append(",");
			dataBuffer.append(object.getOrderRef()).append(",");
			dataBuffer.append(object.getOriginatingOrderRef()).append(",");
			dataBuffer.append(object.getRequestID()).append(",");
			dataBuffer.append(object.getStopPrice()).append(",");
			
			dataBuffer.append(object.getSwapOrder()).append(",");
			dataBuffer.append(object.getTimeCondition()).append(",");
			dataBuffer.append(object.getUserForceClose()).append(",");
			dataBuffer.append(object.getUserID()).append(",");
			dataBuffer.append(object.getVolumeCondition()).append(",");
			
			dataBuffer.append(object.getVolumeTotalOriginal()).append("\n");

			bufferWriter.write(dataBuffer.toString());
			bufferWriter.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


}
