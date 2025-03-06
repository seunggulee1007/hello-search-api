package com.emotionalcart.hellosearchapi.presentation.admin;

import com.emotionalcart.hellosearchapi.application.admin.AdminAllSearchService;
import com.emotionalcart.hellosearchapi.application.admin.AdminSearchResponse;
import com.emotionalcart.hellosearchapi.presentation.common.AllSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/search")
public class AdminSearchController {

    private final AdminAllSearchService adminAllSearchService;

    @GetMapping
    public ResponseEntity<AdminSearchResponse> search(AllSearchCondition searchCondition) throws IOException {
        return ResponseEntity.ok(adminAllSearchService.searchAll(searchCondition));
    }

}
