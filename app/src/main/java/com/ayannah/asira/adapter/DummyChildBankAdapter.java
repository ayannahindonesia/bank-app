package com.ayannah.asira.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ayannah.asira.R;
import com.ayannah.asira.data.model.BankDummy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DummyChildBankAdapter extends RecyclerView.Adapter<DummyChildBankAdapter.ViewHolderChildBank> {

    private List<BankDummy> banks;

    public DummyChildBankAdapter(List<BankDummy> banks){
        this.banks = banks;
    }

    @NonNull
    @Override
    public ViewHolderChildBank onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_choose_bank, parent, false);

        return new ViewHolderChildBank(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderChildBank holder, int position) {

        holder.bind(banks.get(position));
    }

    @Override
    public int getItemCount() {
        return banks.size();
    }

    class ViewHolderChildBank extends RecyclerView.ViewHolder{

        @BindView(R.id.tvNameBank)
        TextView tvNameBank;

        ViewHolderChildBank(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(BankDummy name){

            tvNameBank.setText(name.getName());
        }

    }
}
