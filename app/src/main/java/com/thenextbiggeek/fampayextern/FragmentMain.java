package com.thenextbiggeek.fampayextern;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.thenextbiggeek.fampayextern.databinding.FragmentMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class FragmentMain extends Fragment {
    private FragmentMainBinding binding;
    private ArrayList<CardGroup> cardGroupArrayList;


    public FragmentMain() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardGroupArrayList = new ArrayList<CardGroup>();
        if (isNetworkAvailable()) {
            fetchAPI();
        }
    }

    private void fetchAPI() {
        // Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        // Initialize a new JsonObjectRequest instance (Volley) and parse it into POJOs with GSON
        String API_URL = "https://run.mocky.io/v3/fefcfbeb-5c12-4722-94ad-b8f92caad1ad";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                API_URL,
                null,
                response -> {
                    try {

                        JSONArray result = response.getJSONArray("card_groups");
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject cardGroupObject = result.getJSONObject(i);
                            Gson gson = new Gson();
                            CardGroup cardGroup = gson.fromJson(cardGroupObject.toString(), CardGroup.class);
                            cardGroupArrayList.add(cardGroup);
                        }
                        loadCardViews();

                    } catch (Exception e) {
                        Log.e("APIFetch", e.toString());
                    }
                },
                Throwable::printStackTrace
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

    private void loadCardViews() {
        setUpHc3RecyclerView();
        setUpHc6RecyclerView();
    }

    private void setUpHc6RecyclerView() {
        ArrayList<CardGroup> hc6CardGroups = new ArrayList<CardGroup>();
        for (int i = 0; i < cardGroupArrayList.size(); i++) {
            if (cardGroupArrayList.get(i).getDesign_type().equals("HC6")) {
                hc6CardGroups.add(cardGroupArrayList.get(i));
            }
        }
        AdapterHc6 adapterHc6 = new AdapterHc6(hc6CardGroups, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.hc6Recyclerview.setLayoutManager(mLayoutManager);
        binding.hc6Recyclerview.setItemAnimator(new DefaultItemAnimator());
        binding.hc6Recyclerview.setAdapter(adapterHc6);
        binding.hc6Recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getContext(), binding.hc3Recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

    }

    private void setUpHc3RecyclerView() {
        ArrayList<CardGroup> hc3CardGroups = new ArrayList<CardGroup>();
        for (int i = 0; i < cardGroupArrayList.size(); i++) {
            if (cardGroupArrayList.get(i).getDesign_type().equals("HC3")) {
                hc3CardGroups.add(cardGroupArrayList.get(i));
            }
        }
        AdapterHc3 adapterHc3 = new AdapterHc3(hc3CardGroups, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.hc3Recyclerview.setLayoutManager(mLayoutManager);
        binding.hc3Recyclerview.setItemAnimator(new DefaultItemAnimator());
        binding.hc3Recyclerview.setAdapter(adapterHc3);
        binding.hc3Recyclerview.addOnItemTouchListener(new RecyclerTouchListener(getContext(), binding.hc3Recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean netStatus = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if (!netStatus) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setMessage("Looks like you're offline 👀...");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "OK",
                    (dialog, id) -> dialog.cancel());
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        return netStatus;
    }


}