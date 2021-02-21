package com.pathan.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * Created by Pathan on 10-Feb-21.
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="task_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "task_seq", initialValue = 10, allocationSize=1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "description")
    @Length(min = 5, message = "*Your task description must have at least 5 characters")
    @NotEmpty(message = "*Please provide task description")
    private String description;

    @Column(name = "status")
    @NotEmpty(message = "*Please provide status")
    private String status;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "created_at")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdAt;

    @Column(name = "due_date")
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dueDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Project project;
}
