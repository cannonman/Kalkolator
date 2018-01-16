package io.github.wint3rmute.kalkolator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DrinkLog extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private List<DrinkLogObject> drinkLog = new ArrayList<>();
    private RecyclerView drinkLogRecyclerView;
    private DrinkLogObjectAdapter drinkLogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getApplicationContext());
        firebaseLogin();

        setContentView(R.layout.activity_drink_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), data_upload.class);
                startActivity(intent);
            }
        });

        testRecyclerView();
    }

    void testRecyclerView()
    {
        drinkLogRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        drinkLogAdapter = new DrinkLogObjectAdapter(drinkLog);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        drinkLogRecyclerView .setLayoutManager(mLayoutManager);
        drinkLogRecyclerView .setItemAnimator(new DefaultItemAnimator());
        drinkLogRecyclerView .setAdapter(drinkLogAdapter);

        getSampleShit();
    }

    void getSampleShit()
    {
        DrinkLogObject a = new DrinkLogObject(new Date().toString(), 12, 34);
        DrinkLogObject b = new DrinkLogObject(new Date().toString(), 12, 34);
        DrinkLogObject c = new DrinkLogObject(new Date().toString(), 12, 34);
        DrinkLogObject d = new DrinkLogObject(new Date().toString(), 12, 34);

        drinkLog.add(a);
        drinkLog.add(b);
        drinkLog.add(c);
        drinkLog.add(d);

        drinkLogAdapter.notifyDataSetChanged();
    }

    void firebaseLogin() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()
        );

// Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    void alert(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                alert(user.getUid());
                //getDataBase();
            } else {
                alert("Something went wrong. Go kill yourself");
            }
        }
    }

    private void getDataBase() {
        alert("e kurwa");
        DatabaseReference alkoList = FirebaseDatabase.getInstance().getReference("/alkohole");
        alkoList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                alert(dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drink_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
