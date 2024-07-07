package org.deslre.nodeModule.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor
@Table(name = "relation", schema = "test", catalog = "")
public class RelationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "head_node_id")
    private Integer headNodeId;
    @Basic
    @Column(name = "head_level")
    private String headLevel;
    @Basic
    @Column(name = "information")
    private String information;
    @Basic
    @Column(name = "case_number")
    private String caseNumber;
    @Basic
    @Column(name = "tail_node_id")
    private Integer tailNodeId;
    @Basic
    @Column(name = "tail_level")
    private String tailLevel;
    @Basic
    @Column(name = "exits")
    private Integer exits;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationEntity that = (RelationEntity) o;
        return id == that.id && Objects.equals(headNodeId, that.headNodeId) && Objects.equals(headLevel, that.headLevel) && Objects.equals(information, that.information) && Objects.equals(caseNumber, that.caseNumber) && Objects.equals(tailNodeId, that.tailNodeId) && Objects.equals(tailLevel, that.tailLevel) && Objects.equals(exits, that.exits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, headNodeId, headLevel, information, caseNumber, tailNodeId, tailLevel, exits);
    }
}

