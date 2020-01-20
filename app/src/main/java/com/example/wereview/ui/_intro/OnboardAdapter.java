package com.example.wereview.ui._intro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.wereview.R;

import java.util.List;


class OnboardAdapter extends PagerAdapter {

    private Context context;
    private List<OnboardModel> list;

    public OnboardAdapter(Context context, List<OnboardModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View onboardLayout = inflater.inflate(R.layout.onboard_layout, null);

        TextView title = onboardLayout.findViewById(R.id.tvTitleOnboard);
        TextView body = onboardLayout.findViewById(R.id.tvBodyOnboard);
        ImageView image = onboardLayout.findViewById(R.id.ivImageOnboard);

        title.setText(list.get(position).getTitle());
        body.setText(list.get(position).getBody());
        image.setImageResource(list.get(position).getImage());

        container.addView(onboardLayout);

        return onboardLayout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
