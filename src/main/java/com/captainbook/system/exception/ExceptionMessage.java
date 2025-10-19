package com.captainbook.system.exception;

public class ExceptionMessage {


    public static class Calendar {

        public static final String CALENDAR_NOT_FOUND_ERROR = "해당 가계부 달력이 존재하지 않습니다.";

    }


    public static class CalendarItem {

        public static final String CALENDAR_ITEM_NOT_FOUND_ERROR = "해당 항목이 존재하지 않습니다.";

    }


    public static class ItemType {

        public static final String ITEM_TYPE_NOT_FOUND_ERROR = "해당 항목 종류가 존재하지 않습니다.";

    }

    public static class Member {

        public static final String MEMBER_NOT_FOUND_ERROR = "해당 유저가 존재하지 않습니다.";
        public static final String MEMBER_ALREADY_EXISTS_ERROR = "이미 존재하는 유저입니다.";

    }

    public static class Auth {

        public static final String USER_OBJECT_TYPE_MISMATCH_ERROR = "사용자 정보를 불러오는데 실패하였습니다.";
        public static final String GOOGLE_OAUTH_ERROR = "Google OAuth 데이터를 읽는데 실패하였습니다.";
        public static final String KAKAO_OAUTH_ERROR = "Kakao OAuth 데이터를 읽는데 실패하였습니다.";
        public static final String NAVER_OAUTH_ERROR = "Naver OAuth 데이터를 읽는데 실패하였습니다.";

    }





}
