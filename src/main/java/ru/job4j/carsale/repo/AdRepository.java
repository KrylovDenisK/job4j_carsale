package ru.job4j.carsale.repo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.carsale.model.Ad;
import ru.job4j.carsale.store.hibernate.HibernateFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class AdRepository {
    private SessionFactory sessionFactory = HibernateFactory.getInstance().getSessionFactory();

    public AdRepository() {

    }

    private List<Ad> getBy(final Function<Session, List<Ad>> command) {
        List<Ad> result = new ArrayList<>();
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            result = command.apply(session);
            tx.commit();
        } catch (Exception x) {
            if (tx != null) {
                tx.rollback();
            }
        }
        return result;
    }

    public List<Ad> getAdWithFoto() {
        return getBy(session -> session.createQuery("select a from ru.job4j.carsale.model.Ad a join fetch a.car c "
                    + "join fetch c.model m "
                    + "join fetch m.brand b "
                    + "join fetch m.body body "
                    + "join fetch a.owner o "
                    + "join fetch a.status s where c.photo is not null ", Ad.class).list());
    }

    public List<Ad> getAdByBrand(String brand) {
        return getBy(session -> session.createQuery("select a from ru.job4j.carsale.model.Ad a join fetch a.car c "
                + "join fetch c.model m "
                + "join fetch m.brand b "
                + "join fetch m.body body "
                + "join fetch a.owner o "
                + "join fetch a.status s where m.brand.name = :brand", Ad.class).setParameter("brand", brand).list());
    }

    public List<Ad> getAdByLastDay() {
        return getBy(session -> session.createQuery("from ru.job4j.carsale.model.Ad a where a.date > :startDate ", Ad.class)
                .setParameter("startDate", LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0))).list());
    }
}
