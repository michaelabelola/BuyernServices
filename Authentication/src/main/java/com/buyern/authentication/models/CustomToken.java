package com.buyern.authentication.models;

import com.buyern.authentication.enums.TokenType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "tokens")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CustomToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(unique = true, nullable = false)
    private String token;
    @Column(unique = true, nullable = false)
    private TokenType type;
    @Column(unique = true, nullable = true)
    private String email;
    @Column(unique = true, nullable = false)
    private String userId;
    @JsonFormat
    private Date expTime;
    @CreationTimestamp
    @JsonFormat
    private Date timeCreated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CustomToken that = (CustomToken) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

