package dao;

import java.util.List;

import bo.BarData;
import bo.MarketDataResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class MarketDataDAO {
    private static final Log log = LogFactory.getLog(MarketDataDAO.class);

    public void addMarketData(MarketDataResponse marketDataResponse) {

        // obtain the session and begin transaction
        Session session = SessionUtil.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        // store the barData
        session.save(marketDataResponse);

        // commit the transaction
        tx.commit();
    }

    public List<MarketDataResponse> getAllMarketData() {
        String hqlQuery = "FROM MarketDataResponse";

        //obtain the session and begin transaction
        Session session = SessionUtil.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        //execute the query and obtain the list
        Query query = session.createQuery(hqlQuery);
        List<MarketDataResponse> result = query.list();

        //commit the transaction
        tx.commit();

        //return the result
        return result;
    }

    public MarketDataResponse getMarketData(int marketdata_id) {
        Session session = SessionUtil.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("From MarketDataResponse b where b.marketdata_id =: marketdata_id");
        query.setInteger("marketdata_id", marketdata_id);
        MarketDataResponse member = (MarketDataResponse) query.uniqueResult();
        tx.commit();
        return member;
    }
}

