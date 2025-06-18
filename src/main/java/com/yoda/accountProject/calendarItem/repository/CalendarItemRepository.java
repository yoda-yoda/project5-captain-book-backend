package com.yoda.accountProject.calendarItem.repository;

import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarItemRepository extends JpaRepository<CalendarItem, Long> {
}
