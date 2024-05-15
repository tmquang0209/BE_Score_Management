package com.tmquang.score.security.services;

import com.tmquang.score.models.Year;
import com.tmquang.score.repositories.YearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class YearService {
    @Autowired
    YearRepository yearRepository;

    public Year saveYear(Year year){
        return yearRepository.save(year);
    }

    public List<Year> getAll(){
        return yearRepository.findAll();
    }

    public Year getById(Integer id){
        return yearRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Cannot find year with id: " + id ));
    }

    public Year update(Integer id, Year data){
        Optional<Year> findYearOptional = yearRepository.findById(id);

        if(findYearOptional.isPresent()){
            Year findYear = findYearOptional.get();
            findYear.setYear(data.getYear());
            findYear.setTuition(data.getTuition());
            return yearRepository.save(findYear);
        } else {
            return null;
        }
    }

    public void delete(Integer id){
        yearRepository.deleteById(id);
    }
}
