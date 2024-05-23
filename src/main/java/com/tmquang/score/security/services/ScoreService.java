package com.tmquang.score.security.services;

import com.tmquang.score.dto.StudentScoreDTO;
import com.tmquang.score.models.Score;
import com.tmquang.score.models.Subject;
import com.tmquang.score.repositories.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {
    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    SubjectService subjectService;

    public void saveScore(Score score){
        scoreRepository.save(score);
    }

    public List<Score> getAll(){
        return scoreRepository.findAll();
    }

    public Score getById(Integer id){
        return scoreRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Cannot find score with id: " + id ));
    }

    public List<StudentScoreDTO> getStudentScoresByScheduleId(Integer scheduleId) {
        return scoreRepository.findStudentScoresByScheduleId(scheduleId);
    }

    public StudentScoreDTO getStudentScoresByStudentIdAndScheduleId(Integer studentId, Integer scheduleId) {
        return scoreRepository.findStudentScoresByStudentIdAndScheduleId(studentId, scheduleId).orElse(null);
    }

    public int updateScoreByScheduleIdAndStudentCode(Integer scheduleId, String studentCode, float midtermTest, float finalTest) {
        return scoreRepository.updateScoreByScheduleIdAndStudentCode(scheduleId, studentCode, midtermTest, finalTest);
    }

    public Score update(Integer id, Score data){
        Optional<Score> findScoreOptional = scoreRepository.findById(id);

        if(findScoreOptional.isPresent()){
            Score findScore = findScoreOptional.get();
            findScore.setMidtermScore(data.getMidtermScore());
            findScore.setFinalScore(data.getFinalScore());
            findScore.setSubject(data.getSubject());

            float rate = data.getSubject().getRate() > 1 ? data.getSubject().getRate()/100 : data.getSubject().getRate();
            float newScore = data.getMidtermScore() * (1 - rate) + data.getFinalScore() * rate;

            findScore.setScore(newScore);

            return scoreRepository.save(findScore);
        } else {
            return null;
        }
    }

    public void delete(Integer id){
        scoreRepository.deleteById(id);
    }
}
