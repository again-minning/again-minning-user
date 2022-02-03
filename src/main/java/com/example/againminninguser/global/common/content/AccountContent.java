package com.example.againminninguser.global.common.content;

public class AccountContent {

    private AccountContent() {}

    public static final String LOGOUT_OK = "로그아웃 성공";

    public static final String USER_NOT_FOUND = "유저를 찾을 수 없습니다.";
    public static final String USER_NOT_FOUND_BY_LOGIN = "아이디 혹은 비밀번호를 확인하세요.";
    public static final String LOGIN_OK = "로그인 성공";

    public static final String EXPIRED_TOKEN = "만료된 토큰입니다.";
    public static final String MALFORMED_TOKEN = "토큰의 형식을 확인해주세요.";
    public static final String EMPTY_TOKEN = "토큰이 비어있습니다.";
    public static final String REFRESH_OK = "성공적으로 토큰을 재발행하였습니다.";

    public static final String SIGN_UP_OK = "회원가입이 성공하였습니다.";
    public static final String INVALID_EMAIL_FORMAT = "이메일 형식을 확인하세요.";
    public static final String INVALID_PASSWORD_FORMAT = "비밀번호 형식을 확인하세요.";
    public static final String DUPLICATED_EMAIL = "이미 가입되어있는 이메일입니다.";

    public static final String PROFILE_UPDATE_OK = "프로필 정보 수정 성공";

    public static final String PASSWORD_UPDATE_OK = "비밀번호를 성공적으로 수정 성공";
    public static final String PASSWORD_CONFIRM_INVALID = "비밀번호가 서로 다릅니다.";
}
