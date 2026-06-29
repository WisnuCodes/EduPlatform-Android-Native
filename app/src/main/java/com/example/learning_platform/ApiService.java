package com.example.learning_platform;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("login.php")
    Call<User> login(@Query("username") String username, @Query("password") String password);

    @POST("register.php")
    Call<Void> register(@Body User user);

    @GET("get_courses.php")
    Call<List<Course>> getCourses();

    @POST("add_course.php")
    Call<Void> addCourse(@Body Course course);

    @PUT("update_course.php")
    Call<Void> updateCourse(@Body Course course);

    @DELETE("delete_course.php")
    Call<Void> deleteCourse(@Query("id") int id);
}
