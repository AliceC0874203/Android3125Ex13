package com.example.android_3125_ex13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.example.android_3125_ex13.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final double TAX = 0.13;
    ArrayList<Meal> meals = new ArrayList<>();
    Meal mySelection;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addMeals();

        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                meals);
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        binding.spinner.setAdapter(ad);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Meal meal = (Meal) adapterView.getSelectedItem();
                mySelection = meal;
                binding.price.setText(""+meal.getPrice());
                binding.image.setImageResource(meal.getImgSrc());
                calculateTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                binding.btnMakeOrder.setEnabled(b);
            }
        });

        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                count = seekBar.getProgress();
                binding.priceTV.setText(
                        "Quantity: "+count
                );
                calculateTotal();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.btnMakeOrder.setOnClickListener( v -> {
            if(count < 1){
                Snackbar.make(binding.getRoot(),"Quantity should be more than 0",Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (!binding.checkbox.isChecked()) {
                Snackbar.make(binding.getRoot(),"Please check confirm",Snackbar.LENGTH_SHORT).show();
                return;
            }

            Snackbar.make(binding.getRoot(),"Your Order has been placed. Thank You !",Snackbar.LENGTH_SHORT).show();
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                calculateTotal();
            }
        });
    }

    private void addMeals(){
        meals.add(new Meal("Chicken",30,R.drawable.checken));
        meals.add(new Meal("Noodles",40,R.drawable.noodles));
        meals.add(new Meal("Beef",80,R.drawable.beef));
        meals.add(new Meal("Rice",3.5, R.drawable.rice));
    }

    private void calculateTotal(){
        if(mySelection != null) {
            double totalPriceAmount = count * mySelection.getPrice();
            double taxAmount = totalPriceAmount * TAX;
            int dealId = binding.radioGroup.getCheckedRadioButtonId();
            String value = ((RadioButton) findViewById(dealId)).getText().toString();
            double tip = Double.parseDouble(value.replace("%","")) / 100.0;
            double tipAmount = totalPriceAmount * tip;
            double finalTotal = totalPriceAmount + taxAmount + tipAmount;
            String finalTotalString = String.format("%.2f", finalTotal);
            binding.totalPrice.setText(""+finalTotalString);
        }else{
            binding.price.setText("0");
            binding.totalPrice.setText("0");
        }
    }
}