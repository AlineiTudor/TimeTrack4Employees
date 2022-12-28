package edu.utcn.timetracking.server.employee;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.utcn.timetracking.server.timeTrack.TimeTrack;
import io.swagger.annotations.ApiModelProperty;
import jdk.jfr.Recording;
import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    private Integer id;

    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY,cascade=CascadeType.ALL)
    @JsonManagedReference
    private Collection<TimeTrack> timeTrackSet;

    @Column(unique = true)
    private String name;
    private String hourlyRate;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    @ApiModelProperty(hidden = true)
    private Date enrollDate;
}
