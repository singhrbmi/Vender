package hacker.l.venderapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hacker.l.venderapp.R;
import hacker.l.venderapp.activity.DashboardActivity;
import hacker.l.venderapp.adapter.MyCitiesAdapter;
import hacker.l.venderapp.adapter.SuggestCityAdapter;
import hacker.l.venderapp.models.Result;


public class SuggestCityFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static SuggestCityFragment newInstance(String param1, String param2) {
        SuggestCityFragment fragment = new SuggestCityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View view;
    Context context;
    RecyclerView recycleView;
    Result result;
    TextView tv_clear, tv_done;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_city_suggest, container, false);
        init();
        return view;
    }

    private void init() {
        DashboardActivity dashboardActivity = (DashboardActivity) context;
        dashboardActivity.setTitle("Suggested Cities");
        dashboardActivity.addCity(false);
        dashboardActivity.setInfo(false);
        dashboardActivity.setHelp(false);
        tv_clear = (TextView) view.findViewById(R.id.tv_clear);
        tv_done = (TextView) view.findViewById(R.id.tv_done);
        recycleView = (RecyclerView) view.findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        List<Result> resultList = getList();
        SuggestCityAdapter adapter = new SuggestCityAdapter(context, resultList);
        recycleView.setAdapter(adapter);
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private List<Result> getList() {
        List<Result> resultList = new ArrayList<>();
        result = new Result();
        result.setCityName("Bareilly");
        resultList.add(result);
        result = new Result();
        result.setCityName("Pilibhit");
        resultList.add(result);
        result = new Result();
        result.setCityName("Moradabad");
        resultList.add(result);
        result = new Result();
        result.setCityName("Solapur");
        resultList.add(result);
        result = new Result();
        result.setCityName("Ranchi");
        resultList.add(result);
        result = new Result();
        result.setCityName("Gwalior");
        resultList.add(result);
        result = new Result();
        result.setCityName("Aurangabad");
        resultList.add(result);
        result = new Result();
        result.setCityName("Amritsar");
        resultList.add(result);
        result = new Result();
        result.setCityName("Nashik");
        resultList.add(result);
        result = new Result();
        result.setCityName("Ludhiana");
        resultList.add(result);
        result = new Result();
        result.setCityName("Agra");
        resultList.add(result);
        result = new Result();
        result.setCityName("Ghaziabad");
        resultList.add(result);
        result = new Result();
        result.setCityName("Vadodara");
        resultList.add(result);
        result = new Result();
        result.setCityName("Patna");
        resultList.add(result);
        result = new Result();
        result.setCityName("Bhopal");
        resultList.add(result);
        result = new Result();
        result.setCityName("Thane");
        resultList.add(result);
        result = new Result();
        result.setCityName("Indore");
        resultList.add(result);
        result = new Result();
        result.setCityName("Nagpur");
        resultList.add(result);
        result = new Result();
        result.setCityName("Kanpur");
        resultList.add(result);
        result = new Result();
        result.setCityName("Lucknow");
        resultList.add(result);
        result = new Result();
        result.setCityName("Jaipur");
        resultList.add(result);
        result = new Result();
        result.setCityName("Pune");
        resultList.add(result);
        result = new Result();
        result.setCityName("Surat");
        resultList.add(result);
        result = new Result();
        result.setCityName("Kolkata");
        resultList.add(result);
        result = new Result();
        result.setCityName("Chennai");
        resultList.add(result);
        result = new Result();
        result.setCityName("Ahmedabad");
        resultList.add(result);
        result = new Result();
        result.setCityName("Hyderabad");
        resultList.add(result);
        result = new Result();
        result.setCityName("Bangalore");
        resultList.add(result);
        result = new Result();
        result.setCityName("Delhi");
        resultList.add(result);
        result = new Result();
        result.setCityName("Mumbai");
        resultList.add(result);
        return resultList;
    }
}
