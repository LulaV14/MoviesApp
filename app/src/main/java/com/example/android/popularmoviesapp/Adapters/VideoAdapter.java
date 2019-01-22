package com.example.android.popularmoviesapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmoviesapp.Model.Video;
import com.example.android.popularmoviesapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private static final String TAG = VideoAdapter.class.getSimpleName();
    private final VideoOnClickHandler videoOnClickHandler;
    private ArrayList<Video> videos;

    public VideoAdapter(ArrayList<Video> videos, VideoOnClickHandler clickHandler) {
        this.videos = videos;
        this.videoOnClickHandler = clickHandler;
    }

    public interface VideoOnClickHandler {
        void onClick(int video_position);
    }


    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_video_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Video video = videos.get(i);
        viewHolder.videoName.setText(video.getName());
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_video_name)
        TextView videoName;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
          videoOnClickHandler.onClick(getAdapterPosition());
        }
    }
}
