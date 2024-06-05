package repository
import interfaces.userRepository
import model.users
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet

class DatabaseUserRepository : userRepository{
    private val connection : Connection
    init {
        val jdbcUrl = "jdbc:mysql://localhost:3306/bd_ventas"
        val jdbcUser = "root"
        val jdbcPassword = ""
        connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword)
    }
    override fun findByUsername(username: String): users? {
        val query = "SELECT id, username, password, role_id FROM users WHERE username = ?"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setString(1, username)
        val resultSet: ResultSet = statement.executeQuery()
        return if (resultSet.next()) {
            users(
                id = resultSet.getInt("id"),
                username = resultSet.getString("username"),
                password = resultSet.getString("password"),
                email = resultSet.getString("email"),
                roleId = resultSet.getInt("role_id"),
                firstName = resultSet.getString("firstName"),
                lastName = resultSet.getString("lastName"),
                dni = resultSet.getString("dni")
            )
        } else {
            null
        }
    }
    override fun save(user: users): Boolean {
        val query = "INSERT INTO users (username, password,email, role_id, first_name, last_name, dni) VALUES (?, ?, ?, ?, ?, ?, ?)"
        val statement: PreparedStatement = connection.prepareStatement(query)
        statement.setString(1, user.username)
        statement.setString(2, user.password)
        statement.setString(3, user.email)
        statement.setInt(4, user.roleId)
        statement.setString(5, user.firstName)
        statement.setString(6, user.lastName)
        statement.setString(7, user.dni)
        val rowsInserted = statement.executeUpdate()
        return rowsInserted > 0
    }

    override fun register(user: users): Boolean { 
        return save(user)
    }
}