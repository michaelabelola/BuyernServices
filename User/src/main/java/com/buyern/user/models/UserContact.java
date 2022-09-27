package com.buyern.user.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class UserContact implements Serializable {
    @Id
    @OneToOne(optional = false)
    @JoinColumn(name = "id", nullable = false)
    @JsonBackReference
    private User user;
    private String email;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date dob;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserContact contact = (UserContact) o;
        return user != null && Objects.equals(user, contact.user);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
