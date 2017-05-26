package karnix.the.ssn.app.activity.intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import karnix.the.ssn.ssnmachan.R;

public class IntroSlideFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_DRAWABLE_RES_ID = "drawableResId";

    @BindView(R.id.intro_title)
    TextView introTitle;
    @BindView(R.id.intro_image)
    ImageView introImage;
    @BindView(R.id.intro_description)
    TextView introDescription;

    private Unbinder unbinder;

    public static IntroSlideFragment newInstance(String title, int drawableResId, String description) {
        IntroSlideFragment introSlideFragment = new IntroSlideFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESCRIPTION, description);
        args.putInt(ARG_DRAWABLE_RES_ID, drawableResId);
        introSlideFragment.setArguments(args);

        return introSlideFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_intro_slide, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        introTitle.setText(getArguments().getString(ARG_TITLE));
        introImage.setImageResource(getArguments().getInt(ARG_DRAWABLE_RES_ID));
        introDescription.setText(getArguments().getString(ARG_DESCRIPTION));

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

