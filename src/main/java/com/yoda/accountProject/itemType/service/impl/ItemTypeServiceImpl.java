package com.yoda.accountProject.itemType.service.impl;

import com.yoda.accountProject.itemType.domain.ItemType;
import com.yoda.accountProject.itemType.repository.ItemTypeRepository;
import com.yoda.accountProject.itemType.service.ItemTypeService;
import com.yoda.accountProject.system.exception.ExceptionMessage;
import com.yoda.accountProject.system.exception.itemType.ItemTypeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ItemTypeServiceImpl implements ItemTypeService {

    private final ItemTypeRepository itemTypeRepository;

    public ItemType getItemTypeEntityById(Long id){
        return itemTypeRepository.findById(id)
                .orElseThrow(() -> new ItemTypeNotFoundException(ExceptionMessage.ItemType.ITEM_TYPE_NOT_FOUND_ERROR));
    }


}
