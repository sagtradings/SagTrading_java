package main;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import bo.MarketDataResponse;
import dao.MarketDataDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import bo.BarData;
import dao.BarDataDAO;


public class HibernateTest {

    private static final Log log = LogFactory.getLog(HibernateTest.class);

    @Test
    public void barDateTesting() {
        log.info("Starting the Member Test Client");

        BarData barData = new BarData();
        BarDataDAO barDataDAO = new BarDataDAO();


        log.info("Adding BarData to DAO");
        barData.setClose(10.0);
        barData.setDownVolume(20.0);
        barData.setLow(1.0);
        barData.setHigh(5.0);
        barData.setInstrumentId("FFFFFF");
        barData.setTimestamp(String.valueOf(new Date()));

        barDataDAO.addBarData(barData);


        log.info("Listing all BarData");
        List list = barDataDAO.getAllBarData();

        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            log.info(iter.next());
        }
    }
    @Test
    public void marketDataResponse() {
        log.info("Starting the Member Test Client");

        MarketDataResponse marketData = new MarketDataResponse();
        MarketDataDAO marketDataDAO = new MarketDataDAO();


        log.info("Adding BarData to DAO");
        marketData.setActionDay("Gusiaass");


        marketDataDAO.addMarketData(marketData);


        log.info("Listing all BarData");
        List list = marketDataDAO.getAllMarketData();

        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            log.info(iter.next());
        }


    }
}
