package com.mereexams.mereexamsexams.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Bilal on 10-Jul-17.
 * Used to store the courses which are displayed in the reyclerview of MainActivity
 * For the storage of complete information of an individual course,
 * see /Models/Course.java
 */

public class CourseCompressed extends RealmObject {
    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("disciplineid")
    private String disciplineId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(String disciplineId) {
        this.disciplineId = disciplineId;
    }

    public class Response {
        @SerializedName("exams_all")
        private List<CourseCompressed> courses;

        @SerializedName("status")
        private String status;

        public List<CourseCompressed> getCourses() {
            return courses;
        }

        public void setCourses(List<CourseCompressed> courses) {
            this.courses = courses;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
