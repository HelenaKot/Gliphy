package kot.helena.gliphyapp.dashboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import kot.helena.gliphyapp.R;

public class ImageBoardAdapter extends RecyclerView.Adapter<ImageBoardAdapter.ViewHolder> {
    private List<ImageData> data;
    private LayoutInflater inflater;
    private Context context;

    public ImageBoardAdapter(Context context, List<ImageData> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.grid_cell, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context)
                .load(data.get(position).getImageUri())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAll(List<ImageData> images) {
        data.addAll(images);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }
}
