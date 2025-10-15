package com.captainbook.itemtype.service.impl;

import com.captainbook.itemtype.domain.ItemType;
import com.captainbook.itemtype.repository.ItemTypeRepository;
import com.captainbook.itemtype.service.ItemTypeService;
import com.captainbook.system.exception.ExceptionMessage;
import com.captainbook.system.exception.itemtype.ItemTypeNotFoundException;
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
