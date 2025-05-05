package com.example.firstapp;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.activity.ComponentActivity;

import com.google.firebase.firestore.*;

import java.util.*;

public class HomeActivity extends ComponentActivity {

    private FirebaseFirestore db;
    private EditText teacherName, teacherSpec, courseName, studentName;
    private Button btnAddTeacher, btnAddCourse, btnAddStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = FirebaseFirestore.getInstance();

        // Initialize views
        teacherName = findViewById(R.id.editTeacherName);
        teacherSpec = findViewById(R.id.editTeacherSpec);
        courseName = findViewById(R.id.editCourseName);
        studentName = findViewById(R.id.editStudentName);

        btnAddTeacher = findViewById(R.id.btnAddTeacher);
        btnAddCourse = findViewById(R.id.btnAddCourse);
        btnAddStudent = findViewById(R.id.btnAddStudent);

        // Buttons click actions
        btnAddTeacher.setOnClickListener(v -> {
            String name = teacherName.getText().toString().trim();
            String spec = teacherSpec.getText().toString().trim();
            if (!name.isEmpty() && !spec.isEmpty()) {
                addTeacher(UUID.randomUUID().toString(), name, spec);
            } else {
                showToast("Enter teacher details");
            }
        });

        btnAddCourse.setOnClickListener(v -> {
            String cName = courseName.getText().toString().trim();
            if (!cName.isEmpty()) {
                addCourse(UUID.randomUUID().toString(), cName, "T001"); // Demo teacher ID
            } else {
                showToast("Enter course name");
            }
        });

        btnAddStudent.setOnClickListener(v -> {
            String sName = studentName.getText().toString().trim();
            if (!sName.isEmpty()) {
                addStudent(UUID.randomUUID().toString(), sName, Arrays.asList("C001")); // Demo course ID
            } else {
                showToast("Enter student name");
            }
        });
    }

    private void addTeacher(String teacherId, String name, String specialization) {
        Map<String, Object> teacher = new HashMap<>();
        teacher.put("name", name);
        teacher.put("specialization", specialization);

        db.collection("teachers").document(teacherId)
                .set(teacher)
                .addOnSuccessListener(unused -> showToast("Teacher added"))
                .addOnFailureListener(e -> showToast("Error: " + e.getMessage()));
    }

    private void addCourse(String courseId, String courseName, String teacherId) {
        Map<String, Object> course = new HashMap<>();
        course.put("name", courseName);
        course.put("teacherId", teacherId);
        course.put("students", new ArrayList<>());

        db.collection("courses").document(courseId)
                .set(course)
                .addOnSuccessListener(unused -> showToast("Course added"))
                .addOnFailureListener(e -> showToast("Error: " + e.getMessage()));
    }

    private void addStudent(String studentId, String studentName, List<String> courseIds) {
        Map<String, Object> student = new HashMap<>();
        student.put("name", studentName);
        student.put("enrolledCourses", courseIds);

        db.collection("students").document(studentId)
                .set(student)
                .addOnSuccessListener(unused -> {
                    for (String courseId : courseIds) {
                        db.collection("courses").document(courseId)
                                .update("students", FieldValue.arrayUnion(studentId));
                    }
                    showToast("Student added");
                })
                .addOnFailureListener(e -> showToast("Error: " + e.getMessage()));
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
