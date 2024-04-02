package com.kuku9.goods.domain.event.entity;

import com.kuku9.goods.domain.event.dto.*;
import com.kuku9.goods.domain.seller.entity.*;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id")
	private Seller seller;

	public Event(EventRequest request, Seller seller) {
		this.title = request.getTitle();
		this.content = request.getContent();
		this.limitNum = request.getLimitNum();
		this.openAt = request.getOpenAt();
		this.seller = seller;
	}

	public void update(EventUpdateRequest request) {
		this.title = request.getTitle();
		this.content = request.getContent();
		this.limitNum = request.getLimitNum();
		this.openAt = request.getOpenAt();
	}

}
