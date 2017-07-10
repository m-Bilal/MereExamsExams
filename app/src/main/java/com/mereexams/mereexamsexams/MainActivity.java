package com.mereexams.mereexamsexams;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    Button buttonSync, buttonRefresh;
    RecyclerView recyclerView;
    private ProgressDialog dialog;

    private Realm realm;

    public static RealmConfiguration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRefresh = (Button) findViewById(R.id.button_refresh);
        buttonSync = (Button) findViewById(R.id.button_sync);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        createRealmConfig();

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

        public MyRecyclerViewAdapter() {

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_recyclerview_main_acitivity, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
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
