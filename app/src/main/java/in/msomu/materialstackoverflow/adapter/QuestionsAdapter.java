package in.msomu.materialstackoverflow.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexboxLayout;

import in.msomu.materialstackoverflow.ChipView;
import in.msomu.materialstackoverflow.R;

/**
 * Created by msomu on 29/06/16.
 */
public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {
    private Context context;

    public QuestionsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_question_item, parent, false);
        return new QuestionsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(QuestionsViewHolder holder, int position) {
        holder.flexboxLayout.removeAllViews();
        for (int i = 0; i < 5; i++) {
            ChipView chipView = new ChipView(context);
            chipView.setText("vdbjv dja");
            holder.flexboxLayout.addView(chipView);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder {
        FlexboxLayout flexboxLayout;
        public QuestionsViewHolder(View itemView) {
            super(itemView);
            flexboxLayout = (FlexboxLayout) itemView.findViewById(R.id.flexbox_layout);
        }
    }
}
