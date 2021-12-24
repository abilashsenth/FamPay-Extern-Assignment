package com.thenextbiggeek.fampayextern;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.thenextbiggeek.fampayextern.databinding.FragmentMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentMain extends Fragment {
    private static final String MY_PREFS_NAME = "FamPayExternPref";
    static final String STATE_HC3 = "ishc3dismissed";
    String API_URL = "https://run.mocky.io/v3/fefcfbeb-5c12-4722-94ad-b8f92caad1ad";
    private FragmentMainBinding binding;
    private ArrayList<CardGroup> cardGroupArrayList;
    private boolean isHc3Dismissed;


    public FragmentMain() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences prefs = Objects.requireNonNull(getActivity()).getSharedPreferences(MY_PREFS_NAME, getContext().MODE_PRIVATE);
        isHc3Dismissed = prefs.getBoolean(STATE_HC3, false);
        cardGroupArrayList = new ArrayList<>();
        if (isNetworkAvailable()) {
            fetchAPI();
        }
        binding.fragmentMainSwiperefesh.setOnRefreshListener(() -> binding.fragmentMainSwiperefesh.setRefreshing(false));
    }

    /**
     * Initialize a new RequestQueue instance for retrieving API data,
     * Initialize a new JsonObjectRequest instance (Volley) and parse it into POJOs with GSON
     * And load the card views from hc1 to hc9
     */

    private void fetchAPI() {
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
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
        requestQueue.add(jsonObjectRequest);
    }

    private void loadCardViews() {
        if (!isHc3Dismissed) {
            setUpHc3RecyclerView();
        } else {
            binding.hc3Recyclerview.setVisibility(View.GONE);
            editSharedPref(false);
        }
        setUpHc6RecyclerView();
        setUpHc5RecyclerView();
        setUpHc9RecyclerView();
        setUpHc1RecyclerView();

    }

    /**
     * set up the card layout hc1
     */
    private void setUpHc1RecyclerView() {
        ArrayList<CardGroup> hc1CardGroupsScroll = new ArrayList<>();
        ArrayList<CardGroup> hc1CardGroupsNoScroll = new ArrayList<>();

        for (int i = 0; i < cardGroupArrayList.size(); i++) {
            if (cardGroupArrayList.get(i).getDesign_type().equals("HC1")) {
                if (cardGroupArrayList.get(i).isIs_scrollable()) {
                    hc1CardGroupsScroll.add(cardGroupArrayList.get(i));
                } else {
                    hc1CardGroupsNoScroll.add(cardGroupArrayList.get(i));
                }
            }
        }

        //for scroll enabled hc1s
        ArrayList<Card> hc1CardsScroll = new ArrayList<>();
        for (int i = 0; i < hc1CardGroupsScroll.size(); i++) {
            hc1CardsScroll.addAll(hc1CardGroupsScroll.get(i).getCards());
        }

        AdapterHc1 adapterHc1Scroll = new AdapterHc1(hc1CardsScroll, getContext());
        binding.hc1RecyclerviewScroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.hc1RecyclerviewScroll.setItemAnimator(new DefaultItemAnimator());
        binding.hc1RecyclerviewScroll.setAdapter(adapterHc1Scroll);

        //for scroll disabled hc1s
        ArrayList<Card> hc1CardsNoScroll = new ArrayList<>();
        for (int i = 0; i < hc1CardGroupsNoScroll.size(); i++) {
            hc1CardsNoScroll.addAll(hc1CardGroupsNoScroll.get(i).getCards());
        }
        AdapterHc1 adapterHc1NoScroll = new AdapterHc1(hc1CardsNoScroll, getContext());
        binding.hc1RecyclerviewNoscroll.setVisibility(View.VISIBLE);
        binding.hc1RecyclerviewNoscroll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.hc1RecyclerviewNoscroll.setItemAnimator(new DefaultItemAnimator());
        binding.hc1RecyclerviewNoscroll.setAdapter(adapterHc1NoScroll);

    }

    /**
     * set up the card layout hc9
     */
    private void setUpHc9RecyclerView() {

        ArrayList<CardGroup> hc9CardGroups = new ArrayList<>();
        for (int i = 0; i < cardGroupArrayList.size(); i++) {
            if (cardGroupArrayList.get(i).getDesign_type().equals("HC9")) {
                hc9CardGroups.add(cardGroupArrayList.get(i));
            }
        }
        ArrayList<Card> hc9Cards = new ArrayList<>();
        for (int i = 0; i < hc9CardGroups.size(); i++) {
            hc9Cards.addAll(hc9CardGroups.get(i).getCards());
        }
        AdapterHc9 adapterHc9 = new AdapterHc9(hc9Cards, getContext());
        binding.hc9Recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.hc9Recyclerview.setItemAnimator(new DefaultItemAnimator());
        binding.hc9Recyclerview.setAdapter(adapterHc9);
    }


    /**
     * set up the card layout hc5
     */
    private void setUpHc5RecyclerView() {
        ArrayList<CardGroup> hc5CardGroups = new ArrayList<>();
        for (int i = 0; i < cardGroupArrayList.size(); i++) {
            if (cardGroupArrayList.get(i).getDesign_type().equals("HC5")) {
                hc5CardGroups.add(cardGroupArrayList.get(i));
            }
        }
        ArrayList<Card> hc5Cards = new ArrayList<>();
        for (int i = 0; i < hc5CardGroups.size(); i++) {
            hc5Cards.addAll(hc5CardGroups.get(i).getCards());
        }
        AdapterHc5 adapterHc5 = new AdapterHc5(hc5Cards, getContext());
        binding.hc5Recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.hc5Recyclerview.setItemAnimator(new DefaultItemAnimator());
        binding.hc5Recyclerview.setAdapter(adapterHc5);
    }


    /**
     * set up the card layout hc6
     */
    private void setUpHc6RecyclerView() {
        ArrayList<CardGroup> hc6CardGroups = new ArrayList<>();
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
    }


    /**
     * set up the main card layout hc3
     */
    private void setUpHc3RecyclerView() {
        ArrayList<CardGroup> hc3CardGroups = new ArrayList<>();
        for (int i = 0; i < cardGroupArrayList.size(); i++) {
            if (cardGroupArrayList.get(i).getDesign_type().equals("HC3")) {
                hc3CardGroups.add(cardGroupArrayList.get(i));
            }
        }
        AdapterHc3 adapterHc3 = new AdapterHc3(hc3CardGroups, getContext(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.hc3Recyclerview.setLayoutManager(mLayoutManager);
        binding.hc3Recyclerview.setItemAnimator(new DefaultItemAnimator());
        binding.hc3Recyclerview.setAdapter(adapterHc3);
    }

    public void hideHc3(boolean isDismissed) {
        TimerTask timerTaskObj = new TimerTask() {
            public void run() {
                Objects.requireNonNull(getActivity()).runOnUiThread(() -> {
                    binding.hc3Recyclerview.setVisibility(View.GONE);
                    if (isDismissed) {
                        Toast.makeText(getContext(), "Card is dismissed", Toast.LENGTH_SHORT).show();
                        editSharedPref(true);
                    } else {
                        Toast.makeText(getContext(), "Card will display at next app launch", Toast.LENGTH_SHORT).show();
                        editSharedPref(false);
                    }
                });
            }
        };
        new Timer().schedule(timerTaskObj, 2000);

    }

    /**
     * quick instance saving for acknowledging whether hc3 card is dismissed forever or snoozed
     *
     * @param b
     */
    private void editSharedPref(boolean b) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, getContext().MODE_PRIVATE).edit();
        editor.putBoolean(STATE_HC3, b);
        editor.apply();
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