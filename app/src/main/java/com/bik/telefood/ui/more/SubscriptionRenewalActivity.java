package com.bik.telefood.ui.more;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivitySubscriptionRenewalBinding;
import com.bik.telefood.model.entity.general.BankInformationModel;
import com.bik.telefood.model.entity.general.PackageModel;
import com.google.android.material.textview.MaterialTextView;

public class SubscriptionRenewalActivity extends AppCompatActivity implements SubscriptionsPackagesAdapter.OnSingleSelectionListener {

    private ActivitySubscriptionRenewalBinding binding;
    private SubscriptionsPackagesAdapter subscriptionsPackagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubscriptionRenewalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        MoreViewModel moreViewModel = new ViewModelProvider(this).get(MoreViewModel.class);
        moreViewModel.getBankInfo(this, getSupportFragmentManager()).observe(this, bankInfoResponse -> {
            BankInformationModel bankInformationModel = bankInfoResponse.getInformation();
            appendedText(R.string.label_bank_name, bankInformationModel.getBank(), binding.include.tvBankName);
            appendedText(R.string.label_account_number, bankInformationModel.getAccountNumber(), binding.include.tvAccountNumber);
            appendedText(R.string.label_the_full_name, bankInformationModel.getFullName(), binding.include.tvFullName);
            appendedText(R.string.label_iban, bankInformationModel.getIBAN(), binding.include.tvIban);
        });

        moreViewModel.getSubscriptionPackages(this, getSupportFragmentManager()).observe(this, packageResponse -> {
            subscriptionsPackagesAdapter = new SubscriptionsPackagesAdapter(packageResponse.getPackages(), this);
            binding.rvSubscription.setAdapter(subscriptionsPackagesAdapter);
        });

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

    @Override
    public void onSelect(PackageModel packageModel) {

    }
}