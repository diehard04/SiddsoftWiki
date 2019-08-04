package com.example.siddsoftassignmenttask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.siddsoftassignmenttask.adapter.WikiAdapter;
import com.example.siddsoftassignmenttask.common.Constant;
import com.example.siddsoftassignmenttask.interfaceasmt.AssignmentInerface;
import com.example.siddsoftassignmenttask.model.WikiModel;
import com.example.siddsoftassignmenttask.network.JSONAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AssignmentInerface {

    private RecyclerView mWikiRecycalarView;
    private TextView mMoveToWebView;
    private WikiAdapter mWikiAdapter;
    private List<WikiModel> wikiModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        loadata();
    }

    private void loadata() {
        JSONAsyncTask jsonAsyncTask = new JSONAsyncTask(this);
        jsonAsyncTask.execute(Constant.baseUrl);
    }

    @Override
    public void wikiCallBace(JSONObject jsonObject) {
        wikiModelList.clear();
        System.out.println("wikiCallBace " + jsonObject.toString());
        try {
            JSONObject jsonObject2 = jsonObject.getJSONObject("query");
            JSONArray jsonArray = jsonObject2.getJSONArray("pages");
            for (int i = 0; i < jsonArray.length(); i++) {
                WikiModel wikiModel = new WikiModel();
                wikiModel.setTitle(jsonArray.getJSONObject(i).getString("title"));
                wikiModel.setDescription(jsonArray.getJSONObject(i).getString("description"));
                wikiModel.setEditurl(jsonArray.getJSONObject(i).getString("editurl"));
                wikiModelList.add(wikiModel);
            }
            mWikiAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // i can use butterknife but did not use knowingly
    // there is no image in response
    private void bindView() {
        mMoveToWebView = findViewById(R.id.move_to_webview_tv);
        mWikiRecycalarView = findViewById(R.id.wiki_list_rv);
        mWikiAdapter = new WikiAdapter(wikiModelList, new WikiAdapter.OnItemClicked() {
            @Override
            public void onItemClick(int position) {
                System.out.println(" bindView position = "  + position);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_main, new MainFragment(wikiModelList.get(position).getEditurl()));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mWikiRecycalarView.setLayoutManager(linearLayoutManager);
        mWikiRecycalarView.setAdapter(mWikiAdapter);
    }
}
