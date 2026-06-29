package com.example.learning_platform;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDashboardActivity extends AppCompatActivity {
    private RecyclerView rvCourses;
    private CourseAdapter adapter;
    private List<Course> courseList = new ArrayList<>();
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbarAdmin);
        setSupportActionBar(toolbar);

        rvCourses = findViewById(R.id.rvCoursesAdmin);
        fabAdd = findViewById(R.id.fabAddCourse);

        rvCourses.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CourseAdapter(courseList, true, new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Intent intent = new Intent(AdminDashboardActivity.this, CourseDetailActivity.class);
                intent.putExtra("title", course.getTitle());
                intent.putExtra("content", course.getContent());
                startActivity(intent);
            }

            @Override
            public void onEditClick(Course course) {
                Intent intent = new Intent(AdminDashboardActivity.this, AddEditCourseActivity.class);
                intent.putExtra("isEdit", true);
                intent.putExtra("course_id", course.getId());
                intent.putExtra("title", course.getTitle());
                intent.putExtra("desc", course.getDescription());
                intent.putExtra("content", course.getContent());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Course course) {
                deleteCourse(course.getId());
            }
        });
        rvCourses.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditCourseActivity.class);
            intent.putExtra("isEdit", false);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCourses();
    }

    private void loadCourses() {
        RetrofitClient.getApiService().getCourses().enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    courseList.clear();
                    courseList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Toast.makeText(AdminDashboardActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteCourse(int id) {
        RetrofitClient.getApiService().deleteCourse(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AdminDashboardActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    loadCourses();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AdminDashboardActivity.this, "Delete failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
