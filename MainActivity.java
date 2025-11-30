package com.example.bmicalculator; 

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    // Declare UI components
    private TextInputEditText etWeight, etHeight;
    private MaterialButton btnCalculate;
    private TextView tvBMIValue, tvBMIStatus, tvMessage, tvCategory;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components (connecting Java code to XML layout)
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvBMIValue = findViewById(R.id.tvBMIValue);
        tvBMIStatus = findViewById(R.id.tvBMIStatus);
        tvMessage = findViewById(R.id.tvMessage);
        tvCategory = findViewById(R.id.tvCategory);
        progressBar = findViewById(R.id.progressBar);

        // Set click listener for Calculate button
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
    }

    // Method to calculate BMI
    private void calculateBMI() {
        // Get text from input fields
        String weightStr = etWeight.getText().toString();
        String heightStr = etHeight.getText().toString();

        // Check if fields are empty
        if (weightStr.isEmpty() || heightStr.isEmpty()) {
            Toast.makeText(this, "Please enter both weight and height", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Convert strings to numbers
            float weight = Float.parseFloat(weightStr);
            float heightCm = Float.parseFloat(heightStr);

            // Validate inputs
            if (weight <= 0 || heightCm <= 0) {
                Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convert height from cm to meters
            float heightM = heightCm / 100;

            // Calculate BMI: BMI = weight (kg) / height² (m²)
            float bmi = weight / (heightM * heightM);

            // Display BMI value (rounded to 1 decimal place)
            tvBMIValue.setText(String.format("%.1f", bmi));

            // Determine BMI category and update UI
            updateBMIStatus(bmi);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to update BMI status, category, and message based on BMI value
    private void updateBMIStatus(float bmi) {
        String status;
        String message;
        int progress;

        if (bmi < 15.9) {
            // Severely Malnourished
            status = "Severely Malnourished";
            message = "You are severely underweight. Please consult a doctor.";
            tvBMIStatus.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
            tvCategory.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
            progress = 10;
        } else if (bmi >= 15.9 && bmi < 17.0) {
            // Mildly Malnourished
            status = "Mildly Malnourished";
            message = "You are underweight. Consider consulting a nutritionist.";
            tvBMIStatus.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
            tvCategory.setTextColor(getResources().getColor(android.R.color.holo_blue_light));
            progress = 25;
        } else if (bmi >= 17.0 && bmi < 18.5) {
            // Underweight
            status = "Underweight";
            message = "You are slightly underweight. Try to gain some weight.";
            tvBMIStatus.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));
            tvCategory.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));
            progress = 40;
        } else if (bmi >= 18.5 && bmi < 25.0) {
            // Normal
            status = "Normal";
            message = "You are doing great! Keep up the good work.";
            tvBMIStatus.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            tvCategory.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            progress = 60;
        } else if (bmi >= 25.0 && bmi < 30.0) {
            // Overweight
            status = "Overweight";
            message = "You are overweight. Consider exercise and diet changes.";
            tvBMIStatus.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            tvCategory.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            progress = 75;
        } else {
            // Obese
            status = "Obese";
            message = "You are obese. Please consult a healthcare professional.";
            tvBMIStatus.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            tvCategory.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            progress = 90;
        }

        // Update UI with status and message
        tvBMIStatus.setText(status);
        tvCategory.setText(status);
        tvMessage.setText(message);
        progressBar.setProgress(progress);
    }
}
