package lt.verbus.multithreading;

import lt.verbus.domain.entity.User;
import lt.verbus.util.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserSaveThread extends Thread {

    private Session session;
    final private User user;

    public UserSaveThread(Session session, User user) {
        this.session = session;
        this.user = user;
    }

    @Override
    public void run() {
        while (session == null) {
            try {
                Thread.sleep(1000);
                session = SessionFactoryUtil.getSession();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("User " + user + " saved");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }
}