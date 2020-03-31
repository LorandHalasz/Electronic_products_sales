package ro.utcluj.api.dto;

import java.io.Serializable;

public class UserBaseDTO implements Serializable {
    private Integer iduser;
    private String name;
    private String username;
    private String password;
    private String email;
    private String address;
    private String phone_number;
    private String user_role;
    private Double balance_account;

    public UserBaseDTO(){

    }

    public UserBaseDTO(String name, String username, String password, String email, String address, String phone_number, String user_role, Double balance_account) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone_number = phone_number;
        this.user_role = user_role;
        this.balance_account = balance_account;
    }

    public UserBaseDTO(Integer iduser, String name, String username, String password, String email, String address, String phone_number, String user_role, Double balance_account) {
        this.iduser = iduser;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone_number = phone_number;
        this.user_role = user_role;
        this.balance_account = balance_account;
    }

    public Integer getIduser() {
        return iduser;
    }

    public void setIduser(Integer iduser) {
        this.iduser = iduser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public Double getBalance_account() {
        return balance_account;
    }

    public void setBalance_account(Double balance_account) {
        this.balance_account = balance_account;
    }

    @Override
    public String toString() {
        return "User{" +
                "id_user=" + iduser +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", user_role='" + user_role + '\'' +
                ", balance_account=" + balance_account +
                '}';
    }
}
