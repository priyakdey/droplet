package com.priyakdey.droplet.api.controller;

import com.priyakdey.droplet.api.dto.DirectoryDto;
import com.priyakdey.droplet.api.model.request.NewDirectoryRequest;
import com.priyakdey.droplet.api.model.response.DirectoryListResponse;
import com.priyakdey.droplet.api.model.response.NewDirectoryResponse;
import com.priyakdey.droplet.api.service.DirectoryService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<NewDirectoryResponse> createDir(
            @RequestBody NewDirectoryRequest newDirectoryRequest, Principal principal) {
        long ownerId = Long.parseLong(principal.getName());
        String name = newDirectoryRequest.getName();
        ObjectId parentId = new ObjectId(newDirectoryRequest.getParentId());
        DirectoryDto dir = directoryService.createDir(name, parentId, ownerId);

        NewDirectoryResponse responseBody = new NewDirectoryResponse(dir.id(), dir.name(),
                dir.parentId(), dir.ownerId(), dir.createdAt(), dir.updatedAt());
        return ResponseEntity.created(URI.create("")).body(responseBody);
    }

}
