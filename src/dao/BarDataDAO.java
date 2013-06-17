package dao;

import java.util.List;

import bo.BarData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class BarDataDAO {
    private static final Log log = LogFactory.getLog(BarDataDAO.class);

    public void addBarData(BarData barData) {

        // obtain the session and begin transaction
        Session session = SessionUtil.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        // store the barData
        session.save(barData);

        // commit the transaction
        tx.commit();
    }

    public List<BarData> getAllBarData() {
        String hqlQuery = "FROM BarData";

        //obtain the session and begin transaction
        Session session = SessionUtil.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        //execute the query and obtain the list
        Query query = session.createQuery(hqlQuery);
        List<BarData> result = query.list();

        //commit the transaction
        tx.commit();

        //return the result
        return result;
    }

    public BarData getBarData(int bardata_id) {
        Session session = SessionUtil.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("From BarData b where b.bardata_id =: bardata_id");
        query.setInteger("bardata_id", bardata_id);
        BarData member = (BarData) query.uniqueResult();
        tx.commit();
        return member;
    }
}

