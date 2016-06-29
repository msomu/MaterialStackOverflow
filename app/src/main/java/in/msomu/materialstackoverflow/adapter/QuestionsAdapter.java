package in.msomu.materialstackoverflow.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

import in.msomu.materialstackoverflow.ChipView;
import in.msomu.materialstackoverflow.Question;
import in.msomu.materialstackoverflow.R;

/**
 * Created by msomu on 29/06/16.
 */
public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {
    private Context context;
    private ArrayList<Question> questions;

    public QuestionsAdapter(Context context, ArrayList<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_question_item, parent, false);
        return new QuestionsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(QuestionsViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.flexboxLayout.removeAllViews();
        for (int i = 0; i < question.getTags().length; i++) {
            ChipView chipView = new ChipView(context);
            String[] tags = question.getTags();
            chipView.setText(tags[i]);
            holder.flexboxLayout.addView(chipView);
        }
        holder.questionTV.setText(question.getQuestion());
        holder.upVoteTV.setText(String.valueOf(question.getUpvotes()));
        holder.timeTv.setText(String.valueOf(question.getTimeStamp()));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class QuestionsViewHolder extends RecyclerView.ViewHolder {
        FlexboxLayout flexboxLayout;
        TextView questionTV;
        TextView upVoteTV;
        TextView timeTv;

        public QuestionsViewHolder(View itemView) {
            super(itemView);
            flexboxLayout = (FlexboxLayout) itemView.findViewById(R.id.flexbox_layout);
            questionTV = (TextView) itemView.findViewById(R.id.questionTV);
            upVoteTV = (TextView) itemView.findViewById(R.id.upVoteTV);
            timeTv = (TextView) itemView.findViewById(R.id.timeTv);
        }
    }
}
