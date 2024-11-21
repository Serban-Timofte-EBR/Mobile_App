package eu.ase.ro.consultationmanagement.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import eu.ase.ro.consultationmanagement.R;

public class ConsultationAdapter extends ArrayAdapter<Consultation> {
    private Context context;
    private int resource;
    private List<Consultation> consultations;
    private LayoutInflater inflater;

    public ConsultationAdapter(@NonNull Context context, int resource, @NonNull List<Consultation> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.consultations = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);

        Consultation consultation = consultations.get(position);

        TextView tvDate = view.findViewById(R.id.timofte_serban_row_date);
        tvDate.setText(consultation.getDate().toString());

        TextView tvPatient = view.findViewById(R.id.timofte_serban_row_patient);
        tvPatient.setText(consultation.getPatient());

        TextView tvDiagnostic = view.findViewById(R.id.timofte_serban_row_diagnostic);
        tvDiagnostic.setText(consultation.getDiagnostic());

        return view;
    }
}
