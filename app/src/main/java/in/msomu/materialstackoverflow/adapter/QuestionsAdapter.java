package in.msomu.materialstackoverflow.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.msomu.materialstackoverflow.R;

/**
 * Created by msomu on 29/06/16.
 */
public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {
    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_question_item, parent, false);
        return new QuestionsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(QuestionsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder {
        public QuestionsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
