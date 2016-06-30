package in.msomu.materialstackoverflow.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

import in.msomu.materialstackoverflow.widgets.ChipView;
import in.msomu.materialstackoverflow.models.Question;
import in.msomu.materialstackoverflow.R;

/**
 * Created by msomu on 29/06/16.
 * RecylerView.Adapter which is the adapter that will populate the views for each questions and renders it.
 */
public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {
    private Context context;
    private ArrayList<Question> questions;

    /**
     * Constructer for the Adapter
     * @param context Context of the view so that can be used in the views in the adapter
     * @param questions the array of questions that is going to be rendered
     */
    public QuestionsAdapter(Context context, ArrayList<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    /**
     * Creates the ViewHolder for each Question
     * @param parent the parent View
     * @param viewType this questionViewtype (We use only one view type)
     * @return the viewholder initated with its view in it
     */
    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_question_item, parent, false);
        return new QuestionsViewHolder(v);
    }

    /**
     * Where every questions content are set
     * @param holder the viewholder for that view
     * @param position the position so that we can identify the positon of the question
     */
    @Override
    public void onBindViewHolder(QuestionsViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.flexboxLayout.removeAllViews();
        for (int i = 0; i < question.getTags().length; i++) {
            final ChipView chipView = new ChipView(context);
            final String[] tags = question.getTags();
            chipView.setText(tags[i]);
            final int finalI = i;
            chipView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Tag : "+tags[finalI], Toast.LENGTH_SHORT).show();
                }
            });
            holder.flexboxLayout.addView(chipView);
        }
        holder.questionTV.setText(question.getQuestion());
        holder.upVoteTV.setText(String.valueOf(question.getUpvotes()));
        holder.timeTv.setText(String.valueOf(question.getTimeStamp()));
        holder.usernameTv.setText(String.valueOf(question.getUserName()));
    }

    /**
     * Total number of questions
     * @return number of questions
     */
    @Override
    public int getItemCount() {
        return questions.size();
    }

    /**
     * View Holder for each single Question
     */
    public class QuestionsViewHolder extends RecyclerView.ViewHolder {
        FlexboxLayout flexboxLayout;
        TextView questionTV;
        TextView upVoteTV;
        TextView timeTv;
        TextView usernameTv;

        /**
         * Constructor where the view is initalized
         * @param itemView Initalized and returned back
         */
        public QuestionsViewHolder(View itemView) {
            super(itemView);
            flexboxLayout = (FlexboxLayout) itemView.findViewById(R.id.flexbox_layout);
            questionTV = (TextView) itemView.findViewById(R.id.questionTV);
            upVoteTV = (TextView) itemView.findViewById(R.id.upVoteTV);
            timeTv = (TextView) itemView.findViewById(R.id.timeTv);
            usernameTv = (TextView) itemView.findViewById(R.id.usernameTv);
        }
    }
}
