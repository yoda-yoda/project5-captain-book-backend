package com.yoda.accountProject.itemType.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    public ItemType(String type) {
        this.type = type;
    }


}
