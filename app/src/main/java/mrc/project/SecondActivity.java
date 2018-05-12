package mrc.project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.test.suitebuilder.annotation.Suppress;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private  Spinner spinner4;
    private Button button;

    private String cities[];
    private String locations[];
    private String gases[];
    private String time[];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        spinner1 = (Spinner) findViewById(R.id.spinner_cities);
        spinner2 = (Spinner) findViewById(R.id.spinner_locations);
        spinner3 = (Spinner) findViewById(R.id.spinner_gases);
        spinner4=(Spinner) findViewById(R.id.spinner_time);
        button = (Button) findViewById(R.id.second_fetch_button);

        cities = getResources().getStringArray(R.array.cities);
        locations = getResources().getStringArray(R.array.locations);
        gases = getResources().getStringArray(R.array.gases);
        time=getResources().getStringArray(R.array.time);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(arrayAdapter);
        spinner1.setPrompt("Select City:");

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, locations);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter2);
        spinner2.setPrompt("Select Location:");

        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gases);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(arrayAdapter3);
        spinner3.setPrompt("Select Gas:");

        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, time );
        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(arrayAdapter4);
        spinner4.setPrompt("Select Period");



        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (spinner1.getSelectedItem().toString().equalsIgnoreCase("Bhopal") || spinner1.getSelectedItem().toString().equalsIgnoreCase("Indore")) {
                    spinner2.setEnabled(false);
                    spinner3.setEnabled(false);
                    spinner4.setEnabled(false);
                    Toast.makeText(SecondActivity.this, "Currently Unavailable In Your City", Toast.LENGTH_SHORT).show();
                } else {
                    spinner2.setEnabled(true);
                    spinner3.setEnabled(true);
                    spinner4.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text1 = spinner1.getSelectedItem().toString();
                String text2 = spinner2.getSelectedItem().toString();
                String text3 = spinner3.getSelectedItem().toString();
                String text4= spinner4.getSelectedItem().toString();

                if (text1.equals("--Select--") || text2.equals("--Select--") || text3.equals("--Select--") || text4.equals("--Select--")) {
                    Toast.makeText(SecondActivity.this, "Please Select From DropdownList", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}

