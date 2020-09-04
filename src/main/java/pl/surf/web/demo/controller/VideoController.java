package pl.surf.web.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.surf.web.demo.model.Video;
import pl.surf.web.demo.model.requests.VideoRequest;
import pl.surf.web.demo.service.VideoService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/videos")
@CrossOrigin(origins = "{cross.origin}")

public class VideoController {
    private VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Long createVideo(@RequestBody Video video) {
        return videoService.createVideo(video);
    }

    @GetMapping
    public List<Video> getVideos() {
        return videoService.getVideos();
    }

    @PostMapping("/watched")
    public ResponseEntity<String> markVideoAsWatched(HttpServletRequest request, @RequestParam Long videoId) {
        try {
            videoService.markVideoAsWatched(request, videoId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<Video> changeVideoCategory(@RequestBody VideoRequest videoRequest, @RequestParam Long videoId){
        return ResponseEntity.ok(videoService.updateVideo(videoRequest, videoId));
    }

    @PostMapping("/modify/delete")
    public ResponseEntity<String> deleteVideoById(Long videoId){
        if(videoService.deleteVideoById(videoId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

}
