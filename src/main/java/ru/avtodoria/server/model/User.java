package ru.avtodoria.server.model;

import ru.avtodoria.shared.dto.UserDto;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_db")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String lastName;
    private String firstName;
    private String patronymic;
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    public User() {
    }

    public User(String lastName, String firstName, String patronymic, Date birthDate) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
    }

    public User(int id, String lastName, String firstName, String patronymic, Date birthDate) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
    }

    public User(UserDto userDto) {
        this.id = userDto.getId();
        this.lastName = userDto.getLastName();
        this.firstName = userDto.getFirstName();
        this.patronymic = userDto.getPatronymic();
        this.birthDate = userDto.getBirthDate();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!lastName.equals(user.lastName)) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!patronymic.equals(user.patronymic)) return false;
        return birthDate.equals(user.birthDate);
    }

    @Override
    public int hashCode() {
        int result = lastName.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + patronymic.hashCode();
        result = 31 * result + birthDate.hashCode();
        return result;
    }
}
