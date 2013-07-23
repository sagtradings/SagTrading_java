package tradeloggers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import bo.OrderInsertResponse;

public class OrderFilledLogger implements CsvLogger<OrderInsertResponse> {

	private static StringBuffer headerBuffer = new StringBuffer();
	static{
		
		headerBuffer.append("activeTime,");
		headerBuffer.append("activeTraderID,");
		headerBuffer.append("activeUserID,");
		headerBuffer.append("autosuspend,");
		headerBuffer.append("brokerID,");
		
		headerBuffer.append("brokerOrderSeq,");
		headerBuffer.append("businessUnit,");
		headerBuffer.append("cancelTime,");
		headerBuffer.append("clearingPartID,");
		headerBuffer.append("clientID,");
		
		headerBuffer.append("combHedgeFlag,");
		headerBuffer.append("combOffsetFlag,");
		headerBuffer.append("contingentCondition,");
		headerBuffer.append("creationTimeStamp,");
		headerBuffer.append("direction,");
		
		headerBuffer.append("exchangeId,");
		headerBuffer.append("exchangeInstId,");
		headerBuffer.append("forceCloseReason,");
		headerBuffer.append("frontId,");
		headerBuffer.append("gtdDate,");
		
		headerBuffer.append("insertDate,");
		headerBuffer.append("insertTime,");
		headerBuffer.append("installID,");
		headerBuffer.append("instrumentId,");
		headerBuffer.append("investorId,");
		
		headerBuffer.append("limitPrice,");
		headerBuffer.append("minVolume,");
		headerBuffer.append("notifySequence,");
		headerBuffer.append("orderLocalID,");
		headerBuffer.append("orderPriceType,");
		
		headerBuffer.append("orderRef,");
		headerBuffer.append("orderSource,");
		headerBuffer.append("orderSubmitStatus,");
		headerBuffer.append("orderSysId,");
		headerBuffer.append("orderType,");
		
		headerBuffer.append("participantId,");
		headerBuffer.append("relativeOrderSysId,");
		headerBuffer.append("requestId,");
		headerBuffer.append("sequenceNo,");
		headerBuffer.append("sessionId,");
		
		headerBuffer.append("settlementId,");
		headerBuffer.append("statusMsg,");
		headerBuffer.append("stopPrice,");
		headerBuffer.append("suspendTime,");
		headerBuffer.append("swapOrder,");
		
		headerBuffer.append("timeCondition,");
		headerBuffer.append("traderId,");
		headerBuffer.append("tradingDay,");
		headerBuffer.append("updateTime,");
		headerBuffer.append("userForceClose,");
		
		headerBuffer.append("userId,");
		headerBuffer.append("userProductInfo,");
		headerBuffer.append("volumeCondition,");
		headerBuffer.append("volumeTotal,");
		headerBuffer.append("volumeTotalOriginal,");
		
		headerBuffer.append("volumeTraded,");
		headerBuffer.append("zceTotalTradedVolume\n");


	}
	
	@Override
	public void logObject(OrderInsertResponse object, String fileName) {
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
			dataBuffer.append(object.getActiveTime()).append(",");
			dataBuffer.append(object.getActiveTraderID()).append(",");
			dataBuffer.append(object.getActiveUserID()).append(",");
			dataBuffer.append(object.getAutoSuspend()).append(",");
			dataBuffer.append(object.getBrokerID()).append(",");
			
			dataBuffer.append(object.getBrokerOrderSeq()).append(",");
			dataBuffer.append(object.getBusinessUnit()).append(",");
			dataBuffer.append(object.getCancelTime()).append(",");
			dataBuffer.append(object.getClearingPartID()).append(",");
			dataBuffer.append(object.getClientID()).append(",");
			
			dataBuffer.append(object.getCombHedgeFlag()).append(",");
			dataBuffer.append(object.getCombOffsetFlag()).append(",");
			dataBuffer.append(object.getContingentCondition()).append(",");
			dataBuffer.append(object.getCreationTimeStamp()).append(",");
			dataBuffer.append(object.getDirection()).append(",");
			
			dataBuffer.append(object.getExchangeID()).append(",");
			dataBuffer.append(object.getExchangeInstID()).append(",");
			dataBuffer.append(object.getForceCloseReason()).append(",");
			dataBuffer.append(object.getFrontID()).append(",");
			dataBuffer.append(object.getGtdDate()).append(",");
			
			dataBuffer.append(object.getInsertDate()).append(",");
			dataBuffer.append(object.getInsertTime()).append(",");
			dataBuffer.append(object.getInstallID()).append(",");
			dataBuffer.append(object.getInstrumentID()).append(",");
			dataBuffer.append(object.getInvestorID()).append(",");
			
			dataBuffer.append(object.getLimitPrice()).append(",");
			dataBuffer.append(object.getMinVolume()).append(",");
			dataBuffer.append(object.getNotifySequence()).append(",");
			dataBuffer.append(object.getOrderLocalID()).append(",");
			dataBuffer.append(object.getOrderPriceType()).append(",");
			
			dataBuffer.append(object.getOrderRef()).append(",");
			dataBuffer.append(object.getOrderSource()).append(",");
			dataBuffer.append(object.getOrderSubmitStatus()).append(",");
			dataBuffer.append(object.getOrderSysID()).append(",");
			dataBuffer.append(object.getOrderType()).append(",");
			
			dataBuffer.append(object.getParticipantID()).append(",");
			dataBuffer.append(object.getRelativeOrderSysID()).append(",");
			dataBuffer.append(object.getRequestID()).append(",");
			dataBuffer.append(object.getSequenceNo()).append(",");
			dataBuffer.append(object.getSessionID()).append(",");
			
			dataBuffer.append(object.getSettlementID()).append(",");
			dataBuffer.append(object.getStatusMsg()).append(",");
			dataBuffer.append(object.getStopPrice()).append(",");
			dataBuffer.append(object.getSuspendTime()).append(",");
			dataBuffer.append(object.getSwapOrder()).append(",");
			
			dataBuffer.append(object.getTimeCondition()).append(",");
			dataBuffer.append(object.getTraderID()).append(",");
			dataBuffer.append(object.getTradingDay()).append(",");
			dataBuffer.append(object.getUpdateTime()).append(",");
			dataBuffer.append(object.getUserForceClose()).append(",");
			
			dataBuffer.append(object.getUserID()).append(",");
			dataBuffer.append(object.getUserProductInfo()).append(",");
			dataBuffer.append(object.getVolumeCondition()).append(",");
			dataBuffer.append(object.getVolumeTotal()).append(",");
			dataBuffer.append(object.getVolumeTotalOriginal()).append(",");
			
			dataBuffer.append(object.getVolumeTraded()).append(",");
			dataBuffer.append(object.getZceTotalTradedVolume()).append("\n");

			bufferWriter.write(dataBuffer.toString());
			bufferWriter.close();
			
			
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
