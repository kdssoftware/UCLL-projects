package be.ucll.java.mobile.ucllnotes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import be.ucll.java.mobile.ucllnotes.database.Constants;
import be.ucll.java.mobile.ucllnotes.database.NotesDao;
import be.ucll.java.mobile.ucllnotes.database.LocalDatabase;
import be.ucll.java.mobile.ucllnotes.model.Note;

public class CreateUpdateFragment extends Fragment {
    private static final String TAG = "CreateUpdateFragment";

    private String operation;
    private Long id;

    private Button btnSave;
    private TextView txtTitle;
    private TextView txtContent;

    private NotesDao dao;

    // onCreateView wordt in de lifecycle van de App maar 1 keer opgeroepen op het Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_update, container, false);
    }

    // onViewCreated wordt opgeroepen elke keer als het Fragment zichtbaar wordt
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Haal de 2 argumenten op die meekomen bij het aanroepen van dit fragment
        operation = getArguments().getString(Constants.OPERATION);
        id = getArguments().getLong(Constants.ID);
        Log.i(TAG, "Fragment - onViewCreated met operatie '" + operation + "' en id " + id);

        // Ophalen UI componenten.
        txtTitle = view.findViewById(R.id.txtTitle);
        txtContent = view.findViewById(R.id.txtContent);
        btnSave = view.findViewById(R.id.btnSave);

        // Ophalen dao
        dao = LocalDatabase.getInstance(getContext()).getNotesDao();

        // Zet de Text/Caption van de Button correct
        if (Constants.OPERATION_UPDATE.equals(operation)) {
            Note n = dao.getNote(id);
            txtTitle.setText(n.getTitle());
            txtContent.setText(n.getContent());
            btnSave.setText(getString(R.string.btnUpdate));
        } else {
            btnSave.setText(getString(R.string.btnCreate));
        }

        // Voeg een ClickListener toe op de Save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Controleer dat de 2 velden titel en content wel degelijk werden ingevuld
                String title = txtTitle.getText().toString();
                if (title.trim().length() == 0) {
                    Toast.makeText(getContext(), getString(R.string.title_mandatory), Toast.LENGTH_LONG).show();
                    return;
                }
                String content = txtContent.getText().toString();
                if (content.trim().length() == 0) {
                    Toast.makeText(getContext(), getString(R.string.content_mandatory), Toast.LENGTH_LONG).show();
                    return;
                }

                if (Constants.OPERATION_UPDATE.equals(operation)) {
                    Note n = new Note(id, title.trim(), content.trim());
                    Log.i(TAG, "Note with ID " + id + " updated");
                    dao.updateNote(n);
                } else {
                    // Maak een nieuwe Nota aan en voeg die toe aan de databank
                    Note n = new Note(title.trim(), content.trim());
                    long id = dao.insertNote(n);
                    Log.i(TAG, "Note created with ID: " + id);
                }

                // Keer terug naar het fragment met de lijst van nota's
                NavHostFragment.findNavController(CreateUpdateFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);

                // Maak de FloatingActionButton terug zichtbaar
                FloatingActionButton fab = getActivity().findViewById(R.id.fab);
                if (fab != null) {
                    fab.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}