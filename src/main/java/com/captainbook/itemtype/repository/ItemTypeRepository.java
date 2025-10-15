package com.captainbook.itemtype.repository;

import com.captainbook.itemtype.domain.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {
}
