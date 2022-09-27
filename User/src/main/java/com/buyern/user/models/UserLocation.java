package com.buyern.user.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class UserLocation implements Serializable {
    @Id
    @OneToOne(optional = false)
    @JoinColumn(nullable = false)
    @JsonBackReference
    private User user;
    @Column(nullable = false)
    private String address;
    private String address2;
    private Long city;
    private Long state;
    private Long country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserLocation that = (UserLocation) o;
        return user != null && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
