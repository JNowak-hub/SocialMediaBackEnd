package pl.surf.web.demo.model.responses;

public class UserResponses {
    private String username;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;

    public String getUsername() {
        return username;
    }

    public UserResponses setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserResponses setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserResponses setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserResponses setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserResponses setPassword(String password) {
        this.password = password;
        return this;
    }
}
