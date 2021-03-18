package com.example.flyhigh.game_activity.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.flyhigh.R;

import java.util.Objects;


public class PauseFragment extends Fragment
{

    public interface OnButtonSelected
    {
        void onSelect(int option);
    }

    OnButtonSelected aListener;

    public PauseFragment()
    {

    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        try
        {
            aListener = (OnButtonSelected) context;
        }
        catch (ClassCastException e)
        {
            throw new RuntimeException();
        }
    }

    public void select(int option)
    {
        aListener.onSelect(option);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_pause, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {

        super.onActivityCreated(savedInstance);
        Button resume = Objects.requireNonNull(getView()).findViewById(R.id.pause_fragment_resume_button);
        resume.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                select(0);
            }
        });
        Button exit = getView().findViewById(R.id.pause_fragment_exit_button);
        exit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                select(1);
            }
        });
    }
}