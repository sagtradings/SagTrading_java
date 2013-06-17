package main;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import bo.BarData;
import dao.BarDataDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;


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
}
