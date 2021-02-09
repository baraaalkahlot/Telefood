package com.bik.telefood.ui.more;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivitySubscriptionRenewalBinding;
import com.google.android.material.textview.MaterialTextView;

public class SubscriptionRenewalActivity extends AppCompatActivity {

    private ActivitySubscriptionRenewalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubscriptionRenewalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        appendedText(R.string.label_bank_name, getString(R.string.label_bank_name_p), binding.include.tvBankName);
        appendedText(R.string.label_account_number, "2457454657654", binding.include.tvAccountNumber);
        appendedText(R.string.label_the_full_name, getString(R.string.label_bank_name_p), binding.include.tvFullName);
        appendedText(R.string.label_iban, "354654654654", binding.include.tvIban);

        binding.btnSend.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
    }

    private void appendedText(int label, String value, MaterialTextView textView) {
        SpannableStringBuilder str = new SpannableStringBuilder(getString(label));
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.lochinvar)), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(str);

        Spannable wordTwo = new SpannableString(value);
        wordTwo.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.tundora)), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(" " + wordTwo);
    }
}