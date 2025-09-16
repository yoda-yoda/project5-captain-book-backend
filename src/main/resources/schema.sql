ALTER TABLE calendar
    ADD CONSTRAINT check_calendar_date_max CHECK (date <= '9999-12-31');

ALTER TABLE calendar_item
    ADD CONSTRAINT check_calendar_item_item_amount_max CHECK (item_amount <= 999999999999);