package interfaces;

import model.User;

public interface UserRepository {
    User findByUsername(String username);
    boolean save(User user);
    boolean register(User user);
    boolean authenticate(String username, String password);
}
