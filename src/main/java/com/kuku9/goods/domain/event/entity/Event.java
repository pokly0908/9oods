package com.kuku9.goods.domain.event.entity;

import com.kuku9.goods.global.common.entity.*;
import jakarta.persistence.*;
import java.time.*;
import lombok.*;
import org.hibernate.annotations.*;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE event SET deleted_at=CURRENT_TIMESTAMP where id=?")
@SQLRestriction("deleted_at IS NULL")
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Long limitNum;

    @Column
    private LocalDate openAt;

    public Event(String title, String content, Long limitNum, LocalDate openAt) {
        this.title = title;
        this.content = content;
        this.limitNum = limitNum;
        this.openAt = openAt;
    }

    public void update(String title, String content, Long limitNum, LocalDate openAt) {
        this.title = title;
        this.content = content;
        this.limitNum = limitNum;
        this.openAt = openAt;
    }

}
