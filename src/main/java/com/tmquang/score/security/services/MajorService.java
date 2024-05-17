package com.tmquang.score.security.services;

import com.tmquang.score.models.Major;
import com.tmquang.score.payload.request.MajorRequest;
import com.tmquang.score.repositories.MajorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MajorService {
    @Autowired
    MajorRepository majorRepository;

    // Save a Major
    public Major saveMajor(Major major) {
        majorRepository.save(major);
        return major;
    }

    // Retrieve all Majors
    public List<Major> getAll() {
        return majorRepository.findAll();
    }

    public Major getById(Integer id) {
        return majorRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Cannot find major with id: " + id));
    }

    // Update a Major
    public Major update(Integer id, MajorRequest data) {
        Optional<Major> findMajorOptional = majorRepository.findById(id);

        if (findMajorOptional.isPresent()) {
            Major findMajor = findMajorOptional.get();
            findMajor.setMajorCode(data.getMajorCode());
            findMajor.setMajorName(data.getMajorName());

            return majorRepository.save(findMajor);
        } else {
            return null;
        }
    }

    // Delete a Major by id
    public void delete(Integer id) {
        majorRepository.deleteById(id);
    }
}
