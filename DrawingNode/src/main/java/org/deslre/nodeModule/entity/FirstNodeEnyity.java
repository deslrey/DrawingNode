package org.deslre.nodeModule.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor
@Table(name = "first_node", schema = "test", catalog = "")
public class FirstNodeEnyity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "card_id")
    private String cardId;
    @Basic
    @Column(name = "identity")
    private String identity;
    @Basic
    @Column(name = "level")
    private String level;
    @Basic
    @Column(name = "exits")
    private Integer exits;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirstNodeEnyity that = (FirstNodeEnyity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(cardId, that.cardId) && Objects.equals(identity, that.identity) && Objects.equals(level, that.level) && Objects.equals(exits, that.exits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cardId, identity, level, exits);
    }
}
