package com.ziola.shelter.role;

import com.ziola.shelter.workers.Worker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {

  @Id
  @Column(name = "role_id")
  private Integer id;

  @Column(name = "worker_role")
  private String role;

  @OneToMany(mappedBy = "role")
  private List<Worker> workers;
}
