// Crearea meniului de optiuni
private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // drawerLayout = findViewById(R.id.drawer_layout);
        // ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        // actionBarDrawerToggle.syncState();

        // + Astea ca variabile de clasa
        // private DrawerLayout drawerLayout;
        // private NavigationView navigationView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.option_import) {
            // logica vine aici
        }
        return super.onOptionsItemSelected(item);
    }

// Afisarea in ListView cu Custom Adapter
private void initComponent() {
        lvLabs = findViewById(R.id.main_lv_labs);
        LabAdapter adapter = new LabAdapter(getApplicationContext(),
                R.layout.row_lv_labs,
                labs,
                getLayoutInflater());
        lvLabs.setAdapter(adapter);
    }

private void notifyAdapter() {
        LabAdapter adapter = (LabAdapter) lvLabs.getAdapter();
        adapter.notifyDataSetChanged();
    }

// Implementarea citirii din retea cu AsyncTaskRunner
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.option_import) {
            Callable<String> callable = new HttpManager(NPOINT_URL);
            Callback<String> callback = getCallbackFromHttpManager();
            asyncTaskRunner.executeAsync(callable, callback);
        }
        return super.onOptionsItemSelected(item);
    }

    private Callback<String> getCallbackFromHttpManager() {
        return result -> {
            Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_SHORT).show();
            List<Lab> parsedLabs = LabParser.fromJSON(result);
            labs.addAll(parsedLabs);
            notifyAdapter();
        };
    }

// Activitatea de adaugare
 private ActivityResultLauncher<Intent> launcher;
 private Intent intent;

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getAddLabCallback());
        configNavigation();
        initComponent();
    }

private ActivityResultCallback<ActivityResult> getAddLabCallback() {
        return result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Lab lab = (Lab) result.getData().getSerializableExtra(AddActivity.KEY_LAB);
                labs.add(lab);
                notifyAdapter();
            }
        };
    }

    private void initComponent() {
        lvLabs = findViewById(R.id.main_lv_labs);
        LabAdapter adapter = new LabAdapter(getApplicationContext(),
                R.layout.row_lv_labs,
                labs,
                getLayoutInflater());
        lvLabs.setAdapter(adapter);

        fabAdd = findViewById(R.id.fab);
        fabAdd.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), AddActivity.class);
            launcher.launch(intent);
        });
    }

// Lucrul cu baza de date Room

// libs.versions.toml
    // libraries
    room-common = { group = "androidx.room", name = "room-common", version.ref = "roomVersion" }
    room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "roomVersion" }
    room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "roomVersion" }

    // versions
    roomVersion = "2.6.1"

// build.gradle (Module: app)
    // dependencies
    implementation libs.room.common
    implementation libs.room.runtime
    annotationProcessor libs.room.compiler

