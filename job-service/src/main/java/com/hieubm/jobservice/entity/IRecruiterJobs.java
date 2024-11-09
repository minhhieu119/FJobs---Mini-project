package com.hieubm.jobservice.entity;

import lombok.*;

public interface IRecruiterJobs {
    Long getTotalCandidates ();

    int getJob_post_id ();

    String getJob_title ();

    int getLocationId ();

    String getCity ();

    String getCountry ();

    int getCompanyId ();

    String getName ();
}