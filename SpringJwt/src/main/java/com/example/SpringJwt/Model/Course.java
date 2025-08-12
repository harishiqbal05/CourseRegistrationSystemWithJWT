package com.example.SpringJwt.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;

    private String courseduration;

    private String coursefee;

    //  Users who registered
    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    // Add this method to get user IDs instead of full user objects
    @Transient
    public Set<Long> getUserIds() {
        if (users == null) {
            return new HashSet<>();
        }
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }}
