package com.hieubm.jobservice.service;

import com.hieubm.jobservice.entity.*;
import com.hieubm.jobservice.repository.JobPostActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepository;

    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        return jobPostActivityRepository.save(jobPostActivity);
    }

    public List<RecruiterJobsDTO> getRecruiterJobs(int recruiter) {
        List<IRecruiterJobs> recruiterJobsDTOs = jobPostActivityRepository.getRecruiterJobs(recruiter);
        List<RecruiterJobsDTO> recruiterJobsDTOList = new ArrayList<>();

        for (IRecruiterJobs recruiterJobs : recruiterJobsDTOs) {
            JobLocation location = new JobLocation(recruiterJobs.getLocationId(), recruiterJobs.getCity(), recruiterJobs.getCountry());
            JobCompany company = new JobCompany(recruiterJobs.getCompanyId(), recruiterJobs.getName(), "");
            recruiterJobsDTOList.add(new RecruiterJobsDTO(recruiterJobs.getTotalCandidates(), recruiterJobs.getJob_post_id(),
                    recruiterJobs.getJob_title(), location, company));
        }
        return recruiterJobsDTOList;
    }
}
