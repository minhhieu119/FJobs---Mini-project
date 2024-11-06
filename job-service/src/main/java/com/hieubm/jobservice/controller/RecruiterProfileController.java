package com.hieubm.jobservice.controller;

import com.hieubm.jobservice.entity.RecruiterProfile;
import com.hieubm.jobservice.entity.Users;
import com.hieubm.jobservice.repository.UsersRepository;
import com.hieubm.jobservice.service.RecruiterProfileService;
import com.hieubm.jobservice.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final UsersRepository usersRepository;

    private final RecruiterProfileService recruiterProfileService;

    @GetMapping("/")
    public String recruiterProfile (Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();

            // Tìm user theo email
            Users users = usersRepository.findByEmail(currentUsername)
                    .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy username"));

            // Lấy thông tin RecruiterProfile của user
            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(users.getUserId());

            // Nếu không có thông tin RecruiterProfile, tạo đối tượng mới hoặc có thể thông báo cho người dùng
            RecruiterProfile profile = recruiterProfile.orElse(new RecruiterProfile());

            // Thêm đối tượng profile vào model
            model.addAttribute("profile", profile);
        }

        return "recruiter_profile";  // Trả về template tương ứng
    }

    @PostMapping("/addNew")
    public String addNew (RecruiterProfile recruiterProfile, @RequestParam("image")MultipartFile multipartFile
                            , Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(()  -> new UsernameNotFoundException(
                    " Không tìm thấy username"));
            recruiterProfile.setUserId(users);
            recruiterProfile.setUserAccountId(users.getUserId());
        }
        model.addAttribute("profile", recruiterProfile);
        String fileName = "";
        if (!multipartFile.getOriginalFilename().equals("")) {
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            recruiterProfile.setProfilePhoto(fileName);
        }
        RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);
        String updloadDir = "photos/recruiter/" + savedUser.getUserAccountId();
        try {
            FileUploadUtil.saveFile(updloadDir, fileName, multipartFile);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "redirect:/dashboard/";
    }
}
