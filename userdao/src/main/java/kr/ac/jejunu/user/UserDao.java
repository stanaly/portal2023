package kr.ac.jejunu.user;

import java.sql.*;

public class UserDao {
    public User findById(Long id) throws ClassNotFoundException, SQLException {
        //데이터 어디에 있나? mysql
        //localhost jeju   => jeju/jejupw => userinfo
        //jdbc driver 클래스 로딩
        Class.forName("com.mysql.cj.jdbc.Driver");
        //커넥션 을 맺고
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/jeju", "jeju", "jejupw");
        //sql 을 만들고
        PreparedStatement preparedStatement = connection.prepareStatement
                ("select id, name, password from userinfo where id = ?");
        preparedStatement.setLong(1, id);
        //sql 을 실행하고
        ResultSet resultSet = preparedStatement.executeQuery();
        //결과를 user 에 잘 매핑하고요
        resultSet.next();
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));
        //자원을 해지
        resultSet.close();
        preparedStatement.close();
        connection.close();
        //결과를 리턴
        return user;
    }

    public void insert(User user) throws ClassNotFoundException, SQLException {
        //데이터 어디에 있나? mysql
        //localhost jeju   => jeju/jejupw => userinfo
        //jdbc driver 클래스 로딩
        Class.forName("com.mysql.cj.jdbc.Driver");
        //커넥션 을 맺고
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/jeju", "jeju", "jejupw");
        //sql 을 만들고
        PreparedStatement preparedStatement = connection.prepareStatement
                ("insert into userinfo (name, password) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        //sql 을 실행하고
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        user.setId(resultSet.getLong(1));
        //자원을 해지
        resultSet.close();
        preparedStatement.close();
        connection.close();

    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        //데이터 어딨어? mysql
        //mysql 클래스 로딩
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Connection 맺고
        return DriverManager.getConnection("jbdc:mysql://localhost/jeju", "jeju", "jejupw");
        //
        // }
}
