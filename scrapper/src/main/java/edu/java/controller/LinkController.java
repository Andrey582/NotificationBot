package edu.java.controller;

import edu.java.dto.ListLinkResponseDto;
import edu.java.dto.ListLinkResponseDto.LinkResponseDto;
import edu.java.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(listLinkResponseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LinkResponseDto> addLink(@RequestHeader("id") Long id, @RequestBody LinkResponseDto body) {
        LinkResponseDto linkResponseDto = linkService.create(id, body);
        return new ResponseEntity<>(linkResponseDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<LinkResponseDto> deleteLink(@RequestHeader("id") Long id, @RequestBody LinkResponseDto body) {
        LinkResponseDto linkResponseDto = linkService.delete(id, body);
        return new ResponseEntity<>(linkResponseDto, HttpStatus.OK);
    }
}
