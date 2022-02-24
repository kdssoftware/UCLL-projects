package be.ucll.java.mobile.ucllnotes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import be.ucll.java.mobile.ucllnotes.database.Constants;
import be.ucll.java.mobile.ucllnotes.database.NotesDao;
import be.ucll.java.mobile.ucllnotes.database.LocalDatabase;
import be.ucll.java.mobile.ucllnotes.model.Note;
import be.ucll.java.mobile.ucllnotes.recyclerview.RVItemClick;
import be.ucll.java.mobile.ucllnotes.recyclerview.RecViewAdapter;
import be.ucll.java.mobile.ucllnotes.recyclerview.SwipeToDeleteCallback;

public class RVItemListFragment extends Fragment implements RVItemClick {
    private static final String TAG = "RVListFragment";

    // UI Components
    private FloatingActionButton fab;
    private RecyclerView rvItems;

    // Date Access Object voor database CRUDS operaties
    private NotesDao dao;

    // Lijstje van Note objecten
    private List<Note> notes;

    // onCreateView wordt in de lifecycle van de App maar 1 keer opgeroepen op het Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    // onViewCreated wordt opgeroepen elke keer als het Fragment zichtbaar wordt
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ophalen UI componenten. De 'fab' maakt van het scherm van de Main Activity
        fab = getActivity().findViewById(R.id.fab);
        rvItems = view.findViewById(R.id.rvItems);

        // Ophalen data uit databank
        dao = LocalDatabase.getInstance(getContext()).getNotesDao();
        notes = dao.getAllNotes();

        // Koppel een adapter aan de RecyclerView met de opgehaalde nota's
        RecViewAdapter adapter = new RecViewAdapter(dao, notes, this);
        rvItems.setLayoutManager(new LinearLayoutManager(getContext()));
        rvItems.setAdapter(adapter);

        // Swipe links of rechts om een nota te verwijderen uit de lijst en de databank
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(rvItems);

        // Behandel de click event op de FloatingActionButton
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Create new item");
                navigate(Constants.OPERATION_CREATE, -1L);
            }
        });
    }

    @Override
    public void onRVItemClick(int pos) {
        Note n = notes.get(pos);
        Log.i(TAG, "Update item with ID: " + n.getId());
        navigate(Constants.OPERATION_UPDATE, n.getId());
    }

    private void navigate(String operation, long id) {
        // Navigeer naar het fragment waar je nota kan intikken of bijwerken
        // 1. Geef door dat het Create of Update betreft
        Bundle bundle = new Bundle();
        bundle.putString(Constants.OPERATION, operation);

        // 2. In het geval van een update geef ook de ID door
        if (id != -1L) {
            bundle.putLong(Constants.ID, id);
        }

        NavHostFragment.findNavController(RVItemListFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);

        // 3. Maak de FloatingActionButton onzichtbaar
        fab.setVisibility(View.INVISIBLE);
    }

}