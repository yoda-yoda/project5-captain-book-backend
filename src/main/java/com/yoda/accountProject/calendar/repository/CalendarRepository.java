package com.yoda.accountProject.calendar.repository;

import com.yoda.accountProject.calendar.domain.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    List<Calendar> findByMemberId(Long memberId);
    Optional<Calendar> findByIdAndMemberId(Long calendarId, Long memberId);
    long deleteByIdAndMemberId(Long calendarId, Long memberId);

}
