package shop.heartmuscle.heartmuscle.service;

import shop.heartmuscle.heartmuscle.entity.Feed;
import shop.heartmuscle.heartmuscle.dto.request.FeedRequestDto;
import shop.heartmuscle.heartmuscle.security.UserDetailsImpl;

import java.io.IOException;

public interface FeedService {

    Feed saveFeed(FeedRequestDto feedRequestDto, UserDetailsImpl nowUser) throws IOException;
}
