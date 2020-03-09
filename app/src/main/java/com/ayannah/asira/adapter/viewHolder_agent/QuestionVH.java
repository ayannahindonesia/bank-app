package com.ayannah.asira.adapter.viewHolder_agent;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.custom.CommonListListener;
import com.ayannah.asira.data.model.Question;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionVH extends RecyclerView.ViewHolder {

    @BindView(R.id.text) TextView tvText;

    private CommonListListener.QuestionListener listener;

    public QuestionVH(View itemView, CommonListListener.QuestionListener listener){
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;
    }

    public void bind(Question.Data data){

        tvText.setText(data.getTitle());

        itemView.setOnClickListener(v -> {

            listener.onClickQuestion(data);

        });

    }
}
