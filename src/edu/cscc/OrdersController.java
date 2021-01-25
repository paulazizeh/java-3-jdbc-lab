package edu.cscc;

import com.mysql.cj.jdbc.MysqlDataSource;
import edu.cscc.framework.ApplicationController;
import edu.cscc.framework.MVCContext;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class OrdersController extends ApplicationController {

    private static final String MYSQL_DB_URL = "jdbc:mysql://localhost:3306/lackluster_video";
    private static final String MYSQL_DB_USERNAME = "root";
    private static final String MYSQL_DB_PASSWORD = "password";
    private String currentEmployeeId;
    private String currentCustomerId;


    public OrdersController(MVCContext context) {
        super(context);
    }

    public void index() throws SQLException {
            Properties properties = new Properties();
            buildProperties(properties);
            DataSource dataSource = getDataSource(properties);
            Connection connection = null;
            try {
                connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select ol.order_id, e.first_name, e.last_name, c.smart_id, c.first_name, c.last_name, orders.store_number, r.name\n" +
                    "from lackluster_video.order_line_items ol\n" +
                    "inner join lackluster_video.orders orders on ol.order_id = orders.id\n" +
                    "inner join lackluster_video.customers c on orders.customer_id = c.id\n" +
                    "inner join lackluster_video.employees e on orders.employee_id = e.id\n" +
                    "inner JOIN lackluster_video.rentals r on ol.rental_id = r.id");
                render(new OrdersIndex(context, resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        }

    private static void buildProperties(Properties properties) {
        properties.setProperty(MYSQL_DB_URL, "jdbc:mysql://localhost:3306/lackluster_video");
        properties.setProperty(MYSQL_DB_USERNAME, "root");
        properties.setProperty(MYSQL_DB_PASSWORD, "password");
    }

    public void delete() {
        Properties properties = new Properties();
        buildProperties(properties);
        DataSource dataSource = getDataSource(properties);
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select ol.order_id, e.first_name, e.last_name, c.smart_id, c.first_name, c.last_name, orders.store_number, r.name\n" +
                    "from lackluster_video.order_line_items ol\n" +
                    "inner join lackluster_video.orders orders on ol.order_id = orders.id\n" +
                    "inner join lackluster_video.customers c on orders.customer_id = c.id\n" +
                    "inner join lackluster_video.employees e on orders.employee_id = e.id\n" +
                    "inner JOIN lackluster_video.rentals r on ol.rental_id = r.id");
            render(new OrdersIndex(context, resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create() {
        render(new OrdersCreate(context));
    }

    public void complete() {
        // Todo save order to database
        Properties properties = new Properties();
        properties.setProperty(MYSQL_DB_URL, "jdbc:mysql://localhost:3306/lackluster_video");
        properties.setProperty(MYSQL_DB_USERNAME, "root");
        properties.setProperty(MYSQL_DB_PASSWORD, "password");
        currentEmployeeId = context.getRequest().getParams().get("employeeId").toString();
        currentCustomerId = context.getRequest().getParams().get("customerid").toString();
        List<String> rentalIds = (List <String>) context.getRequest().getParams().get("rentalIds");
        DataSource dataSource = getDataSource(properties);
        String insert = "Insert into orders (employee_id, customer_id, store_number) values (" + currentEmployeeId + ", " + currentCustomerId + ", (SELECT active_store_number FROM employees WHERE id = " + currentEmployeeId + "))";

        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement updateOrders = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            updateOrders.executeUpdate();
            ResultSet keySet = updateOrders.getGeneratedKeys();
            keySet.next();
            for (String rentalId : rentalIds) {
                String insert2 = "Insert into order_line_items (order_id, rental_id) values (" + keySet.getInt(1) + ", " + rentalId + ")";
                PreparedStatement updateLineItems = connection.prepareStatement(insert2);
                updateLineItems.executeUpdate();
            }
            connection.commit();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select ol.order_id, e.first_name, e.last_name, c.smart_id, c.first_name, c.last_name, orders.store_number, r.name\n" +
                    "from lackluster_video.order_line_items ol\n" +
                    "inner join lackluster_video.orders orders on ol.order_id = orders.id\n" +
                    "inner join lackluster_video.customers c on orders.customer_id = c.id\n" +
                    "inner join lackluster_video.employees e on orders.employee_id = e.id\n" +
                    "inner JOIN lackluster_video.rentals r on ol.rental_id = r.id");
            render(new OrdersIndex(context, resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private static DataSource getDataSource(Properties properties) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(properties.getProperty(MYSQL_DB_URL));
        dataSource.setUser(properties.getProperty(MYSQL_DB_USERNAME));
        dataSource.setPassword(properties.getProperty(MYSQL_DB_PASSWORD));

        return dataSource;
    }

}


