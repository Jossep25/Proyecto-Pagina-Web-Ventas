package model;

public class Users {
    private final int id;
    private final String username;
    private final String password;
    private final String email;
    private final int roleId;
    private final String firstName;
    private final String lastName;
    private final String dni;

    public Users(int id, String username, String password, String email, int roleId, String firstName, String lastName, String dni) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dni = dni;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDni() {
        return dni;
    }
}
