package tradeloggers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import bo.TradeDataResponse;

public class TradeDataResponseLogger implements CsvLogger<TradeDataResponse> {
	private static StringBuffer headerBuffer = new StringBuffer();
	static{
		TradeDataResponse bo = new TradeDataResponse();
		
		headerBuffer.append("BrokerID").append(",");
		headerBuffer.append("BrokerOrderSeq").append(",");
		headerBuffer.append("businessUnit").append(",");
		headerBuffer.append("clearingPartID").append(",");
		headerBuffer.append("clientID").append(",");
		
		headerBuffer.append("creationTimeStamp").append(",");
		headerBuffer.append("direction").append(",");
		headerBuffer.append("cxchangeID").append(",");
		headerBuffer.append("exchangeInstID").append(",");
		headerBuffer.append("hedgeFlag").append(",");
		
		headerBuffer.append("instrumentID").append(",");
		headerBuffer.append("investorID").append(",");
		headerBuffer.append("offsetFlag").append(",");
		headerBuffer.append("orderLocalID").append(",");
		headerBuffer.append("orderRef").append(",");
		
		headerBuffer.append("orderSysID").append(",");
		headerBuffer.append("participantID").append(",");
		headerBuffer.append("price").append(",");
		headerBuffer.append("priceSource").append(",");
		headerBuffer.append("sequenceNo").append(",");
		
	    headerBuffer.append("settlementID").append(",");
		headerBuffer.append("tradeDate").append(",");
		headerBuffer.append("tradeID").append(",");
		headerBuffer.append("traderID").append(",");
		headerBuffer.append("tradeSource").append(",");
		
		headerBuffer.append("tradeTime").append(",");
		headerBuffer.append("tradeType").append(",");
		headerBuffer.append("tradingDay").append(",");
		headerBuffer.append("tradingRole").append(",");
		headerBuffer.append("userID").append(",");
		
		headerBuffer.append("volume").append("\n");
	}
	
	public void logObject(TradeDataResponse object, String fileName) {
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
			
			dataBuffer.append(object.getBrokerID()).append(",");
			dataBuffer.append(object.getBrokerOrderSeq()).append(",");
			dataBuffer.append(object.getBusinessUnit()).append(",");
			dataBuffer.append(object.getClearingPartID()).append(",");
			dataBuffer.append(object.getClientID()).append(",");
			
			dataBuffer.append(object.getCreationTimeStamp()).append(",");
			dataBuffer.append(object.getDirection()).append(",");
			dataBuffer.append(object.getExchangeID()).append(",");
			dataBuffer.append(object.getExchangeInstID()).append(",");
			dataBuffer.append(object.getHedgeFlag()).append(",");
			
			dataBuffer.append(object.getInstrumentID()).append(",");
			dataBuffer.append(object.getInvestorID()).append(",");
			dataBuffer.append(object.getOffsetFlag()).append(",");
			dataBuffer.append(object.getOrderLocalID()).append(",");
			dataBuffer.append(object.getOrderRef()).append(",");
			
			dataBuffer.append(object.getOrderSysID()).append(",");
			dataBuffer.append(object.getParticipantID()).append(",");
			dataBuffer.append(object.getPrice()).append(",");
			dataBuffer.append(object.getPriceSource()).append(",");
			dataBuffer.append(object.getSequenceNo()).append(",");
			
			dataBuffer.append(object.getSettlementID()).append(",");
			dataBuffer.append(object.getTradeDate()).append(",");
			dataBuffer.append(object.getTradeID()).append(",");
			dataBuffer.append(object.getTraderID()).append(",");
			dataBuffer.append(object.getTradeSource()).append(",");
			
			dataBuffer.append(object.getTradeTime());
			dataBuffer.append(object.getTradeType()).append(",");
			dataBuffer.append(object.getTradingDay()).append(",");
			dataBuffer.append(object.getTradingRole()).append(",");
			dataBuffer.append(object.getUserID()).append(",");
			
			dataBuffer.append(object.getVolume()).append("\n");
			
			bufferWriter.write(dataBuffer.toString());
			bufferWriter.close();
			
			
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
