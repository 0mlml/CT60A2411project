package dev.mlml.ct60a2411project;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ExploreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        TextInputLayout textInputLayout = findViewById(R.id.inputLayout);
        MaterialAutoCompleteTextView autoCompleteTextView = findViewById(R.id.inputTV);
        MaterialButton button = findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.requireNonNull(autoCompleteTextView.getText()).toString().isEmpty()) {
                    textInputLayout.setError("Select an option");
                } else {
                    Toast.makeText(ExploreActivity.this, autoCompleteTextView.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
