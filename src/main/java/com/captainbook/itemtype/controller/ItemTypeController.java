package com.captainbook.itemtype.controller;

import com.captainbook.itemtype.service.ItemTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ItemTypeController {

    // 현재는 필요하지않아 사용하지않고있다

    private final ItemTypeService ItemTypeService;

}
