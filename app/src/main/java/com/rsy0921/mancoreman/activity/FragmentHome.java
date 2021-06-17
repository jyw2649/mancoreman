package com.rsy0921.mancoreman.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rsy0921.mancoreman.R;
import com.rsy0921.mancoreman.common.PreferenceManager;

public class FragmentHome extends Fragment {

    private Context context;
    FragmentContainerActivity fragmentContainerActivity;



    // 프래그먼트가 액티비티에 attach될 때 호출됩니다.
    // 액티비티와 프래그먼트를 붙일 때 호출되는 메소드입니다.
    // 사용할 액티비티는 MainActivity이기 때문에 getActivity() 메소드를 통해 액티비티를 찾아주어
    // 버튼 클릭 시 액티비티의 메소드를 호출할 수 있도록 정의해줍니다.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);        
        fragmentContainerActivity = (FragmentContainerActivity) getActivity();
    }


    //프래그먼트가 액티비티로부터 떨어지는 단계입니다.
    @Override
    public void onDetach() {
        super.onDetach();
        fragmentContainerActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment_home, container, false);

        Button btnLogout = view.findViewById(R.id.btn_logout);
        TextView textView = view.findViewById(R.id.txt_user_name);
        RelativeLayout layoutNotice = view.findViewById(R.id.layout_notice);
        
        //이벤트
        fragmentContainerActivity.eventRecyclerView(view);

        //추천디자이너
        fragmentContainerActivity.goodDesignerRecyclerView(view);

        //추천스타일
        fragmentContainerActivity.goodStyleRecyclerView(view);

        String sName = PreferenceManager.getString(fragmentContainerActivity, "name");
        textView.setText(sName + "님.");


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                //메인엑티비티에서 실행
                fragmentContainerActivity.onButtonClick(view);
            }
        });

        layoutNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //메인엑티비티에서 실행
                fragmentContainerActivity.onButtonClick(view);
            }
        });

        return view;
    }






}