package com.hieubm.jobservice.service;

import com.hieubm.jobservice.entity.JobSeekerProfile;
import com.hieubm.jobservice.repository.JobSeekerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobSeekerProfileService {

    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    public Optional<JobSeekerProfile> getOne (Integer id) {
        return jobSeekerProfileRepository.findById(id);
    };

    public JobSeekerProfile add(JobSeekerProfile jobSeekerProfile) {
        return jobSeekerProfileRepository.save(jobSeekerProfile);
    }

}
