package com.ayannah.asira.screen.bantuan.isi_bantuan;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import com.ayannah.asira.BuildConfig;
import com.ayannah.asira.R;
import com.ayannah.asira.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;

public class IsiBantuanFragment extends BaseFragment {

    @BindView(R.id.text)
    TextView text;

    private String html;

    @Inject
    public IsiBantuanFragment(){}

    @Override
    protected int getLayoutView() {
        return R.layout.fragment_isi_bantuan;
    }

    @Override
    protected void initView(Bundle state) {

        Bundle bundle = getArguments();

        html = bundle.getString("desc");

        if(!html.contains("<html>")){

            text.setText(html);

        }else {

            String clear = html.replace("\\n", "");
            text.setText(HtmlCompat.fromHtml(clear, HtmlCompat.FROM_HTML_MODE_COMPACT));
        }

    }
}
