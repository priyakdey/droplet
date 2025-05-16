package com.priyakdey.droplet.api.controller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * @author Priyak Dey
 */
@RestController
@RequestMapping(path = "/v1/available-timezones", produces = APPLICATION_JSON_VALUE,
        consumes = APPLICATION_JSON_VALUE)
public class AvailableTimezoneController {


    // TODO: sending a raw list is bad for parsing. Wrap in object
    @GetMapping
    public ResponseEntity<List<String>> getAllAvailableTimezones() {
        List<String> timezones = ZoneId.getAvailableZoneIds().stream().sorted().toList();
        return ResponseEntity.ok(timezones);
    }

}
