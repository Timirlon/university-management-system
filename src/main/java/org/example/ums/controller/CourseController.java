package org.example.ums.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ums.dto.course.CourseRequest;
import org.example.ums.dto.course.CourseResponse;
import org.example.ums.mapper.CourseMapper;
import org.example.ums.model.Course;
import org.example.ums.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.ums.util.RequestConstants.USER_ID_REQUEST_HEADER;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor

@RestController
@RequestMapping("/courses")
public class CourseController {
    CourseService courseService;

    CourseMapper courseMapper;

    @GetMapping
    public List<CourseResponse> findAll() {
        return courseMapper.toDto(
                courseService.findAll());
    }

    @GetMapping("/{id}")
    public CourseResponse findById(@PathVariable int id) {
        return courseMapper.toDto(
                courseService.findById(id));
    }

    @PostMapping
    public CourseResponse create(
            @RequestBody CourseRequest courseRequest,
            @RequestHeader(USER_ID_REQUEST_HEADER) int requesterId) {

        Course course = courseMapper.fromDto(courseRequest);

        return courseMapper.toDto(
                courseService.create(course, requesterId));
    }

    @PatchMapping("/{id}")
    public CourseResponse update(
            @PathVariable(name = "id") int courseId,
            @RequestBody CourseRequest courseRequest,
            @RequestHeader(USER_ID_REQUEST_HEADER) int requesterId) {

        Course course = courseMapper.fromDto(courseRequest);

        return courseMapper.toDto(
                courseService.update(courseId, course, requesterId));
    }

    @DeleteMapping("/{id}")
    public void deleteById(
            @PathVariable(name = "id") int courseId,
            @RequestHeader(USER_ID_REQUEST_HEADER) int requesterId) {

        courseService.deleteById(courseId, requesterId);
    }
}
