package mrc.project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class ChooseActivity extends AppCompatActivity {

    String arr[];
    ArrayList<String> arrayList = new ArrayList<>();
    private TextView result;
    private Spinner spinner1;
    private Spinner spinner2;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        result = (TextView) findViewById(R.id.result_field);
        spinner1 = (Spinner) findViewById(R.id.spinner_location);
        spinner2 = (Spinner) findViewById(R.id.spinner_gases);
        btn = (Button) findViewById(R.id.fetch_button);

        readFile();

        // now arr has the headers. and so set the adapters for the two spinners.

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(arrayAdapter);
        spinner1.setPrompt("Select Your Loaction:");

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter2);
        spinner2.setPrompt("Select Gas:");

    }

    public void readFile() {
        InputStream is = getResources().openRawResource(R.raw.test);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String spilt = ",";
        try {
            String line = reader.readLine();
            arr = line.split(spilt);

            arrayList.add(arr[0]);
            while ((line = reader.readLine()) != null) {
                String temp[] = line.split(spilt);
                arrayList.add(temp[0]);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        String text1 = spinner1.getSelectedItem().toString();
        String text2 = spinner2.getSelectedItem().toString();
        if (text1.equals("Select:") || text2.equals("Select:")) {
            Toast.makeText(ChooseActivity.this, "Please Select From DropdownList", Toast.LENGTH_SHORT).show();
        } else {
            int x = getPosition(spinner1.getSelectedItem().toString());
            result.setText(getValue(x, spinner2.getSelectedItem().toString()) + " ppm");
        }
    }


    public int getPosition(String key) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equalsIgnoreCase(key))
                return i;
        }
        return 0;
    }

    public String getValue(int pos, String key) {
        InputStream is = getResources().openRawResource(R.raw.test);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String spilt = ",";
        try {
            String line = "";
            while ((line = reader.readLine()) != null) {
                String temp[] = line.split(spilt);
                if (temp[0].equalsIgnoreCase(key)) {
                    return temp[pos];
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}

