package interfaces
import model.users
interface userRepository {
    fun findByUsername(username: String): users?
    fun save(user: users): Boolean
    fun register(user: users): Boolean
}