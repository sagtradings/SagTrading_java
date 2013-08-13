package dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bo.BarData;
import org.junit.Test;


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

    public BarData getHighestHighBarData(String instrumentId, Date startDate, int numberOfPreviousRecord){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss");
        String startDateStr = df.format(startDate);
        Session session = SessionUtil.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("From BarData b where b.day <= '" + startDateStr + "' and b.instrumentId = '" + instrumentId+"' order by b.day DESC");
        query.setMaxResults(numberOfPreviousRecord);
        List<BarData> members = (List<BarData>) query.list();
        tx.commit();

        return Collections.max(members, new Comparator<BarData>() {
            @Override
            public int compare(BarData o1, BarData o2) {
                if(o1.getHigh() > o2.getHigh()) return 1;
                if(o1.getHigh() < o2.getHigh()) return -1;
                return 0;
            }
        });
    }
    
    public BarData getHighestLowBarData(String instrumentId, Date startDate, int numberOfPreviousRecord){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss");
        String startDateStr = df.format(startDate);
        Session session = SessionUtil.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("From BarData b where b.day <= '" + startDateStr + "' and b.instrumentId = '" + instrumentId+"' order by b.day DESC");
        query.setMaxResults(numberOfPreviousRecord);
        List<BarData> members = (List<BarData>) query.list();
        tx.commit();

        return Collections.max(members, new Comparator<BarData>() {
            @Override
            public int compare(BarData o1, BarData o2) {
                if(o1.getLow() > o2.getLow()) return 1;
                if(o1.getLow() < o2.getLow()) return -1;
                return 0;
            }
        });
    }
    
    public BarData getHighestCloseBarData(String instrumentId, Date startDate, int numberOfPreviousRecord){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss");
        String startDateStr = df.format(startDate);
        Session session = SessionUtil.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("From BarData b where b.day <= '" + startDateStr + "' and b.instrumentId = '" + instrumentId+"' order by b.day DESC");
        query.setMaxResults(numberOfPreviousRecord);
        List<BarData> members = (List<BarData>) query.list();
        tx.commit();

        return Collections.max(members, new Comparator<BarData>() {
            @Override
            public int compare(BarData o1, BarData o2) {
                if(o1.getClose() > o2.getClose()) return 1;
                if(o1.getClose() < o2.getClose()) return -1;
                return 0;
            }
        });
    }
    
    public BarData getHighestOpenBarData(String instrumentId, Date startDate, int numberOfPreviousRecord){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss");
        String startDateStr = df.format(startDate);
        Session session = SessionUtil.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("From BarData b where b.day <= '" + startDateStr + "' and b.instrumentId = '" + instrumentId+"' order by b.day DESC");
        query.setMaxResults(numberOfPreviousRecord);
        List<BarData> members = (List<BarData>) query.list();
        tx.commit();

        return Collections.max(members, new Comparator<BarData>() {
            @Override
            public int compare(BarData o1, BarData o2) {
                if(o1.getOpen() > o2.getOpen()) return 1;
                if(o1.getOpen() < o2.getOpen()) return -1;
                return 0;
            }
        });
    }

    public BarData getLowestBarData(String instrumentId, Date startDate, int numberOfPreviousRecord){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss");
        String startDateStr = df.format(startDate);
        Session session = SessionUtil.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("From BarData b where b.day <= '" + startDateStr + "' and b.instrumentId = '" + instrumentId+"' order by b.day DESC");
        query.setMaxResults(numberOfPreviousRecord);
        List<BarData> members = (List<BarData>) query.list();
        tx.commit();

        return Collections.min(members, new Comparator<BarData>() {
            @Override
            public int compare(BarData o1, BarData o2) {
                if(o1.getLow() > o2.getLow()) return 1;
                if(o1.getLow() < o2.getLow()) return -1;
                return 0;
            }
        });
    }

    @Test
    public void getHighestBarDataTest(){

        String instrumentId = "IF1309";
        int numberOfPreviousRecord = 3;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss");
        Date startDate = null;
        try {
            startDate = formatter.parse("2013-08-07 11:29:13");
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Session session = SessionUtil.sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("From BarData b where b.day <= '2013-08-07 11:29:13' and b.instrumentId = '" + instrumentId+"' order by b.day DESC");
        query.setMaxResults(numberOfPreviousRecord);
        List<BarData> members = (List<BarData>) query.list();
        tx.commit();

        BarData barData = Collections.min(members, new Comparator<BarData>() {
            @Override
            public int compare(BarData o1, BarData o2) {
                if (o1.getLow() > o2.getLow()) return 1;
                if (o1.getLow() < o2.getLow()) return -1;
                return 0;
            }
        });
        System.out.println();
    }
}

