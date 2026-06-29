package com.example.learning_platform;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEditCourseActivity extends AppCompatActivity {
    private TextInputEditText etTitle, etDesc, etContent;
    private MaterialButton btnSave;
    private boolean isEdit;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_course);

        Toolbar toolbar = findViewById(R.id.toolbarAddEdit);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        etTitle = findViewById(R.id.etCourseTitle);
        etDesc = findViewById(R.id.etCourseDesc);
        etContent = findViewById(R.id.etCourseContent);
        btnSave = findViewById(R.id.btnSaveCourse);

        isEdit = getIntent().getBooleanExtra("isEdit", false);

        if (isEdit) {
            setTitle("Edit Course");
            courseId = getIntent().getIntExtra("course_id", -1);
            etTitle.setText(getIntent().getStringExtra("title"));
            etDesc.setText(getIntent().getStringExtra("desc"));
            etContent.setText(getIntent().getStringExtra("content"));
        } else {
            setTitle("Add New Course");
        }

        btnSave.setOnClickListener(v -> saveCourse());
    }

    private void saveCourse() {
        String title = etTitle.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        if (title.isEmpty() || desc.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Course course = new Course(title, desc, content);
        
        if (isEdit) {
            course.setId(courseId);
            RetrofitClient.getApiService().updateCourse(course).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(AddEditCourseActivity.this, "Course updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddEditCourseActivity.this, "Failed to update: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("API_ERROR", "Update failed", t);
                    Toast.makeText(AddEditCourseActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            RetrofitClient.getApiService().addCourse(course).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(AddEditCourseActivity.this, "Course added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddEditCourseActivity.this, "Failed to add: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("API_ERROR", "Add failed", t);
                    Toast.makeText(AddEditCourseActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
