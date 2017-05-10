package com.atlas.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.atlas.AtlasApplication;
import com.atlas.R;
import com.atlas.helper.AtlasConstant;


public class SettingFragment extends Fragment {

    private EditText mEditTextMapKey;
    private Button mButtonSave;
    private onSettingFragmentInteraction mListener;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        mEditTextMapKey = (EditText) rootView.findViewById(R.id.edt_Mapkey);
        mButtonSave = (Button) rootView.findViewById(R.id.btn_save);
        if(AtlasApplication.appPrefs.contains(AtlasConstant.USER_KEY)){
         String userKey = AtlasApplication.appPrefs.getString(AtlasConstant.USER_KEY,"");
            if (!userKey.isEmpty() && userKey.equals(AtlasConstant.APP_KEY)  ) {
                mEditTextMapKey.setText(userKey);
            }else {
                mEditTextMapKey.setText("");
            }
        }
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userKey = mEditTextMapKey.getText().toString().trim();
                if (!userKey.isEmpty() && userKey.equals(AtlasConstant.APP_KEY)  ) {
                    AtlasApplication.appEditor.putString(AtlasConstant.USER_KEY,userKey).commit();
                    mListener.onSettingFragmentInteraction(true);
                } else {
                    AtlasApplication.appEditor.putString(AtlasConstant.USER_KEY,userKey).commit();
                    Snackbar.make(mEditTextMapKey, getResources().getString(R.string.map_key_error), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onSettingFragmentInteraction) {
            mListener = (onSettingFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface onSettingFragmentInteraction {
        // TODO: Update argument type and name
        void onSettingFragmentInteraction(boolean flag);
    }

}
