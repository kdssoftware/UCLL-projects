package be.ucll.java.mobile.ucllnotes.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.ucll.java.mobile.ucllnotes.R;
import be.ucll.java.mobile.ucllnotes.database.NotesDao;
import be.ucll.java.mobile.ucllnotes.model.Note;

public class RecViewAdapter extends RecyclerView.Adapter<RVItemHolder> {
    private static final String TAG = "RVAdapter";

    private NotesDao dao;
    private List<Note> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private RVItemClick RVItemClick;

    public RecViewAdapter(NotesDao dao, List<Note> list, Fragment frag) {
        context = frag.getContext();
        layoutInflater = LayoutInflater.from(context);
        this.dao = dao;
        this.list = list;
        this.RVItemClick = (RVItemClick) frag;
    }

    @NonNull
    @Override
    public RVItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new RVItemHolder(view, RVItemClick);
    }

    // Voor elk item in de List van de adapter wordt deze method aangeroepen
    // om de 'Data' te visualiseren in de UI met de Recyclerview
    @Override
    public void onBindViewHolder(@NonNull RVItemHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + list.get(position));
        Note n = list.get(position);
        holder.getTxtRVItem().setText(n.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void deleteItem(int position) {
        Log.d(TAG, "Delete item in position: " + position);

        // Verwijder het item uit de databank
        Note n = list.get(position);
        dao.deleteNote(n);

        // Neem het item ook weg uit de lijst in de Adapter
        list.remove(position);

        // Trigger refresh van de lijst/RecyclerView in de UI
        notifyItemRemoved(position);
    }

    public Context getContext() {
        return context;
    }
}
