package com.kms.seft203.dashboard;

import com.kms.seft203.exception.DataNotFoundException;
import com.kms.seft203.user.CustomUserDetails;
import com.kms.seft203.user.User;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboards")
public class DashboardApi {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("")
    public ResponseEntity<List<Dashboard>> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = ((CustomUserDetails) principal).getUser();
        return ResponseEntity.ok().body(dashboardService.getAllByUserId(user.getId()));

    }

    @PostMapping("")
    public ResponseEntity<Dashboard> createDashboard(@RequestBody @Valid SaveDashboardRequest saveDashboardRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = ((CustomUserDetails) principal).getUser();
        return ResponseEntity.status(HttpStatus.CREATED).body(dashboardService.createDashboard(saveDashboardRequest, user));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Dashboard> update(@PathVariable Long id, @RequestBody SaveDashboardRequest request) throws DataNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User user = ((CustomUserDetails) principal).getUser();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(dashboardService.update(id,request,user));
    }
}
