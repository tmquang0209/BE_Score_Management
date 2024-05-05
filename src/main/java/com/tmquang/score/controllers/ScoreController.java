package com.tmquang.score.controllers;

import com.tmquang.score.models.Score;
import com.tmquang.score.security.services.ScoreService;
import com.tmquang.score.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    ScoreService scoreService;

    @PostMapping("/create")
    public ApiResponse<Score> create(@RequestBody Score score) {
        try {
            scoreService.saveScore(score);
            return new ApiResponse<>(true, null, "Score created successfully.");
        } catch (RuntimeException ex) {
            return new ApiResponse<>(false, null, ex.getMessage());
        }
    }

    @GetMapping("/all")
    public ApiResponse<Score> getAll(){
        try{
            List<Score> scoreList = scoreService.getAll();
            return new ApiResponse<>(true, scoreList, "Get score list successful.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @GetMapping("/details/{id}")
    public ApiResponse<Score> getById(@PathVariable Integer id){
        try{
            Score scoreDetails = scoreService.getById(id);
            return new ApiResponse<>(true, List.of(scoreDetails), "Get score details successful.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ApiResponse<Score> updatebyId(@PathVariable Integer id, @RequestBody Score score){
        try{
            scoreService.update(id, score);
            return new ApiResponse<>(true, null, "Score updated successfully.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Score> deleteById(@PathVariable Integer id){
        try{
            scoreService.delete(id);
            return new ApiResponse<>(true, null, "Score deleted successfully.");
        }catch (Exception e){
            return  new ApiResponse<>(false, null, e.getMessage());
        }
    }
}
