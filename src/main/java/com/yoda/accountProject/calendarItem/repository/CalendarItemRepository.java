package com.yoda.accountProject.calendarItem.repository;

import com.yoda.accountProject.calendarItem.domain.CalendarItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalendarItemRepository extends JpaRepository<CalendarItem, Long> {

    Optional<CalendarItem> findByIdAndMemberId(Long calendarId, Long memberId);
    long deleteByIdAndMemberId(Long calendarId, Long memberId);

}
