package com.sender.collectverythingapi.controllers;

import com.sender.collectverythingapi.classes.Theme;
import com.sender.collectverythingapi.services.ScriptExecutor;
import com.sender.collectverythingapi.services.Transfert;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("script")
@RequiredArgsConstructor
public class ScriptController {

    @Autowired
    private Transfert transfert;

    @GetMapping("/allBuild")
    public ResponseEntity<String> transfertByMultipleCmd() throws Exception {
        transfert.transfertAllFilesAndFolder();
        return ResponseEntity.ok("ok");
    }

}


