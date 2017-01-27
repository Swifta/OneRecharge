package com.swifta.onerecharge.agent.agentquickrecharge;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.swifta.onerecharge.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RechargeResponseFragment extends DialogFragment {
    @BindView(R.id.recharge_response_image)
    ImageView imageView;
    @BindView(R.id.successful_recharge)
    TextView successText;

    private static final String MESSAGE = "message";
    private static final String STATUS = "status";

    public RechargeResponseFragment() {
        // Required empty public constructor
    }

    public static RechargeResponseFragment newInstance(String message, int
            status) {
        RechargeResponseFragment successfulFragment = new
                RechargeResponseFragment();
        Bundle argument = new Bundle();
        argument.putString(MESSAGE, message);
        argument.putInt(STATUS, status);
        successfulFragment.setArguments(argument);
        return successfulFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recharge_successful, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        int status = getArguments().getInt(STATUS);

        if (status == 0) {
            imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(),
                    R.drawable.ic_highlight_off_red_700_36dp));
        } else if (status == 1) {
            imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(),
                    R.drawable.ic_check_circle_green_700_36dp));
        }

        String message = getArguments().getString(MESSAGE);
        successText.setText(message);
    }

    @OnClick(R.id.dismiss_success_fragment)
    void dismissFragment() {
        dismiss();
    }
}
