package com.example.android.popularmoviesapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmoviesapp.Model.Review;
import com.example.android.popularmoviesapp.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private static final String TAG = ReviewAdapter.class.getSimpleName();
    private ArrayList<Review> reviews;

    public ReviewAdapter(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_card, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Review review = reviews.get(i);
        viewHolder.reviewAuthor.setText(review.getAuthor());
        viewHolder.reviewContent.setText(review.getContent());
        viewHolder.reviewReadMore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(review.getUrl()));
                v.getContext().startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_review_author)
        TextView reviewAuthor;
        @BindView(R.id.tv_review_content)
        TextView reviewContent;
        @BindView(R.id.tv_review_read_more)
        TextView reviewReadMore;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
