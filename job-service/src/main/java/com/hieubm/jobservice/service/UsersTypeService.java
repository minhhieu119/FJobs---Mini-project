package com.hieubm.jobservice.service;

import com.hieubm.jobservice.entity.UsersType;
import com.hieubm.jobservice.repository.UsersTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersTypeService {

    private final UsersTypeRepository usersTypeRepository;

    public List<UsersType> getAll () {
        return usersTypeRepository.findAll();
    }
}
