package com.kms.seft203.report;

import com.kms.seft203.user.CustomUserDetails;
import com.kms.seft203.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportApi {

    @Autowired
    private ReportService reportService;

    @GetMapping("_countBy/{collection}/{field}")
    public ResponseEntity<Map<String, Integer>> countBy(@PathVariable String collection, @PathVariable String field) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = ((CustomUserDetails) principal).getUser();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(reportService.countFieldByCollection(collection,field,user));
    }
}
