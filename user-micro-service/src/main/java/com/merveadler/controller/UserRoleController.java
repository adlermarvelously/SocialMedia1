package com.merveadler.controller;

import com.merveadler.repository.entity.UserRole;
import com.merveadler.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.merveadler.constants.RestApis.*;
import java.util.List;

@RestController
@RequestMapping("/userrole")
@RequiredArgsConstructor
public class UserRoleController {
    private final UserRoleService userRoleService;
    @PostMapping(SAVE)
    public ResponseEntity<List<UserRole>> save(@RequestBody UserRole userRole){
        userRoleService.save(userRole);
        return ResponseEntity.ok(userRoleService.findAllByAuthid(userRole.getAuthid()));
    }

}
