package com.gjiazhe.scrollparallaximageview.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gjiazhe.scrollparallaximageview.ScrollParallaxImageView;
import com.gjiazhe.scrollparallaximageview.parallaxstyle.HorizontalAlphaStyle;
import com.gjiazhe.scrollparallaximageview.parallaxstyle.HorizontalMovingStyle;
import com.gjiazhe.scrollparallaximageview.parallaxstyle.HorizontalScaleStyle;

public class HorizontalSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_sample);
        RecyclerView rvMoving = (RecyclerView) findViewById(R.id.rv_horizontal_moving);
        RecyclerView rvScale = (RecyclerView) findViewById(R.id.rv_horizontal_scale);
        RecyclerView rvAlpha = (RecyclerView) findViewById(R.id.rv_horizontal_alpha);

        rvMoving.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvScale.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvAlpha.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        rvMoving.setAdapter(new MyAdapter(new HorizontalMovingStyle()));
        rvScale.setAdapter(new MyAdapter(new HorizontalScaleStyle()));
        rvAlpha.setAdapter(new MyAdapter(new HorizontalAlphaStyle()));
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private ScrollParallaxImageView.ParallaxStyle parallaxStyle;

        MyAdapter(ScrollParallaxImageView.ParallaxStyle parallaxStyle) {
            this.parallaxStyle = parallaxStyle;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_img_horizontal, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.iv.setParallaxStyles(parallaxStyle);
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
            return 15;
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
