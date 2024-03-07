package edu.java.service;

import edu.java.dto.ListLinkResponseDto;
import edu.java.dto.ListLinkResponseDto.LinkResponseDto;
import edu.java.exception.exception.LinkAlreadyTrackedException;
import edu.java.exception.exception.UserHasNoLinkException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

    Map<Long, List<LinkResponseDto>> linkStub = new HashMap<>();

    public ListLinkResponseDto getAll(Long id) {
        if (!linkStub.containsKey(id)) {
            throw new UserHasNoLinkException("Links are empty.");
        }
        return new ListLinkResponseDto(linkStub.get(id), linkStub.get(id).size());
    }

    public LinkResponseDto create(Long id, LinkResponseDto body) {
        if (!linkStub.containsKey(id)) {
            linkStub.put(id, new ArrayList<>());
        }
        Optional<LinkResponseDto> findLinkInArray =
            linkStub.get(id).stream().filter(e -> e.equals(body.url())).findFirst();
        if (findLinkInArray.isPresent()) {
            throw new LinkAlreadyTrackedException("Link already tracked");
        }
        LinkResponseDto linkResponseDto = new LinkResponseDto(id, body.url());
        linkStub.get(id).add(linkResponseDto);
        return linkResponseDto;
    }

    public LinkResponseDto delete(Long id, LinkResponseDto body) {
        if (!linkStub.containsKey(id)) {
            throw new UserHasNoLinkException("User does not have any link.");
        }
        List<LinkResponseDto> linkList = linkStub.get(id);
        LinkResponseDto linkResponseDto =
            linkList.stream().filter(e -> e.url().equals(body.url())).findFirst().orElse(null);
        if (linkResponseDto == null) {
            throw new UserHasNoLinkException("User does not have this link.");
        }
        linkList.remove(body.url());
        return new LinkResponseDto(id, body.url());
    }
}
