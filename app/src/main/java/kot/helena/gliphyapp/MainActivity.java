package kot.helena.gliphyapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import kot.helena.gliphyapp.dashboard.ImageBoardAdapter;
import kot.helena.gliphyapp.dashboard.ImageData;

public class MainActivity extends AppCompatActivity {
    ImageBoardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_title);

        initRecyclerView();
        initService();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.dashboard_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new ImageBoardAdapter(this, new ArrayList<ImageData>(20));
        recyclerView.setAdapter(adapter);
    }

    private void initService() {
        GliphyService service = new GliphyService();
        service.getTrending(20);
    }

}
