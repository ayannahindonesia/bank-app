package com.ayannah.asira.custom;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ayannah.asira.data.model.Angsuran;
import com.ayannah.asira.data.model.BankDetail;
import com.ayannah.asira.data.model.Loans.DataItem;
import com.ayannah.asira.data.model.MenuAgent;
import com.ayannah.asira.data.model.Question;
import com.ayannah.asira.data.model.UserBorrower;

public interface CommonListListener {

    interface LoanAdapterListener{

        void onClickItem(DataItem loans);
    }

    interface NotifAdapterListener {

        void onClickItem(String data);
    }

    interface ViewBorrowerListener{

        void onClickButton(UserBorrower user);

        void onClick(UserBorrower user);
    }

    interface ListLoanAgent{

        void onClickItem(DataItem loan);
    }

    interface BankListListener{
        void onClickItem(BankDetail bankDetail, View itemView, LinearLayout linearLayout);

    }

    interface CommonStringItemClickListener{

        void onClickItem(String item);
    }

    interface MenuAgentListener{

        void onClick(MenuAgent param);
    }

    interface AgentsClientListener{

        void onClickClient(UserBorrower param);

        void onClickAjukan(UserBorrower param);

    }

    interface QuestionListener{

        void onClickQuestion(Question.Data param);

    }

    interface AngsuranListener{

        void onClickAngsuran(Angsuran data);

    }

}
