package org.deslre.nodeModule.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "case_table", schema = "test", catalog = "")
public class CaseTableEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "case_number")
    private String caseNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaseTableEntity that = (CaseTableEntity) o;
        return id == that.id && Objects.equals(caseNumber, that.caseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, caseNumber);
    }
}
