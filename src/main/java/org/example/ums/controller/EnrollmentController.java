package org.example.ums.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ums.dto.enrollment.EnrollmentResponse;
import org.example.ums.mapper.EnrollmentMapper;
import org.example.ums.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.ums.util.RequestConstants.USER_ID_REQUEST_HEADER;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    EnrollmentService enrollmentService;

    EnrollmentMapper enrollmentMapper;

    @GetMapping
    public List<EnrollmentResponse> findAll(
            @RequestHeader(USER_ID_REQUEST_HEADER) int requesterId) {

        return enrollmentMapper.toDto(
                enrollmentService.findAll(requesterId));
    }

    @GetMapping("/{id}")
    public EnrollmentResponse findById(
            @PathVariable(name = "id") int enrollmentId,
            @RequestHeader(USER_ID_REQUEST_HEADER) int requesterId) {

        return enrollmentMapper.toDto(
                enrollmentService.findById(enrollmentId, requesterId));
    }

    @PostMapping
    public EnrollmentResponse create(
            @RequestParam(name = "student") int studentId,
            @RequestParam(name = "course") int courseId,
            @RequestHeader(USER_ID_REQUEST_HEADER) int requesterId) {

        return enrollmentMapper.toDto(
                enrollmentService.create(studentId, courseId, requesterId));
    }

    @PatchMapping("/{id}")
    public EnrollmentResponse update(
            @PathVariable(name = "id") int enrollmentId,
            @RequestParam(name = "student", defaultValue = "0") int studentId,
            @RequestParam(name = "course", defaultValue = "0") int courseId,
            @RequestHeader(USER_ID_REQUEST_HEADER) int requesterId) {

        return enrollmentMapper.toDto(
                enrollmentService.update(enrollmentId, studentId, courseId, requesterId));
    }

    @DeleteMapping("/{id}")
    public void deleteById(
            @PathVariable(name = "id") int enrollmentId,
            @RequestHeader(USER_ID_REQUEST_HEADER) int requesterId) {

        enrollmentService.deleteById(enrollmentId, requesterId);
    }
}
