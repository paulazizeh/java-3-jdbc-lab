package edu.cscc;

import com.mysql.cj.jdbc.MysqlDataSource;
import edu.cscc.framework.ApplicationController;
import edu.cscc.framework.MVCContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public void index() {
        render(new OrdersIndex(context));
    }

    public void delete() {
        render(new OrdersIndex(context));
    }

    public void create() {
        render(new OrdersCreate(context));
    }

    public void complete() {
        render(new OrdersIndex(context));
    }

    public void save() {
        // Todo save order to database
        Properties properties = new Properties();
        properties.setProperty(MYSQL_DB_URL, "jdbc:mysql://localhost:3306/lackluster_video");
        properties.setProperty(MYSQL_DB_USERNAME, "root");
        properties.setProperty(MYSQL_DB_PASSWORD, "password");
        currentEmployeeId = context.getRequest().getParams().get("employeeId").toString();
        currentCustomerId = context.getRequest().getParams().get("customerid").toString();
        DataSource dataSource = getDataSource(properties);
        String insert = "Insert into orders (employee_id, customer_id, store_number) values (" + currentEmployeeId + ", " + currentCustomerId + ", (SELECT active_store_number FROM employees WHERE id = " + currentEmployeeId + "))";

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement updateOrders = connection.prepareStatement(insert);
            updateOrders.execute();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        render(new OrdersCreate(context));

    }

    private static DataSource getDataSource(Properties properties) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(properties.getProperty(MYSQL_DB_URL));
        dataSource.setUser(properties.getProperty(MYSQL_DB_USERNAME));
        dataSource.setPassword(properties.getProperty(MYSQL_DB_PASSWORD));

        return dataSource;
    }

}


