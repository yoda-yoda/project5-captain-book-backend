package com.captainbook.itemtype.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


// 현재 data.sql 파일을 사용해 앱 시작시 미리 아이템 타입 테이블을 추가하고있다.

@Entity
@Getter
@NoArgsConstructor
public class ItemType {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "TINYINT")
    private Long id;

    @Column(nullable = false, unique = true)
    private String type;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public ItemType(String type) {
        this.type = type;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


}
