package org.deslre.nodeModule.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "fourthly_node", schema = "test", catalog = "")
public class FourthlyNodeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "identity")
    private String identity;
    @Basic
    @Column(name = "level")
    private String level;
    @Basic
    @Column(name = "exist")
    private Integer exist;

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

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getExist() {
        return exist;
    }

    public void setExist(Integer exist) {
        this.exist = exist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FourthlyNodeEntity that = (FourthlyNodeEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(identity, that.identity) && Objects.equals(level, that.level) && Objects.equals(exist, that.exist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, identity, level, exist);
    }
}
