package com.fic.notescategories.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fic.notescategories.R;
import com.fic.notescategories.controller.HistoryController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {

    Spinner spAction;
    EditText txtFrom, txtTo;
    Button btnFilter;
    RecyclerView recycler;

    HistoryController controller;
    HistoryAdapter adapter;

    // Guardamos el rango en millis (String para tu DAO)
    String fromTs = null;
    String toTs = null;

    final SimpleDateFormat uiFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setTitle("Historial");

        controller = new HistoryController(this);

        spAction = findViewById(R.id.spAction);
        txtFrom = findViewById(R.id.txtFrom);
        txtTo = findViewById(R.id.txtTo);
        btnFilter = findViewById(R.id.btnFilter);
        recycler = findViewById(R.id.recyclerHistory);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        String[] actions = {"all", "insert_note", "update_note", "delete_note", "insert_category", "update_category", "delete_category"};
        spAction.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, actions));

        adapter = new HistoryAdapter(controller.getAll());
        recycler.setAdapter(adapter);

        // ✅ Calendario para "Desde"
        txtFrom.setOnClickListener(v -> openDatePicker(true));

        // ✅ Calendario para "Hasta"
        txtTo.setOnClickListener(v -> openDatePicker(false));

        btnFilter.setOnClickListener(v -> {
            String action = spAction.getSelectedItem().toString();

            // Validación simple: si solo eligió una fecha, no filtramos por fechas
            if ((fromTs == null) != (toTs == null)) {
                Toast.makeText(this, "Selecciona Desde y Hasta para filtrar por fechas", Toast.LENGTH_SHORT).show();
                return;
            }

            adapter.setData(controller.filter(action, fromTs, toTs));
        });
    }

    private void openDatePicker(boolean isFrom) {
        Calendar c = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    Calendar selected = Calendar.getInstance();
                    selected.set(Calendar.YEAR, year);
                    selected.set(Calendar.MONTH, month);
                    selected.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    if (isFrom) {
                        // Inicio del día 00:00:00.000
                        selected.set(Calendar.HOUR_OF_DAY, 0);
                        selected.set(Calendar.MINUTE, 0);
                        selected.set(Calendar.SECOND, 0);
                        selected.set(Calendar.MILLISECOND, 0);

                        fromTs = String.valueOf(selected.getTimeInMillis());
                        txtFrom.setText(uiFormat.format(selected.getTime()));

                    } else {
                        // Fin del día 23:59:59.999
                        selected.set(Calendar.HOUR_OF_DAY, 23);
                        selected.set(Calendar.MINUTE, 59);
                        selected.set(Calendar.SECOND, 59);
                        selected.set(Calendar.MILLISECOND, 999);

                        toTs = String.valueOf(selected.getTimeInMillis());
                        txtTo.setText(uiFormat.format(selected.getTime()));
                    }
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }
}
