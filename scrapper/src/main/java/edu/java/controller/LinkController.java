package edu.java.controller;

import edu.java.dto.request.LinkRequestDto;
import edu.java.dto.response.LinkResponseDto;
import edu.java.dto.response.ListLinkResponseDto;
import edu.java.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/links")
public class LinkController {

    @Autowired
    LinkService linkService;

    @GetMapping
    public ResponseEntity<ListLinkResponseDto> getAllLinks(@RequestHeader("id") Long id) {
        ListLinkResponseDto listLinkResponseDto = linkService.getAll(id);
        return ResponseEntity.ok(listLinkResponseDto);
    }

    @PostMapping
    public ResponseEntity<LinkResponseDto> addLink(@RequestHeader("id") Long id, @RequestBody LinkRequestDto body) {
        LinkResponseDto linkResponseDto = linkService.create(id, body);
        return ResponseEntity.ok(linkResponseDto);
    }

    @DeleteMapping
    public ResponseEntity<LinkResponseDto> deleteLink(@RequestHeader("id") Long id, @RequestBody LinkRequestDto body) {
        LinkResponseDto linkResponseDto = linkService.delete(id, body);
        return ResponseEntity.ok(linkResponseDto);
    }
}
