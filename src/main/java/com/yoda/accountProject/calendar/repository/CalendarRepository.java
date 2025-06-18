package com.yoda.accountProject.calendar.repository;

import com.yoda.accountProject.calendar.domain.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

}
