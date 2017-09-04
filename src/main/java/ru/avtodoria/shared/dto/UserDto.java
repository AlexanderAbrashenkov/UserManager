package ru.avtodoria.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class UserDto implements Serializable {
    private int id;
    private String lastName;
    private String firstName;
    private String patronymic;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

    public UserDto() {
    }

    public UserDto(String lastName, String firstName, String patronymic, Date birthDate) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
    }

    public UserDto(int id, String lastName, String firstName, String patronymic, Date birthDate) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
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

    public void copyUser (UserDto user) {
        this.lastName = user.getLastName();
        this.firstName = user.getFirstName();
        this.patronymic = user.getPatronymic();
        this.birthDate = user.getBirthDate();
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (!lastName.equals(userDto.lastName)) return false;
        if (!firstName.equals(userDto.firstName)) return false;
        if (!patronymic.equals(userDto.patronymic)) return false;
        return birthDate.equals(userDto.birthDate);
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
