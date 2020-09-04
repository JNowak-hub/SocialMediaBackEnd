package pl.surf.web.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.surf.web.demo.configuration.JwtUtil;
import pl.surf.web.demo.exceptions.VideoException;
import pl.surf.web.demo.model.UserApp;
import pl.surf.web.demo.model.Video;
import pl.surf.web.demo.model.requests.VideoRequest;
import pl.surf.web.demo.repository.UserRepo;
import pl.surf.web.demo.repository.VideoRepo;
import pl.surf.web.demo.util.VideoMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {
    private VideoRepo videoRepo;

    private UserService userService;
    private UserRepo userRepo;
    private CategoryService categoryService;
    private LevelService levelService;
    private VideoMapper videoMapper;

    private JwtUtil util;

    public VideoService(VideoRepo videoRepo, UserService userService, UserRepo userRepo, JwtUtil util,CategoryService categoryService, LevelService levelService, VideoMapper videoMapper) {
        this.videoRepo = videoRepo;
        this.userService = userService;
        this.userRepo = userRepo;
        this.util = util;
        this.categoryService = categoryService;
        this.levelService = levelService;
        this.videoMapper = videoMapper;
    }

    @Transactional
    public Long createVideo(Video video) {
        return videoRepo.save(video).getVideoId();
    }

    public List<Video> getVideos() {
        return videoRepo.findAll();
    }

    @Transactional
    public void markVideoAsWatched(HttpServletRequest request, Long videoId) {

        Long userId = util.getUserIdFromRequest(request);

        UserApp user = userRepo.findById(userId).get();

        final Video video = videoRepo.getOne(videoId);
        video.getUsers().add(user);
        userRepo.save(user);
    }

    private Boolean checkIfVideoExists(Long videoId){
        if (videoRepo.existsById(videoId)){
            return true;
        }
        throw new VideoException("Video with id: " + videoId + " Doesn't exists");
    }

    public Video updateVideo(VideoRequest videoRequest, Long videoId){
        Optional<Video> optionalVideo = videoRepo.findById(videoId);

        if(optionalVideo.isEmpty()){
            throw new VideoException("Video with id: " + videoId + " doesn't exist");
        }

        Video video = optionalVideo.get();

        videoMapper.mapVideoDtoToVideo(videoRequest, video);

        videoRepo.save(video);

        return video;
    }

    @Transactional
    public Boolean deleteVideoById(Long videoId){
        if(videoRepo.existsById(videoId)){
            videoRepo.deleteById(videoId);
            return true;
        }
        return false;
    }

}
