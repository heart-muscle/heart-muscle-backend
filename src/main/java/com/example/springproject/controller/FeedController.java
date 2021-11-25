package com.example.springproject.controller;

import com.example.springproject.domain.Feed;
import com.example.springproject.dto.FeedRequestDto;
import com.example.springproject.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FeedController {

    private final FeedRepository feedRepository;

    @PostMapping("/api/feeds")
    public Feed createFeed(@RequestBody FeedRequestDto requestDto) {
        Feed feed = new Feed(requestDto);
        return feedRepository.save(feed);
    }

    @GetMapping("/api/feeds")
    public List<Feed> getFeeds() {
        return feedRepository.findAll();
    }

    @GetMapping("/api/feeds/{id}")
    public Feed getFeed(@PathVariable Long id) {
        return feedRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("없음!")
        );
    }
}
