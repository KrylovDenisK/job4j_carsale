package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException  {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        executeQuery("./db/00-create-orders-table.sql");
    }

    @After
    public void setDown() throws SQLException {
        executeQuery("./db/00-drop-orders-table.sql");
    }

    private void executeQuery(String query) throws SQLException  {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(query)))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name1", "description1"));
        List<Order> all = (List<Order>) store.findAll();
        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenSearchOrdersByNameGetTwoRowWithName() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name", "description1"));
        store.save(Order.of("NoName", "description2"));
        store.save(Order.of("name", "description3"));
        List<Order> result = store.findByName("name");
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getDescription(), is("description1"));
        assertThat(result.get(0).getId(), is(1));
        assertThat(result.get(1).getDescription(), is("description3"));
        assertThat(result.get(1).getId(), is(3));
    }

    @Test
    public void whenUpdateByIdThenResultTrue() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name1", "description1"));
        boolean result = store.update(1, Order.of("newName", "newDescription"));
        Order ordRst = store.findById(1);
        assertThat(result, is(true));
        assertThat(ordRst.getDescription(), is("newDescription"));
        assertThat(ordRst.getName(), is("newName"));


    }
}