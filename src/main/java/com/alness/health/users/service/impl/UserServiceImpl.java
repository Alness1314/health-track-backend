package com.alness.health.users.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.alness.health.common.dto.ResponseDto;
import com.alness.health.common.keys.Filters;
import com.alness.health.config.GenericMapper;
import com.alness.health.profiles.entity.ProfileEntity;
import com.alness.health.profiles.repository.ProfileRepository;
import com.alness.health.users.dto.CustomUser;
import com.alness.health.users.dto.request.UserRequest;
import com.alness.health.users.dto.response.UserResponse;
import com.alness.health.users.entity.UserEntity;
import com.alness.health.users.repository.UserRepository;
import com.alness.health.users.service.UserService;
import com.alness.health.users.specification.UserSpecification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    GenericMapper mapper;

    @Override
    public UserResponse save(UserRequest request) {
        UserEntity newUser = mapper.map(request, UserEntity.class);
        try {
            List<ProfileEntity> profiles = new ArrayList<>();
            for (String profileId : request.getProfiles()) {
                ProfileEntity profile = profileRepository.findById(UUID.fromString(profileId)).orElse(null);
                profiles.add(profile);
            }
            newUser.setProfiles(profiles);
            newUser.setPassword(passwordEncoder.encode(request.getPassword()));
            newUser = userRepository.save(newUser);
            return mapperDto(newUser);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "Username already exists: " + request.getUsername(), ex);
            }
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Data integrity violation", ex);
        } catch (ResponseStatusException ex) {
            throw ex; // Re-lanzar excepciones ya gestionadas
        } catch (Exception ex) {
            log.error("Error to save user", ex);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error", ex);
        }
    }

    @Override
    public UserResponse findOne(String id) {
        UserEntity findUser = userRepository.findOne(filterWithParameters(Map.of(Filters.KEY_ID, id)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));
        return mapperDto(findUser);
    }

    @Override
    public List<UserResponse> find(Map<String, String> params) {
        return userRepository.findAll(filterWithParameters(params))
                .stream().map(this::mapperDto).toList();
    }

    @Override
    public UserResponse update(String id, UserRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ResponseDto delete(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    private UserResponse mapperDto(UserEntity source) {
        return mapper.map(source, UserResponse.class);
    }

    public Specification<UserEntity> filterWithParameters(Map<String, String> parameters) {
        return new UserSpecification().getSpecificationByFilters(parameters);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Specification<UserEntity> specification = filterWithParameters(Map.of("username", username));
        UserEntity user = userRepository.findOne(specification).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User with name: [%s] not found in database", username)));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getProfiles().forEach(profile -> authorities.add(new SimpleGrantedAuthority(profile.getName())));
        return new CustomUser(user.getUsername(), user.getPassword(), authorities,
                user.getId());
    }

    @Override
    public UserResponse findByUsername(String username) {
        UserEntity findUser = userRepository.findOne(filterWithParameters(Map.of(Filters.KEY_USERNAME, username)))
                .orElse(null);
        if (findUser == null) {
            return null;
        }
        return mapperDto(findUser);
    }

}
