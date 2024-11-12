package com.hieubm.jobservice.controller;

import com.hieubm.jobservice.entity.JobPostActivity;
import com.hieubm.jobservice.service.JobPostActivityService;
import com.hieubm.jobservice.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class JobSeekerApplyController {
    private final JobPostActivityService jobPostActivityService;

    private final UsersService usersService;

    @GetMapping("/job-details-apply/{id}")
    public String display (@PathVariable("id") int id, Model model) {
        JobPostActivity jobDetail = jobPostActivityService.getOne(id);
        model.addAttribute("jobDetails", jobDetail);
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "job-details";
    }
}
