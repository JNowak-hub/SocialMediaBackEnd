package pl.surf.web.demo.util;

import org.springframework.stereotype.Component;
import pl.surf.web.demo.model.Category;
import pl.surf.web.demo.model.Level;
import pl.surf.web.demo.model.Video;
import pl.surf.web.demo.model.requests.VideoRequest;
import pl.surf.web.demo.service.CategoryService;
import pl.surf.web.demo.service.LevelService;

@Component
public class VideoMapper {

    private CategoryService categoryService;
    private LevelService levelService;

    public VideoMapper(CategoryService categoryService, LevelService levelService) {
        this.categoryService = categoryService;
        this.levelService = levelService;
    }

    public void mapVideoDtoToVideo(VideoRequest videoRequest, Video video) {
        if (videoRequest.getCategoryId() != (null)) {
            Category category = categoryService.findCategoryById(videoRequest.getCategoryId());
            video.setCategory(category);
        }
        if (videoRequest.getLevelId() != (null)) {
            Level level = levelService.getLevelById(videoRequest.getLevelId());
            video.setLevel(level);
        }
        if (videoRequest.getDescription() != (null))
            video.setDescription(videoRequest.getDescription());
        if (videoRequest.getVideoUrl() != (null))
            video.setVideoUrl(videoRequest.getVideoUrl());
        if (videoRequest.getTitle() != (null))
            video.setTitle(videoRequest.getTitle());
    }
}
