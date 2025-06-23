package com.yoda.accountProject.itemType.service;

import com.yoda.accountProject.itemType.domain.ItemType;
import com.yoda.accountProject.system.exception.ExceptionMessage;
import com.yoda.accountProject.system.exception.itemType.ItemTypeNotFoundException;

public interface ItemTypeService {

    ItemType getItemTypeEntityById(Long id);

}
