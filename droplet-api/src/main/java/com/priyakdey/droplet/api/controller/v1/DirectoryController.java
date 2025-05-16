package com.priyakdey.droplet.api.controller.v1;

import com.priyakdey.droplet.api.model.dto.v1.DirectoryDto;
import com.priyakdey.droplet.api.model.response.v1.AllDirectoriesResponse;
import com.priyakdey.droplet.api.service.v1.InodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * @author Priyak Dey
 */
@RestController
@RequestMapping(path = "/v1/directories", consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE)
public class DirectoryController {

    private final InodeService inodeService;

    public DirectoryController(InodeService inodeService) {
        this.inodeService = inodeService;
    }

    @GetMapping
    public ResponseEntity<AllDirectoriesResponse> getAllDirectories(Principal principal) {
        int ownerId = Integer.parseInt(principal.getName());
        List<DirectoryDto> allDirectories = inodeService.getAllDirectories(ownerId);
        AllDirectoriesResponse response = new AllDirectoriesResponse();
        response.setDirectories(allDirectories);
        return ResponseEntity.ok(response);
    }

}
