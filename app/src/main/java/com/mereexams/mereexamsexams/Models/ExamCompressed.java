package com.mereexams.mereexamsexams.Models;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;
import com.mereexams.mereexamsexams.Helpers.ApiClient;
import com.mereexams.mereexamsexams.Helpers.ApiInterface;
import com.mereexams.mereexamsexams.Helpers.GlobalVars;
import com.mereexams.mereexamsexams.MainActivity;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Bilal on 10-Jul-17.
 * Used to store the exams which are displayed in the reyclerview of MainActivity
 * For the storage of complete information of an individual exam,
 * see /Models/Exam.java
 */

public class ExamCompressed extends RealmObject {
    @PrimaryKey
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("disciplineid")
    private String disciplineId;

    // Sync with server
    public static void sync(final Context context) {
        // Creating the progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading exams...");
        progressDialog.show();

        // Making the request
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ExamCompressed.Response> call = apiService.getExamList(
                GlobalVars.API_All_EXAMS,
                GlobalVars.API_VALUE_TOKEN
        );

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                final List<ExamCompressed> exams = response.body().getExams();

                // TODO: Remove this after testing
                Log.i("ExamCompressed", exams.toString() + "");

                Realm realm = Realm.getInstance(MainActivity.config);

                // Delete existing records from Realm
                realm.beginTransaction();
                RealmResults<ExamCompressed> realmResults = realm.where(ExamCompressed.class).findAll();
                realmResults.deleteAllFromRealm();
                realm.commitTransaction();

                // Save to Realm
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        for (ExamCompressed i : exams) {
                            int id = i.getId();
                            ExamCompressed examCompressed = realm.createObject(ExamCompressed.class, id);
                            examCompressed.setName(i.getName());
                            examCompressed.setDisciplineId(i.getDisciplineId());
                        }
                    }
                });
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                progressDialog.show();
                Log.e("ExamCompressed", t.toString());
                Toast.makeText(context, "Not Connected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static List<ExamCompressed> readFromRealm() {
        Realm realm = Realm.getInstance(MainActivity.config);
        List<ExamCompressed> exams = realm.where(ExamCompressed.class).findAll();
        return exams;
    }

    // This method retrieves the saved data or syncs from server if the data is not found
    public static List<ExamCompressed> retrieveOrSync(Context context) {
        if (readFromRealm().size() == 0) {
            sync(context);
        }
        return readFromRealm();
    }

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
        private List<ExamCompressed> exams;

        @SerializedName("status")
        private String status;

        public List<ExamCompressed> getExams() {
            return exams;
        }

        public void setExams(List<ExamCompressed> exams) {
            this.exams = exams;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
