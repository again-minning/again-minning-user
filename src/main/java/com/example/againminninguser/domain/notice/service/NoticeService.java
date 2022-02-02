package com.example.againminninguser.domain.notice.service;

import com.example.againminninguser.domain.notice.domain.Notice;
import com.example.againminninguser.domain.notice.domain.NoticeRepository;
import com.example.againminninguser.domain.notice.domain.dto.NoticeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public List<NoticeDto> getNoticeList(long offset) {
        List<Notice> noticeList = noticeRepository.findTop5ByIdGreaterThanOrderById(offset);
        return noticeList.stream().map(notice ->
                        NoticeDto.builder()
                                .id(notice.getId())
                                .title(notice.getTitle())
                                .content(notice.getContent())
                                .createdAt(notice.getCreatedAt())
                                .updatedAt(notice.getUpdatedAt()).build())
                .collect(Collectors.toList());
    }

}
