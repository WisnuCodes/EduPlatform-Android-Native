package com.example.learning_platform;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDashboardActivity extends AppCompatActivity {
    private RecyclerView rvCourses;
    private CourseAdapter adapter;
    private List<Course> courseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbarUser);
        setSupportActionBar(toolbar);

        rvCourses = findViewById(R.id.rvCoursesUser);
        rvCourses.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CourseAdapter(courseList, false, new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Intent intent = new Intent(UserDashboardActivity.this, CourseDetailActivity.class);
                intent.putExtra("title", course.getTitle());
                intent.putExtra("content", course.getContent());
                startActivity(intent);
            }
            @Override
            public void onEditClick(Course course) {}
            @Override
            public void onDeleteClick(Course course) {}
        });
        rvCourses.setAdapter(adapter);

        loadCourses();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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
                Toast.makeText(UserDashboardActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
