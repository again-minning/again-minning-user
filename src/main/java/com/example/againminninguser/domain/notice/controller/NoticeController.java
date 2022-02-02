package com.example.againminninguser.domain.notice.controller;

import com.example.againminninguser.domain.notice.domain.dto.NoticeDto;
import com.example.againminninguser.domain.notice.service.NoticeService;
import com.example.againminninguser.global.common.response.CustomResponseEntity;
import com.example.againminninguser.global.common.response.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("")
    public CustomResponseEntity<List<NoticeDto>> getNotice(@PathParam("lastId") long lastId) {
        return new CustomResponseEntity<> (
                Message.of(HttpStatus.OK, "공지사항 조회 성공"),
                noticeService.getNoticeList(lastId)
        );
    }
}
