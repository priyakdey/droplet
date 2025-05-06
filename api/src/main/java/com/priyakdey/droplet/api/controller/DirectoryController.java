package com.priyakdey.droplet.api.controller;

import com.priyakdey.droplet.api.dto.DirectoryDto;
import com.priyakdey.droplet.api.model.request.NewDirectoryRequest;
import com.priyakdey.droplet.api.model.response.DirectoryListResponse;
import com.priyakdey.droplet.api.service.DirectoryService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
* @author Priyak Dey
*/
@RestController
@RequestMapping("/v1/directories")
public class DirectoryController {

    private final DirectoryService directoryService;

    public DirectoryController(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }

    @GetMapping
    public ResponseEntity<DirectoryListResponse> getDirHierarchy(Principal principal) {
        long ownerId = Long.parseLong(principal.getName());
        // TODO: wrap list in object
        List<DirectoryDto> hierarchy = directoryService.getDirHierarchy(ownerId);
        DirectoryListResponse response = new DirectoryListResponse(hierarchy);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<NewDirectoryResponse> createDir(Principal principal,
                                          @RequestBody NewDirectoryRequest newDirectoryRequest) {
        long ownerId = Long.parseLong(principal.getName());
        String name = newDirectoryRequest.getName();
        ObjectId parentId = new ObjectId();
        DirectoryDto dir = directoryService.createDir(name, parentId, ownerId);

        NewDirectoryResponse responseBody = new NewDirectoryResponse(dir.id(), dir.name(),
                dir.parentId(), dir.ownerId(), dir.createdAt(), dir.updatedAt());
        return ResponseEntity.ok().body(responseBody);
    }

}
