package com.nephilim.WordSearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class search extends AppCompatActivity {

    EditText edtxt;
    Button btnser;
    TextView rslt1, rslt2, rslt3;
    Spinner lang;

    String lan, searchedword;
    static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        edtxt = (EditText) findViewById(R.id.edtxt);
        lang = (Spinner) findViewById(R.id.lang);
        String[] items = new String[]{"ENGLISH", "SPANISH", "HINDI", "SWAHILI", "MALAY", "NORTHERNSOTO", "LATVIAN", "INDONESIAN", "URDU", "ISIZULU", "SETSWANA"};
        final String[] ianacode = new String[]{"en", "es", "hi", "sw", "ms", "nso", "lv", "id", "ur", "zu", "tn"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        lang.setAdapter(adapter);
        rslt1 = (TextView) findViewById(R.id.rslt1);
        rslt2 = (TextView) findViewById(R.id.rslt2);
        rslt3 = (TextView) findViewById(R.id.rslt3);
        btnser = (Button) findViewById(R.id.btnser);
        btnser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchedword = edtxt.getText().toString().trim();
                int pos = lang.getSelectedItemPosition();
                lan = ianacode[pos];
                if (isonline()) {
                    if (searchedword.length() > 0) {
                        if (lan == "en") {
                            try {
                                new CallbackTask().execute(dictionaryEntries(searchedword, lan));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                new CallbackTask().execute(dictionaryEntriessyn(searchedword, lan));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                new CallbackTask().execute(dictionaryEntriesant(searchedword, lan));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                new CallbackTask().execute(dictionaryEntries(searchedword, lan));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    } else {
                        edtxt.requestFocus();
                        edtxt.setError("Enter word");
                    }
                } else {
                    Toast.makeText(search.this, "Network Unavaliable", Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    private String dictionaryEntries(String word, String lan) {
        final String language = lan.toLowerCase();
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id;
    }

    private String dictionaryEntriessyn(String word, String lan) {
        final String language = lan.toLowerCase();
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id + "/synonyms";
    }

    private String dictionaryEntriesant(String word, String lan) {
        final String language = lan.toLowerCase();
        final String word_id = word.toLowerCase(); //word id is case sensitive and lowercase is required
        return "https://od-api.oxforddictionaries.com:443/api/v1/entries/" + language + "/" + word_id + "/antonyms";
    }

    public boolean isonline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }


    }

    //in android calling network requests on the main thread forbidden by default
    //create class to do async job
    class CallbackTask extends AsyncTask<String, Integer, String> {


        protected String doInBackground(String... params) {

            //TODO: replace with your own app id and app key ,this is for demo
            final String app_id = "d4218d25";
            final String app_key = "387bb3182a49099fb7633766279c0f97";
            try {
                URL url = new URL(params[0]);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("app_id", app_id);
                urlConnection.setRequestProperty("app_key", app_key);

                // read the output from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                return stringBuilder.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return null;

            }
        }

        protected void onPostExecute(String result) {
            if (count == 0) {
                if (lan != "hi") {
                    try {
                        model obj = JsonParser.parsefeed(result);
                        count = 1;
                        rslt1.setText("Definition : " + obj.getDefinitions() + "\n\n" + "Example : " + obj.getExamples() + "\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(search.this, "definition not available", Toast.LENGTH_LONG).show();
                        count=0;
                    }
                } else {
                    try {
                        model obj = JsonHindi.parsefeed(result);
                        count = 0;
                        rslt1.setText("Definition : " + obj.getDefinitions() + "\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(search.this, "definition not available", Toast.LENGTH_LONG).show();
                        count=0;
                    }
                }
            } else if (count == 1) {
                try {
                    model obj1 = JsonParserSyn.parsefeed(result);
                    count = 2;
                    rslt2.setText("Synonyms : " + obj1.getSynonym() + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(search.this, "Synonym not available", Toast.LENGTH_LONG).show();
                }

            } else if (count == 2) {
                try {
                    model obj2 = JsonParserAnt.parsefeed(result);
                    count = 0;
                    rslt3.setText("Antonyms : " + obj2.getAntonym());

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(search.this, "antonym not available", Toast.LENGTH_LONG).show();
                }
            } else {
            }

        }

    }
}





