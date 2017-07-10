package com.mereexams.mereexamsexams;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mereexams.mereexamsexams.Models.Exam;
import com.mereexams.mereexamsexams.Models.ExamCompressed;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    Button buttonSync, buttonRefresh;
    RecyclerView recyclerView;
    private ProgressDialog dialog;
    private RecyclerView.Adapter adapter;
    private Realm realm;
    private List<ExamCompressed> exams;

    public static RealmConfiguration config;

    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        buttonRefresh = (Button) findViewById(R.id.button_refresh);
        buttonSync = (Button) findViewById(R.id.button_sync);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        createRealmConfig();
        addActionListeners();

        exams = ExamCompressed.retrieveOrSync(this);

        adapter = new MyRecyclerViewAdapter(exams);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    void addActionListeners() {
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exams = ExamCompressed.readFromRealm();

                adapter = new MyRecyclerViewAdapter(exams);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }
        });

        buttonSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExamCompressed.sync(context);

            }
        });
    }

    void createRealmConfig() {
        // initialize Realm
        Realm.init(getApplicationContext());

        // create your Realm configuration
        config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .name("myCustomRealm")
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

        List<ExamCompressed> exams;

        public MyRecyclerViewAdapter(List<ExamCompressed> exams) {
            this.exams = exams;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_recyclerview_main_acitivity, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textViewDisciplineId.setText("" + exams.get(position).getDisciplineId());
            holder.textViewName.setText(exams.get(position).getName());
            holder.textViewId.setText("" + exams.get(position).getId());
        }

        @Override
        public int getItemCount() {
            if (exams == null) return 0;
            else return exams.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textViewId, textViewDisciplineId, textViewName;

            public MyViewHolder(View view) {
                super(view);
                textViewDisciplineId = (TextView) view.findViewById(R.id.textview_discipline_id_recyclerview_main_activity);
                textViewId = (TextView) view.findViewById(R.id.textview_id_recyclerview_main_activity);
                textViewName = (TextView) view.findViewById(R.id.textview_name_recyclerview_main_activity);
            }
        }
    }
}
