package com.infodev.sanimaotp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    private String name;

    private String description;

    @OneToMany(mappedBy = "role")
    @JsonBackReference
    private List<UserRole> userRole;

    @Column(name = "status")
    private int status;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "roles_modules",
            joinColumns = @JoinColumn(name = "role_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "module_id", nullable = false))
    private List<Module> modules = new ArrayList<>();

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
        this.userRole = userRole;
    }

    public Role() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<UserRole> userRole) {
        this.userRole = userRole;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public void addModule(Module module) {
        this.modules.add(module);
    }

    public void removeModule(Module module) {
        this.modules.remove(module);
    }
}
