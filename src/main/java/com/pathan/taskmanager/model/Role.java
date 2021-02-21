package com.pathan.taskmanager.model;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Pathan on 10-Feb-21.
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq", initialValue = 10, allocationSize=1)
    @Column(name = "id")
    private Integer id;
    @Column(name = "role", unique = true, nullable = false)
    private String role;
}