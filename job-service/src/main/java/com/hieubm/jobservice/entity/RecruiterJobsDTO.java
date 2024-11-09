package com.hieubm.jobservice.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RecruiterJobsDTO {
    private Long totalCandidates;

    private Integer jobPostId;

    private String jobTitle;

    private JobLocation jobLocation;

    private JobCompany jobCompany;

}
