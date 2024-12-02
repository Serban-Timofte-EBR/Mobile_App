package eu.ase.ro.damapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.ase.ro.damapp.databinding.ActivityMainBinding;
import eu.ase.ro.damapp.databinding.FragmentAboutBinding;
import eu.ase.ro.damapp.fragments.AboutFragment;
import eu.ase.ro.damapp.fragments.HomeFragment;
import eu.ase.ro.damapp.fragments.ProfileFragment;
import eu.ase.ro.damapp.model.Expense;
import eu.ase.ro.damapp.network.AsyncTaskRunner;
import eu.ase.ro.damapp.network.Callback;
import eu.ase.ro.damapp.network.ExpenseParser;
import eu.ase.ro.damapp.network.HttpManager;

public class MainActivity extends AppCompatActivity {
    public static final String UPDATE_EXPENSE_KEY = "update_expense_key";


    private static final String EXPENSES_URL = "https://api.npoint.io/5380854edf409813c032";
    public static final String UPDATED_EXPENSE_POSITION = "updated_expense_position";

    private DrawerLayout drawerLayout;
    private NavigationView navView;

    private FloatingActionButton fabAdd;
    private ActivityResultLauncher<Intent> launcher;
    private ActivityResultLauncher<Intent> updateLauncher;

    private final List<Expense> expenses = new ArrayList<>();

    private Fragment currentFragment;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configNavigation();

        navView = findViewById(R.id.main_nav_view);
        navView.setNavigationItemSelectedListener(
                getItemSelected());
        fabAdd = findViewById(R.id.main_fab_add);
        fabAdd.setOnClickListener(getAddEvent());

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                getAddExpenseCallback());
        updateLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                getUpdateExpenseCallback());
        if (savedInstanceState == null) {
            //prima deschidere
            currentFragment = HomeFragment.getInstance(expenses);
            openFragment();
            navView.setCheckedItem(R.id.nav_home);
        }
    }

    private ActivityResultCallback<ActivityResult> getAddExpenseCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Expense expense = result.getData()
                        .getParcelableExtra(AddExpenseActivity.EXPENSE_KEY);
                expenses.add(expense);

                notifyAdapter();
                Log.i("MainActivity", "expenses = " + expenses);
            }
        };
    }

    private void notifyAdapter() {
        //notify adapter
        if (currentFragment instanceof HomeFragment) {
            ((HomeFragment) currentFragment).notifyAdapter();
        }
    }

    private View.OnClickListener getAddEvent() {
        return v -> {
            Intent intent = new Intent(getApplicationContext(), AddExpenseActivity.class);
            launcher.launch(intent);
        };
    }

    private NavigationView.OnNavigationItemSelectedListener getItemSelected() {
        return item -> {
            if (item.getItemId() == R.id.nav_home) {
                Toast.makeText(getApplicationContext(),
                        R.string.main_home_clicked,
                        Toast.LENGTH_SHORT).show();
                currentFragment = HomeFragment.getInstance(expenses);
            } else if (item.getItemId() == R.id.nav_profile) {
                Toast.makeText(getApplicationContext(),
                        R.string.main_profile_clicked,
                        Toast.LENGTH_SHORT).show();
                currentFragment = new ProfileFragment();
            } else {
                Toast.makeText(getApplicationContext(),
                        R.string.main_about_clicked,
                        Toast.LENGTH_SHORT).show();
                currentFragment = new AboutFragment();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            openFragment();
            return true;
        };
    }

    private void openFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fl_container, currentFragment)
                .commit();
    }

    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.main_drawer);
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(
            @NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_import_data) {
            Toast.makeText(getApplicationContext(),
                    R.string.main_import_data_clicked,
                    Toast.LENGTH_SHORT).show();

            //method 1 - Thread
//            Thread thread = new Thread(){
//                @Override
//                public void run() {
//                    //suntem pe un alt fir de executie
//                    //nu avem acces la views sau context
//                    HttpManager manager = new HttpManager(EXPENSES_URL);
//                    String result = manager.call();
//
////                    runOnUiThread(runResultOnUIThread(result));
//                    handler.post(runResultOnUIThread(result));
//                }
//            };
//            thread.start();

            //method 2
//            executor.execute(() -> {
//                //suntem pe un alt fir de executie
//
//                HttpManager manager = new HttpManager(EXPENSES_URL);
//                String result = manager.call();
//
////                    runOnUiThread(runResultOnUIThread(result));
//                handler.post(runResultOnUIThread(result));
//            });
            //method 3
            Callable<String> callable = new HttpManager(EXPENSES_URL);
            Callback<String> callback = getCallbackFromHttpManager();
            asyncTaskRunner.executeAsync(callable, callback);
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    private Callback<String> getCallbackFromHttpManager() {
        return result -> {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            List<Expense> parsedExpenses = ExpenseParser.fromJson(result);
            expenses.addAll(parsedExpenses);
            notifyAdapter();
        };
    }

    @NonNull
    private Runnable runResultOnUIThread(String result) {
        return () -> {
            // ne aflam pe UI thread
            Toast.makeText(getApplicationContext(),
                    result,
                    Toast.LENGTH_SHORT).show();
        };
    }

    public void updateExpenseFromListView(Expense expenseForUpdate, int position) {
        Intent updateIntent = new Intent(this, AddExpenseActivity.class);
        updateIntent.putExtra(UPDATE_EXPENSE_KEY, expenseForUpdate);
        updateIntent.putExtra(UPDATED_EXPENSE_POSITION, position);
        updateLauncher.launch(updateIntent);
    }

    private ActivityResultCallback<ActivityResult> getUpdateExpenseCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Expense updatedExpense = result.getData().getParcelableExtra(AddExpenseActivity.UPDATE_EXPENSE_KEY_RESPONSE);
                int position = result.getData().getIntExtra(UPDATED_EXPENSE_POSITION, -1);

                if (updatedExpense != null && position != -1) {
                    expenses.set(position, updatedExpense);
                    notifyAdapter();
                }
            }
        };
    }

    public void deleteExpenseFromListView(int position) {
        expenses.remove(position);
        notifyAdapter();
        Toast.makeText(getApplicationContext(), getString(R.string.delete_ok_template, position), Toast.LENGTH_SHORT).show();
    }
}