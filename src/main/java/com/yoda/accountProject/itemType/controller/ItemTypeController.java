package com.yoda.accountProject.itemType.controller;

import com.yoda.accountProject.itemType.service.ItemTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;


@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemTypeController {

    private final ItemTypeService ItemTypeService;

}
