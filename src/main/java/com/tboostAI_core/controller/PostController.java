package com.tboostAI_core.controller;

import com.tboostAI_core.dto.PostDTO;
import com.tboostAI_core.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<PostDTO> getPostByVehicleId(@PathVariable("vehicleId") Long vehicleId) {
        PostDTO postDTO = postService.getPostByVehicleId(vehicleId);
        return postDTO != null ? ResponseEntity.ok(postDTO) : ResponseEntity.notFound().build();
    }
}
