package eu.ase.ro.damapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import eu.ase.ro.damapp.R;
import eu.ase.ro.damapp.model.Review;

public class ReviewFragment extends Fragment {

    private FloatingActionButton fabSave;
    private FloatingActionButton fabRemove;
    private FloatingActionButton fabClear;
    private RatingBar rbStar;
    private TextInputEditText tietDescription;
    private ListView lvReviews;

    private final List<Review> reviews = new ArrayList<>();

    private Review selectedReview;

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        fabSave = view.findViewById(R.id.review_fab_save);
        fabRemove = view.findViewById(R.id.review_fab_remove);
        fabClear = view.findViewById(R.id.review_fab_clear);
        rbStar = view.findViewById(R.id.review_rb_star);
        tietDescription = view.findViewById(R.id.review_tiet_description);
        lvReviews = view.findViewById(R.id.review_lv_reviews);

        ArrayAdapter<Review> adapter = new ArrayAdapter<>(getContext().getApplicationContext(), android.R.layout.simple_list_item_1, reviews);
        lvReviews.setAdapter(adapter);

        lvReviews.setOnItemClickListener(getItemClickEvent());
        fabSave.setOnClickListener(getSaveEvent());
        fabRemove.setOnClickListener(getRemoveEvent());
        fabClear.setOnClickListener(getClearEvent());
    }

    private AdapterView.OnItemClickListener getItemClickEvent() {
        return (parent, view, position, id) -> {
            selectedReview = reviews.get(position);
            tietDescription.setText(selectedReview.getDescription());
            rbStar.setRating(selectedReview.getStar());
        };
    }

    private View.OnClickListener getClearEvent() {
        return v -> clearViews();
    }

    private View.OnClickListener getRemoveEvent() {
        return v -> {
            if (selectedReview == null) {
                Toast.makeText(getContext().getApplicationContext(), R.string.review_no_review_selected, Toast.LENGTH_SHORT).show();
                return;
            }
            reviews.removeIf(r -> r.getId().equals(selectedReview.getId()));
            notifyAdapter();
            clearViews();
        };
    }

    private View.OnClickListener getSaveEvent() {
        return v -> {
            if (!isValid()) {
                return;
            }
            if (selectedReview == null) {
                addNewReview();
                return;
            }
            updateSelectedReview();
        };
    }

    private void updateSelectedReview() {
        reviews.forEach(r -> {
            if (r.getId().equals(selectedReview.getId())) {
                r.setDescription(tietDescription.getText().toString());
                r.setStar(rbStar.getRating());
            }
        });
        clearViews();
        notifyAdapter();
    }

    private void addNewReview() {
        float star = rbStar.getRating();
        String description = tietDescription.getText().toString();
        reviews.add(new Review(star, description));
        notifyAdapter();
        clearViews();
    }

    private boolean isValid() {
        if (rbStar.getRating() == 0) {
            Toast.makeText(getContext().getApplicationContext(), R.string.review_missing_rating, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (tietDescription.getText() == null || tietDescription.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext().getApplicationContext(), R.string.review_missing_description, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void notifyAdapter() {
        ArrayAdapter<Review> adapter = (ArrayAdapter<Review>) lvReviews.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void clearViews() {
        selectedReview = null;
        rbStar.setRating(0);
        tietDescription.setText("");
    }
}