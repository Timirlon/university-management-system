package org.example.ums.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ums.exception.ForbiddenAccessException;
import org.example.ums.exception.NotFoundException;
import org.example.ums.model.Course;
import org.example.ums.model.Role;
import org.example.ums.model.User;
import org.example.ums.repository.CourseRepository;
import org.example.ums.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor

@Service
public class CourseService {
    CourseRepository courseRepository;

    UserRepository userRepository;


    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(int courseId) {
        return checkIfCourseExistsById(courseId);
    }

    public Course create(Course course, int requesterId) {
        User requester = checkIfUserExistsById(requesterId);

        if (requester.getRole() != Role.ADMIN
                && requester.getRole() != Role.TEACHER) {
            throw new ForbiddenAccessException("Insufficient rights to proceed.");
        }

        courseRepository.save(course);

        return course;
    }

    public Course update(int courseId, Course updateCourse, int requesterId) {
        User requester = checkIfUserExistsById(requesterId);

        if (requester.getRole() != Role.ADMIN
                && requester.getRole() != Role.TEACHER) {
            throw new ForbiddenAccessException("Insufficient rights to proceed.");
        }

        Course oldCourse = checkIfCourseExistsById(courseId);

        if (updateCourse.getDescription() == null
                || updateCourse.getDescription().isBlank()) {
            updateCourse.setDescription(oldCourse.getDescription());
        }

        if (updateCourse.getTitle() == null
                || updateCourse.getTitle().isBlank()) {
            updateCourse.setTitle(oldCourse.getTitle());
        }


        courseRepository.save(updateCourse);
        return updateCourse;
    }

    public void deleteById(int courseId, int requesterId) {
        User requester = checkIfUserExistsById(requesterId);

        if (requester.getRole() != Role.ADMIN
                && requester.getRole() != Role.TEACHER) {
            throw new ForbiddenAccessException("Insufficient rights to proceed.");
        }

        courseRepository.deleteById(courseId);
    }


    private Course checkIfCourseExistsById(int courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with '%d' id not found.", courseId)
                ));
    }

    private User checkIfUserExistsById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with '%d' id not found.", userId)
                ));
    }
}
