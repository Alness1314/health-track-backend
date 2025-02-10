package com.alness.health.patients.service.impl;

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
import com.alness.health.exceptions.RestExceptionHandler;
import com.alness.health.patients.dto.request.PatientsRequest;
import com.alness.health.patients.dto.response.PatientsResponse;
import com.alness.health.patients.entity.MedicalRecordEntity;
import com.alness.health.patients.entity.PatientsEntity;
import com.alness.health.patients.repository.PatientsRepository;
import com.alness.health.patients.service.PatientsService;
import com.alness.health.patients.specification.PatientsSpecification;
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
public class PatientsServiceImpl implements PatientsService {
    @Autowired
    private PatientsRepository patientsRepository;

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
    private GenericMapper mapper;

    @Override
    public List<PatientsResponse> find(Map<String, String> parameters) {
        return patientsRepository.findAll(filterWithParams(parameters))
                .stream()
                .map(this::mapperDto)
                .toList();
    }

    @Override
    public PatientsResponse findOne(String id) {
        PatientsEntity patients = patientsRepository.findOne(filterWithParams(Map.of(Filters.KEY_ID, id)))
                .orElseThrow(() -> new RestExceptionHandler(ApiCodes.API_CODE_404, HttpStatus.INTERNAL_SERVER_ERROR,
                        "Patient not found"));
        return mapperDto(patients);
    }

    @SuppressWarnings("null")
    @Override
    public PatientsResponse save(PatientsRequest request) {
        PatientsEntity patient = mapper.map(request, PatientsEntity.class);
        try {
            // guardar la direccion
            patient.setAddress(saveAndGetAddress(request.getAddress()));

            // guardar el usuario
            patient.setUser(saveAndGetUser(request.getUser()));

            // Guardar subsidiarias
            patient.setSubsidiary(saveAndGetSubsidiaries(request.getSubsidiaryId()));

            if (request.getMedicalRecord() != null) {
                MedicalRecordEntity medicalRecord = mapper.map(request.getMedicalRecord(),
                        MedicalRecordEntity.class);
                medicalRecord.setPatient(patient);
                patient.setMedicalRecord(medicalRecord);
            }

            patient = patientsRepository.save(patient);

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
        return mapperDto(patient);
    }

    @Override
    public PatientsResponse update(String id, PatientsRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ResponseDto delete(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    private PatientsResponse mapperDto(PatientsEntity source) {
        return mapper.map(source, PatientsResponse.class);
    }

    private Specification<PatientsEntity> filterWithParams(Map<String, String> params) {
        return new PatientsSpecification().getSpecificationByFilters(params);
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
