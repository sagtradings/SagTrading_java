package nativeinterfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.Timer;

import listeners.DefaultCTPListener;
import listeners.ICTPListener;
import bo.MarketDataResponse;

public class MockMDNativeInterface extends MarketDataNativeInterface{
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH:mm:ss");
	private SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
	private List<ICTPListener> listeners = new ArrayList<ICTPListener>(10);
	 public final int ACTION_DAY = 0;
	 
	 public final int ASK_PRICE_1 = 1;
	 public final int ASK_PRICE_2 = 2;
	 public final int ASK_PRICE_3 = 3;
	 public final int ASK_PRICE_4 = 4;
	 public final int ASK_PRICE_5 = 5;
	 
	 public final int ASK_VOLUME_1 = 6;
	 public final int ASK_VOLUME_2 = 7;
	 public final int ASK_VOLUME_3 = 8;
	 public final int ASK_VOLUME_4 = 9;
	 public final int ASK_VOLUME_5 = 10;
	 
	 public final int AVERAGE_PRICE = 11;
	 
	 public final int BID_PRICE_1 = 12;
	 public final int BID_PRICE_2 = 13;
	 public final int BID_PRICE_3 = 14;
	 public final int BID_PRICE_4 = 15;
	 public final int BID_PRICE_5 = 16;
	 
	 public final int BID_VOLUME_1 = 17;
	 public final int BID_VOLUME_2 = 18;
	 public final int BID_VOLUME_3 = 19;
	 public final int BID_VOLUME_4 = 20;
	 public final int BID_VOLUME_5 = 21;
	 
	 public final int CLOSE_PRICE = 22;
	 public final int CURR_DELTA = 23;
	 
	 public final int DOWN_VOLUME = 24;
	 public final int EXCHANGE_ID = 25;
	 public final int EXCHANGE_INST_ID = 26;
	 public final int HIGHEST_PRICE = 27;
	 public final int INSTRUMENT_ID = 28;
	 
	 public final int LAST_PRICE = 29;
	 public final int LOWER_LIMIT_PRICE = 30;
	 public final int LOWEST_PRICE = 31;
	 public final int OPEN_INTEREST = 32;
	 public final int OPEN_PRICE = 33;
	 
	 public final int PRE_CLOSE_PRICE = 34;
	 public final int PRE_DELTA = 35;
	 public final int PRE_OPEN_INTEREST = 36;
	 public final int PRE_SETTLEMENT_PRICE = 37;
	 public final int SETTLEMENT_PRICE = 38;
	 
	 public final int TIME_OF_EVENT = 39;
	 public final int TRADING_DAY = 40;
	 public final int TURN_OVER = 41;
	 public final int UPDATE_MILLISEC = 42;
	 public final int UPPER_LIMIT_PRICE = 43;
	 
	 public final int UP_VOLUME = 44;
	 public final int VOLUME = 45;
	 private List<MarketDataResponse> testData;
	 
	 private Timer mdMarketDataCompiler;
	 private List<String> subscribedInstruments = new ArrayList<String>(10);
	 public MockMDNativeInterface(String fileHandle){

		 String line;
		 testData = new ArrayList<MarketDataResponse>(10);
		 
		 try {
			 BufferedReader br = new BufferedReader(new FileReader(fileHandle));
			 while((line = br.readLine()) != null){
				 String[] txtData = line.split(",");
				 MarketDataResponse response = new MarketDataResponse();
				 response.setActionDay(txtData[ACTION_DAY]);

				 response.setAskPrice1(Double.parseDouble(txtData[ASK_PRICE_1]));
				 response.setAskPrice2(Double.parseDouble(txtData[ASK_PRICE_2]));
				 response.setAskPrice3(Double.parseDouble(txtData[ASK_PRICE_3]));
				 response.setAskPrice4(Double.parseDouble(txtData[ASK_PRICE_4]));
				 response.setAskPrice5(Double.parseDouble(txtData[ASK_PRICE_5]));

				 response.setAskVolume1(Integer.parseInt(txtData[ASK_VOLUME_1]));
				 response.setAskVolume2(Integer.parseInt(txtData[ASK_VOLUME_2]));
				 response.setAskVolume3(Integer.parseInt(txtData[ASK_VOLUME_3]));
				 response.setAskVolume4(Integer.parseInt(txtData[ASK_VOLUME_4]));
				 response.setAskVolume5(Integer.parseInt(txtData[ASK_VOLUME_5]));

				 response.setAveragePrice(Double.parseDouble(txtData[AVERAGE_PRICE]));

				 response.setBidPrice1(Double.parseDouble(txtData[BID_PRICE_1]));
				 response.setBidPrice2(Double.parseDouble(txtData[BID_PRICE_2]));
				 response.setBidPrice3(Double.parseDouble(txtData[BID_PRICE_3]));
				 response.setBidPrice4(Double.parseDouble(txtData[BID_PRICE_4]));
				 response.setBidPrice5(Double.parseDouble(txtData[BID_PRICE_5]));

				 response.setBidVolume1(Integer.parseInt(txtData[BID_VOLUME_1]));
				 response.setBidVolume2(Integer.parseInt(txtData[BID_VOLUME_2]));
				 response.setBidVolume3(Integer.parseInt(txtData[BID_VOLUME_3]));
				 response.setBidVolume4(Integer.parseInt(txtData[BID_VOLUME_4]));
				 response.setBidVolume5(Integer.parseInt(txtData[BID_VOLUME_5]));

				 response.setClosePrice(Double.parseDouble(txtData[CLOSE_PRICE]));
				 response.setCurrDelta(Double.parseDouble(txtData[CURR_DELTA]));

				 response.setDownVolume(Double.parseDouble(txtData[DOWN_VOLUME]));
				 response.setExchangeID(txtData[EXCHANGE_ID]);
				 response.setExchangeInstId(txtData[EXCHANGE_INST_ID]);
				 response.setHighestPrice(Double.parseDouble(txtData[HIGHEST_PRICE]));
				 response.setInstrumentId(txtData[INSTRUMENT_ID]);

				 response.setLastPrice(Double.parseDouble(txtData[LAST_PRICE]));
				 response.setLowerLimitPrice(Double.parseDouble(txtData[LOWER_LIMIT_PRICE]));
				 response.setLowestPrice(Double.parseDouble(txtData[LOWEST_PRICE]));
				 response.setOpenInterest(Double.parseDouble(txtData[OPEN_INTEREST]));
				 response.setOpenPrice(Double.parseDouble(txtData[OPEN_PRICE]));

				 response.setPreClosePrice(Double.parseDouble(txtData[PRE_CLOSE_PRICE]));
				 response.setPreDelta(Double.parseDouble(txtData[PRE_DELTA]));
				 response.setPreOpenInterest(Double.parseDouble(txtData[PRE_OPEN_INTEREST]));
				 response.setPreSettlementPrice(Double.parseDouble(txtData[PRE_SETTLEMENT_PRICE]));
				 response.setSettlementPrice(Double.parseDouble(txtData[SETTLEMENT_PRICE]));

				 response.setCreationTimeStamp(Long.parseLong(txtData[TIME_OF_EVENT]));
				 response.setTradingDay(txtData[TRADING_DAY]);
				 response.setTurnOver(Double.parseDouble(txtData[TURN_OVER]));
				 response.setUpdateMillisec(Integer.parseInt(txtData[UPDATE_MILLISEC]));
				 Calendar cal = Calendar.getInstance();
				 cal.setTimeInMillis(response.getCreationTimeStamp());
				 response.setUpdateTime(timeFormatter.format(cal.getTime()));

				 response.setUpperLimitPrice(Double.parseDouble(txtData[UPPER_LIMIT_PRICE]));
				 response.setUpVolume(Double.parseDouble(txtData[UP_VOLUME]));
				 response.setVolume(Integer.parseInt(txtData[VOLUME]));
				 testData.add(response);
			 }
			 br.close();
		 } catch (IOException e) {
			 System.exit(0);
		 }
		 Collections.sort(testData, new Comparator<MarketDataResponse>(){

			 @Override
			 public int compare(MarketDataResponse arg0, MarketDataResponse arg1) {
				 Integer int1 = arg0.getUpdateMillisec();
				 Integer int2 = arg1.getUpdateMillisec();
				 return int1.compareTo(int2);
			 }

		 });
		 mdMarketDataCompiler = new Timer(1000, new ActionListener(){

			 @Override
			 public void actionPerformed(ActionEvent arg0) {
				 if(testData.size() == 0 || testData.get(0) == null){
					 return;
				 }
				 MarketDataResponse top = testData.get(0);
				 int time = top.getUpdateMillisec();
				 while(testData.size() > 0 && testData.get(0).getUpdateMillisec() == time){
						MarketDataResponse pop = testData.get(0);
						pop.setMillisecConversionTime(System.currentTimeMillis());
						
					 for(int i = 0, n = listeners.size(); i  < n; i++){
						 if(subscribedInstruments.contains(pop.getInstrumentId())){
							 listeners.get(i).onRtnDepthMarketData(pop);
						 }
					 }
					 testData.remove(0);
				 }


			 }

		 });

	 }
	
	public void initiateTimer(){
		mdMarketDataCompiler.start();
	}
	 
	@Override
	public void subscribeListener(DefaultCTPListener obj) {
		listeners.add(obj);
	}

	@Override
	public void unSubscribeListener(DefaultCTPListener obj) {
		// TODO Auto-generated method stub
		super.unSubscribeListener(obj);
	}

	@Override
	public void sendLoginMessage(String brokerId, String password,
			String investorId, String url) {
		// TODO Auto-generated method stub
		//super.sendLoginMessage(brokerId, password, investorId, url);
	}

	@Override
	public void sendQuoteRequest(String[] insturments) {
		// TODO Auto-generated method stub
		for(int i = 0, n = insturments.length; i < n; i++){
			subscribedInstruments.add(insturments[i]);
		}
	}

	@Override
	public void sendUnsubscribeQuoteRequest(String[] insturments) {
		// TODO Auto-generated method stub
		for(int i = 0, n = insturments.length; i < n; i++){
			subscribedInstruments.remove(insturments[i]);
		}
	}

}
