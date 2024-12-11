// Crearea meniului de optiuni
private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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