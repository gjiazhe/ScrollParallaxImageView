package com.gjiazhe.scrollparallaximageview.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gjiazhe.scrollparallaximageview.ScrollParallaxImageView;
import com.gjiazhe.scrollparallaximageview.parallaxstyle.VerticalMovingStyle;

public class VerticalSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_sample);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter());
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private VerticalMovingStyle verticalMovingStyle = new VerticalMovingStyle();
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_img_vertical, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.iv.setParallaxStyles(verticalMovingStyle);
            switch (position % 5) {
                case 0 : holder.iv.setImageResource(R.drawable.pic1); break;
                case 1 : holder.iv.setImageResource(R.drawable.pic2); break;
                case 2 : holder.iv.setImageResource(R.drawable.pic3); break;
                case 3 : holder.iv.setImageResource(R.drawable.pic4); break;
                case 4 : holder.iv.setImageResource(R.drawable.pic5); break;
            }
        }

        @Override
        public int getItemCount() {
            return 25;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ScrollParallaxImageView iv;
            ViewHolder(View itemView) {
                super(itemView);
                iv = (ScrollParallaxImageView) itemView.findViewById(R.id.img);
            }
        }
    }
}
