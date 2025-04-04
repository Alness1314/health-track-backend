package com.alness.health.employee.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alness.health.address.dto.request.AddressRequest;
import com.alness.health.address.dto.response.AddressResponse;
import com.alness.health.address.entity.AddressEntity;
import com.alness.health.address.repository.AddressRepository;
import com.alness.health.address.service.AddressService;
import com.alness.health.common.ApiCodes;
import com.alness.health.common.dto.ResponseDto;
import com.alness.health.common.keys.Filters;
import com.alness.health.common.messages.Messages;
import com.alness.health.config.GenericMapper;
import com.alness.health.employee.dto.request.EmployeeRequest;
import com.alness.health.employee.dto.response.EmployeeResponse;
import com.alness.health.employee.entity.EmployeeEntity;
import com.alness.health.employee.repository.EmployeeRepository;
import com.alness.health.employee.service.EmployeeService;
import com.alness.health.employee.specification.EmployeeSpecification;
import com.alness.health.exceptions.RestExceptionHandler;
import com.alness.health.files.dto.FileResponse;
import com.alness.health.files.entity.FileEntity;
import com.alness.health.files.service.FileService;
import com.alness.health.subsidiary.entity.SubsidiaryEntity;
import com.alness.health.subsidiary.repository.SubsidiaryRepository;
import com.alness.health.users.dto.request.UserRequest;
import com.alness.health.users.dto.response.UserResponse;
import com.alness.health.users.entity.UserEntity;
import com.alness.health.users.repository.UserRepository;
import com.alness.health.users.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubsidiaryRepository subsidiaryRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    GenericMapper mapper;

    @Override
    public List<EmployeeResponse> find(Map<String, String> parameters) {
        return employeeRepository.findAll(filterWithParams(parameters))
                .stream()
                .map(this::mapperDto)
                .toList();
    }

    @Override
    public EmployeeResponse findOne(String id) {
        EmployeeEntity employee = employeeRepository.findOne(filterWithParams(Map.of(Filters.KEY_ID, id)))
                .orElseThrow(() -> new RestExceptionHandler(ApiCodes.API_CODE_404, HttpStatus.INTERNAL_SERVER_ERROR,
                        "Employee not found"));
        return mapperDto(employee);
    }

    @SuppressWarnings("null")
    @Override
    public EmployeeResponse save(EmployeeRequest request) {
        EmployeeEntity employee = mapper.map(request, EmployeeEntity.class);

        try {
            // Guardar dirección del contribuyente primero
            employee.setAddress(saveAndGetAddress(request.getAddress()));

            // Guardar usuario
            employee.setUser(saveAndGetUser(request.getUser()));

            // Guardar subsidiarias
            employee.setSubsidiary(saveAndGetSubsidiaries(request.getSubidiary()));

            if (request.getImageId() != null) {
                FileResponse imageFile = fileService.findOne(request.getImageId());
                employee.setImage(mapper.map(imageFile, FileEntity.class));
            }

            employee = employeeRepository.save(employee);
        } catch (DataIntegrityViolationException e) {
            // Captura de excepciones de integridad de datos
            log.error("Error de integridad de datos al guardar el empleado", e);
            throw new RestExceptionHandler(ApiCodes.API_CODE_409, HttpStatus.CONFLICT,
                    String.format(Messages.DATA_INTEGRITY_ERROR, e.getRootCause().getMessage()));
        } catch (RestExceptionHandler e) {
            // Re-lanzar excepciones específicas ya manejadas
            throw e;
        } catch (Exception e) {
            // Captura de excepciones globales
            log.error("Error global al guardar empleado", e);
            throw new RestExceptionHandler(ApiCodes.API_CODE_500, HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format(Messages.ERROR_TO_SAVE_ENTITY, Messages.EMPLOYEE));
        }
        return mapperDto(employee);
    }

    @Override
    public EmployeeResponse update(String id, EmployeeRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ResponseDto delete(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    private Specification<EmployeeEntity> filterWithParams(Map<String, String> parameters) {
        return new EmployeeSpecification().getSpecificationByFilters(parameters);
    }

    private EmployeeResponse mapperDto(EmployeeEntity source) {
        return mapper.map(source, EmployeeResponse.class);
    }

    @SuppressWarnings("null")
    private AddressEntity saveAndGetAddress(AddressRequest addressRequest) {
        try {
            AddressResponse addressResponse = addressService.save(addressRequest);
            return addressRepository.findById(addressResponse.getId())
                    .orElseThrow(() -> new RuntimeException(
                            String.format(Messages.NOT_FOUND_AFTER_SAVING, Messages.ADDRESS)));
        } catch (DataIntegrityViolationException e) {
            log.error("Error de integridad de datos al guardar la dirección", e);
            throw new RestExceptionHandler(ApiCodes.API_CODE_409, HttpStatus.CONFLICT,
                    String.format(Messages.DATA_INTEGRITY_ERROR, e.getRootCause().getMessage()));
        } catch (Exception e) {
            log.error("Error al guardar la dirección", e);
            throw new RestExceptionHandler(ApiCodes.API_CODE_500, HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format(Messages.ERROR_TO_SAVE_ENTITY, Messages.ADDRESS));
        }
    }

    @SuppressWarnings("null")
    private UserEntity saveAndGetUser(UserRequest userRequest) {
        try {
            UserResponse userResponse = userService.save(userRequest);
            return userRepository.findById(userResponse.getId())
                    .orElseThrow(
                            () -> new RuntimeException(String.format(Messages.NOT_FOUND_AFTER_SAVING, Messages.USER)));
        } catch (DataIntegrityViolationException e) {
            log.error("Error de integridad de datos al guardar el usuario", e);
            throw new RestExceptionHandler(ApiCodes.API_CODE_409, HttpStatus.CONFLICT,
                    String.format(Messages.DATA_INTEGRITY_ERROR, e.getRootCause().getMessage()));
        } catch (Exception e) {
            log.error("Error al guardar el usuario", e);
            throw new RestExceptionHandler(ApiCodes.API_CODE_500, HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format(Messages.ERROR_TO_SAVE_ENTITY, Messages.USER));
        }
    }

    @SuppressWarnings("null")
    private List<SubsidiaryEntity> saveAndGetSubsidiaries(List<String> subsidiaryIds) {
        try {
            return subsidiaryIds.stream()
                    .map(id -> subsidiaryRepository.findById(UUID.fromString(id))
                            .orElseThrow(
                                    () -> new RuntimeException(String.format(Messages.NOT_FOUND, Messages.SUBSIDIARY))))
                    .toList();
        } catch (DataIntegrityViolationException e) {
            log.error("Error de integridad de datos al bucar las sucursales", e);
            throw new RestExceptionHandler(ApiCodes.API_CODE_409, HttpStatus.CONFLICT,
                    String.format(Messages.DATA_INTEGRITY_ERROR, e.getRootCause().getMessage()));
        } catch (Exception e) {
            log.error("Error al guardar las sucursales", e);
            throw new RestExceptionHandler(ApiCodes.API_CODE_500, HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format(Messages.ERROR_TO_SAVE_ENTITY, Messages.SUBSIDIARY));
        }
    }
}
