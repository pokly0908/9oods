package com.kuku9.goods.domain.file.entity;

import com.kuku9.goods.global.common.entity.*;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE file SET deleted_at=CURRENT_TIMESTAMP where id=?")
@SQLRestriction("deleted_at IS NULL")
public class File extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String url;


}
