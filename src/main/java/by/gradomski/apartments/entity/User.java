package by.gradomski.apartments.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    private long id;
    private String loginName;
    private String password;
    private Role role;
    private String mail;
    private LocalDateTime registrationDate;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Gender gender;
    private String phone;
    private String photoBase64;
    private boolean visibility;

    public User(){}
    public User(String loginName, String password, String mail){
        this(loginName, password, mail, Role.USER);
    }
    public User(String loginName, String password, String mail, Role role){
        this.loginName = loginName;
        this.password = password;
        this.mail = mail;
        this.role = role;
        this.registrationDate = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean isVisible() {
        return visibility;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (visibility != user.visibility) return false;
        if (loginName != null ? !loginName.equals(user.loginName) : user.loginName != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (role != user.role) return false;
        if (mail != null ? !mail.equals(user.mail) : user.mail != null) return false;
        if (registrationDate != null ? !registrationDate.equals(user.registrationDate) : user.registrationDate != null)
            return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null) return false;
        if (gender != user.gender) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        return photoBase64 != null ? photoBase64.equals(user.photoBase64) : user.photoBase64 == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (photoBase64 != null ? photoBase64.hashCode() : 0);
        result = 31 * result + (visibility ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName());
        builder.append(": ");
        builder.append("id=");
        builder.append(id);
        builder.append(", loginName=");
        builder.append(loginName);
        builder.append(", password=");
        builder.append(password);
        builder.append(", role=");
        builder.append(role);
        builder.append(", mail=");
        builder.append(mail);
        builder.append(", reg.Date=");
        builder.append(registrationDate);
        builder.append(", firstName=");
        builder.append(firstName);
        builder.append(", lastName=");
        builder.append(lastName);
        builder.append(", b.Day=");
        builder.append(birthday);
        builder.append(", gender=");
        builder.append(gender);
        builder.append(", phone=");
        builder.append(phone);
        builder.append(", photoBase64=");
        builder.append(photoBase64);
        builder.append(", visibility=");
        builder.append(visibility);
        return builder.toString();
    }
}
