package kot.helena.gliphyapp.dashboard.event;

import java.util.List;

import kot.helena.gliphyapp.dashboard.ImageData;

public class LoadDataEvent {
    private final List<ImageData> data;

    public LoadDataEvent(List<ImageData> data) {
        this.data = data;
    }

    public List<ImageData> getData() {
        return data;
    }
}
