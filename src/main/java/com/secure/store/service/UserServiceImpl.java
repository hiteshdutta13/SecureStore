package com.secure.store.service;

import com.secure.store.entity.*;
import com.secure.store.entity.util.Gender;
import com.secure.store.entity.util.RequestType;
import com.secure.store.entity.util.Status;
import com.secure.store.modal.*;
import com.secure.store.repository.*;
import com.secure.store.util.DateTimeUtil;
import com.secure.store.util.EmailUtil;
import com.secure.store.util.FileUtil;
import com.secure.store.util.TokenUtil;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserServiceImpl extends GlobalService implements UserService {
    @Autowired
    UserRepository repository;
    @Autowired
    UserRequestRepository userRequestRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    SettingRepository settingRepository;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    UserPlanRepository userPlanRepository;
    @Autowired
    FileRepository fileRepository;

    @Override
    public Response register(UserDTO user) {
        try {
            if (repository.findBy(user.getUsername()).isPresent()) {
                var response = new Response(false);
                response.addMessage("Username already registered.");
                return response;
            } else if (repository.findByEmail(user.getEmail()).isPresent()) {
                var response = new Response(false);
                response.addMessage("Email already registered.");
                return response;
            }
            var registerUser = new User();
            var passwordEncoder = new BCryptPasswordEncoder();
            registerUser.setPassword(passwordEncoder.encode(user.getPassword()));
            registerUser.setEmail(user.getEmail());
            registerUser.setUsername(user.getUsername());
            registerUser.setFirstName(user.getFirstName());
            registerUser.setLastName(user.getLastName());
            registerUser.setStatus(Status.Active);
            registerUser.setCreatedDateTime(DateTimeUtil.currentDateTime());
            registerUser.setUpdateDateTime(registerUser.getCreatedDateTime());
            repository.save(registerUser);
            Optional<Plan> plan = planRepository.findBy("Basic Vault");
            if(plan.isPresent()) {
                var userPlan = new UserPlan();
                var userId = new User();
                userId.setId(registerUser.getId());
                userPlan.setUser(userId);
                userPlan.setStatus(Status.Active);
                userPlan.setPlan(plan.get());
                userPlanRepository.save(userPlan);
            }
            return new Response();
        }catch (Exception e) {
            var response = new Response(false);
            response.addMessage(e.getMessage());
            return response;
        }
    }

    @Override
    public UserDTO get(Long id) {
        return null;
    }

    @Override
    public UserDTO getActive() {
        var userDTO = new UserDTO();
        var user = repository.getReferenceById(this.getUserId());
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setMobileNo(user.getMobileNo());
        userDTO.setGender(user.getGender() != null ? user.getGender().toString(): null);
        userDTO.setStatus(user.getStatus().toString());
        userDTO.setDateOfBirth(DateTimeUtil.formatDate(user.getDateOfBirth(), DateTimeUtil.DATE_FORMAT_UI_EDIT));
        List<Setting> settings = settingRepository.findBy(this.getUserId());
        Optional.ofNullable(settings).orElseGet(Collections::emptyList).stream().sorted(Comparator.comparing(Setting::getKeyword)).forEach(setting -> {
            var settingDTO = new SettingDTO();
            settingDTO.setId(setting.getId());
            settingDTO.setValue(setting.getValue());
            settingDTO.setKeyword(setting.getKeyword());
            userDTO.getSettings().add(settingDTO);
        });
        Optional<UserPlan> optionalUserPlan = userPlanRepository.findBy(this.getUserId());
        if(optionalUserPlan.isPresent()) {
            var plan = optionalUserPlan.get().getPlan();
            var planDTO = new PlanDTO();
            planDTO.setId(plan.getId());
            planDTO.setName(plan.getName());
            planDTO.setPrice(plan.getPrice());
            planDTO.setShare(plan.getShare());
            planDTO.setStorage(plan.getStorage());
            planDTO.setPricingType(plan.getPricingType().toString());
            planDTO.setStorageType(plan.getStorageType().toString());
            planDTO.setStatus(plan.getStatus().toString());
            userDTO.setPlan(planDTO);
            List<File> files = fileRepository.findBy(this.getUserId(), Status.Active, Sort.by(Sort.Direction.DESC, "updatedDateTime"));
            AtomicLong atomicLong = new AtomicLong();
            Optional.ofNullable(files).orElseGet(Collections::emptyList).forEach(file -> {
                atomicLong.set(atomicLong.get()+file.getSize());
            });
            userDTO.setStorageInfo(FileUtil.calculate((plan.getStorage() * 1024 * 1024), atomicLong.get()));
        }
        return userDTO;
    }

    @Override
    public Response resetPassword(String email) {
        var response = new Response();
        Optional<User> optionalUser =  repository.findByEmail(email);
        if(optionalUser.isPresent()) {
            try {
                var userRequest = new UserRequest();
                Optional<UserRequest> optionalUserRequest = userRequestRepository.findBy(optionalUser.get().getId(), RequestType.ResetPassword, Status.Active);
                if (optionalUserRequest.isPresent()) {
                    var existingRequest = optionalUserRequest.get();
                    existingRequest.setStatus(Status.Deleted);
                    userRequestRepository.save(existingRequest);
                }
                userRequest.setUser(optionalUser.get());
                userRequest.setType(RequestType.ResetPassword);
                userRequest.setStatus(Status.Active);
                userRequest.setCreatedDateTime(DateTimeUtil.currentDateTime());
                userRequest.setToken(TokenUtil.generateToken());
                userRequest.setCode(TokenUtil.generate6DigitCode());
                userRequestRepository.save(userRequest);
                MimeMessage message = javaMailSender.createMimeMessage();
                var helper = new MimeMessageHelper(message, true);
                helper.setTo(email);
                helper.setSubject("Password Reset");
                helper.setText(EmailUtil.buildEmailContent(httpServletRequest, optionalUser.get(), userRequest.getToken()), true); // Set the HTML content to true
                javaMailSender.send(message);
                response.addMessage("A password reset link has been sent to your registered email address. Please check your email and follow the instructions to reset your password. If you do not see the email, please check your spam or junk folder.");
            }catch (Exception e) {
                response.setSuccess(false);
                response.addMessage(e.getMessage());
            }
        }else {
            response.setSuccess(false);
            response.addMessage("We couldn't find an account associated with this email address.");
        }
        return response;
    }

    @Override
    public Response validate(String token) {
        var response = new Response();
        Optional<UserRequest> optionalUserRequest = userRequestRepository.findBy(token,  RequestType.ResetPassword, Status.Active);
        if(optionalUserRequest.isEmpty()) {
            response.setSuccess(false);
            response.addMessage("Your password reset link has expired. Please request a new password reset link.");
        }
        return response;
    }

    @Override
    public List<UserDTO> filter(String email) {
        List<UserDTO> userDTOS = new ArrayList<>();
        if(StringUtils.hasText(email)) {
            Optional.ofNullable(repository.filterEmail("%" + email + "%")).orElseGet(Collections::emptyList).stream().filter(user -> user.getId().longValue() != this.getUserId().longValue()).forEach(user -> {
                var userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setUsername(user.getUsername());
                userDTO.setEmail(user.getEmail());
                userDTO.setFirstName(user.getFirstName());
                userDTO.setLastName(user.getLastName());
                userDTOS.add(userDTO);
            });
        }
        return userDTOS;
    }

    @Override
    public Response changePassword(ResetPasswordDTO resetPasswordDTO) {
        var response = new Response();
        if(resetPasswordDTO != null && StringUtils.hasText(resetPasswordDTO.getToken())) {
            if(StringUtils.hasText(resetPasswordDTO.getNewPassword()) && !resetPasswordDTO.getNewPassword().equals(resetPasswordDTO.getConfirmPassword())) {
                response.setSuccess(false);
                response.addMessage("Passwords do not match.");
            }else if(!TokenUtil.validatePassword(resetPasswordDTO.getConfirmPassword())){
                response.setSuccess(false);
                response.addMessage(TokenUtil.getErrorMessage(resetPasswordDTO.getConfirmPassword()));
            }else {
                Optional<UserRequest> optionalUserRequest = userRequestRepository.findBy(resetPasswordDTO.getToken(),  RequestType.ResetPassword, Status.Active);
                if(optionalUserRequest.isPresent()) {
                    var user = optionalUserRequest.get().getUser();
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
                    user.setUpdateDateTime(DateTimeUtil.currentDateTime());
                    repository.save(user);
                    response.addMessage("Password has been reset successfully.");
                }else {
                    response.setSuccess(false);
                    response.addMessage("Your password reset link has expired. Please request a new password reset link.");
                }
            }
        }
        return response;
    }

    @Override
    public Response update(UserDTO userDTO) {
        var response = new Response(false);
        if(userDTO != null && userDTO.getId() != null && userDTO.getId() > 0) {
            User user = repository.getReferenceById(userDTO.getId());
            if(StringUtils.hasText(userDTO.getGender())) {
                user.setGender(Gender.valueOf(userDTO.getGender()));
            }else {
                user.setGender(null);
            }
            if(StringUtils.hasText(userDTO.getDateOfBirth())) {
                user.setDateOfBirth(DateTimeUtil.formatDate(userDTO.getDateOfBirth(), DateTimeUtil.DATE_FORMAT_UI_EDIT));
            }else {
                user.setDateOfBirth(null);
            }
            user.setMobileNo(userDTO.getMobileNo());
            user.setUpdateDateTime(DateTimeUtil.currentDateTime());
            repository.save(user);
            response = new Response();
            response.setPersistId(user.getId());
        }
        return response;
    }

}
