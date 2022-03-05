package com.ziola.shelter.workers.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class Role {

  @Id
  @Column(name = "roleId")
  private Integer id;
  @Column(name = "workersRole")
  private String role;

  public Role(Integer id, String role) {
    this.id = id;
    this.role = role;
  }

  @OneToMany(mappedBy = "role")
  private List<Worker> workers;

  public List<Worker> getWorkers() {
    return workers;
  }

  public void setWorkers(List<Worker> workers) {
    this.workers = workers;
  }

  public Role() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
