package com.tmquang.score.security.services;

import com.tmquang.score.models.Schedule;
import com.tmquang.score.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    public void saveSchedule(Schedule schedule){
        scheduleRepository.save(schedule);
    }

    public List<Schedule> getAll(){
        return scheduleRepository.findAll();
    }

    public Schedule getById(Integer id){
        return scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Cannot find schedule with id: " + id ));
    }

    public Schedule update(Integer id, Schedule data){
        Optional<Schedule> findScheduleOptional = scheduleRepository.findById(id);

        if(findScheduleOptional.isPresent()){
            Schedule findSchedule = findScheduleOptional.get();
            findSchedule.setSemester(data.getSemester());
            findSchedule.setSubject(data.getSubject());
            findSchedule.setDay(data.getDay());
            findSchedule.setShift(data.getShift());
            findSchedule.setMajor(data.getMajor());
            findSchedule.setTeacher(data.getTeacher());
            findSchedule.setCurrentStudent(data.getCurrentStudent());

            return scheduleRepository.save(findSchedule);
        } else {
            return null;
        }
    }

    public boolean updateStudentAmount(Integer id, String type){
        Schedule data = scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: can not find schedule."));
        if(data == null){
            return false;
        }
        switch (type) {
            case "INCREASE":
                if (data.getCurrentStudent() >= 1) {
                    data.setCurrentStudent(data.getCurrentStudent() - 1);
                    scheduleRepository.save(data);
                    return true;
                } else {
                    return false;
                }
            case "DECREASE":
                if (data.getCurrentStudent() < data.getMaxStudent()) {
                    data.setCurrentStudent(data.getCurrentStudent() + 1);
                    scheduleRepository.save(data);
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    public void delete(Integer id){
        scheduleRepository.deleteById(id);
    }
}
