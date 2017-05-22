package kot.helena.gliphyapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import kot.helena.gliphyapp.api.GliphyService;
import kot.helena.gliphyapp.dashboard.ImageBoardAdapter;
import kot.helena.gliphyapp.dashboard.ImageData;
import kot.helena.gliphyapp.dashboard.event.LoadDataEvent;

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

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onNewData(LoadDataEvent dataEvent) {
        adapter.addAll(dataEvent.getData());
    }

}
