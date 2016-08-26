package com.swifta.onerecharge;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swifta.onerecharge.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgentRechargeFragment extends Fragment {


    public AgentRechargeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agent_recharge, container, false);
    }

}
