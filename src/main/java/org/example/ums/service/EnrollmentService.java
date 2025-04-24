package org.example.ums.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ums.exception.ForbiddenAccessException;
import org.example.ums.exception.NotFoundException;
import org.example.ums.model.Course;
import org.example.ums.model.Enrollment;
import org.example.ums.model.Role;
import org.example.ums.model.User;
import org.example.ums.repository.CourseRepository;
import org.example.ums.repository.EnrollmentRepository;
import org.example.ums.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor

@Service
public class EnrollmentService {
    EnrollmentRepository enrollmentRepository;

    UserRepository userRepository;

    CourseRepository courseRepository;


    public List<Enrollment> findAll(int requesterId) {
        User requester = checkIfUserExistsById(requesterId);

        if (requester.getRole() == Role.STUDENT) {
            return enrollmentRepository.findByStudent_Id(requesterId);
        }

        if (requester.getRole() == Role.ADMIN
        || requester.getRole() == Role.TEACHER) {
            return enrollmentRepository.findAll();
        }

        return List.of();
    }

    public Enrollment findById(int enrollmentId, int requesterId) {
        User requester = checkIfUserExistsById(requesterId);

        Enrollment enrollment = checkIfEnrollmentExistsById(enrollmentId);

        if (requester.getRole() == Role.ADMIN
                || requester.getRole() == Role.TEACHER
                || enrollment.getStudent().getId() == requesterId) {

            return enrollment;
        } else {
            throw new NotFoundException("Enrollment not found.");
        }
    }

    public Enrollment create(int studentId, int courseId, int requesterId) {
        User requester = checkIfUserExistsById(requesterId);

        if (requester.getRole() != Role.ADMIN
                && requester.getRole() != Role.TEACHER) {
            throw new ForbiddenAccessException("Insufficient rights to proceed.");
        }

        User student = checkIfUserExistsById(studentId);
        Course course = checkIfCourseExistsById(courseId);


        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setCreatedAt(LocalDateTime.now());

        enrollmentRepository.save(enrollment);

        return enrollment;
    }

    public Enrollment update(int enrollmentId, int studentId, int courseId, int requesterId) {
        User requester = checkIfUserExistsById(requesterId);

        if (requester.getRole() != Role.ADMIN
                && requester.getRole() != Role.TEACHER) {
            throw new ForbiddenAccessException("Insufficient rights to proceed.");
        }


        Enrollment enrollment = checkIfEnrollmentExistsById(enrollmentId);

        if (studentId != 0) {
            User student = checkIfUserExistsById(studentId);
            enrollment.setStudent(student);
        }

        if (courseId == 0) {
            Course course = checkIfCourseExistsById(courseId);
            enrollment.setCourse(course);
        }


        enrollmentRepository.save(enrollment);

        return enrollment;
    }

    public void deleteById(int enrollmentId, int requesterId) {
        User requester = checkIfUserExistsById(requesterId);

        if (requester.getRole() != Role.ADMIN
                && requester.getRole() != Role.TEACHER) {
            throw new ForbiddenAccessException("Insufficient rights to proceed.");
        }

        enrollmentRepository.deleteById(enrollmentId);
    }

    private Enrollment checkIfEnrollmentExistsById(int enrollmentId) {
        return enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Enrollment with '%d' id not found.", enrollmentId)
                ));
    }

    private User checkIfUserExistsById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with '%d' id not found.", userId)
                ));
    }

    private Course checkIfCourseExistsById(int courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Course with '%d' id not found.", courseId)
                ));
    }
}
