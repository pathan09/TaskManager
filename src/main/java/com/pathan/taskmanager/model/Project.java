package com.pathan.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiOperation;
import lombok.*;
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
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="project_seq")
    @SequenceGenerator(name = "project_seq", sequenceName = "project_seq", initialValue = 10, allocationSize=1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @Length(min = 5, message = "*Your project name must have at least 5 characters")
    @NotEmpty(message = "*Please provide a project name")
    private String name;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "created_at")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
}
