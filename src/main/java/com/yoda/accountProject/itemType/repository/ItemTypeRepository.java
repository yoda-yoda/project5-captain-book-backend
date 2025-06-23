package com.yoda.accountProject.itemType.repository;

import com.yoda.accountProject.itemType.domain.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemTypeRepository extends JpaRepository<ItemType, Long> {
}
