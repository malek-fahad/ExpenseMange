package com.tecraa.emanager.ui.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tecraa.emanager.R;
import com.tecraa.emanager.Utils.Constants;
import com.tecraa.emanager.Utils.Helper;
import com.tecraa.emanager.adapters.AccountAdapter;
import com.tecraa.emanager.adapters.CategoryAdapter;
import com.tecraa.emanager.databinding.FragmentAddTransactionBinding;
import com.tecraa.emanager.databinding.ListDialogBinding;
import com.tecraa.emanager.models.Account;
import com.tecraa.emanager.models.Category;
import com.tecraa.emanager.models.Transaction;
import com.tecraa.emanager.ui.activities.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class AddTransactionFragment extends BottomSheetDialogFragment {


    public AddTransactionFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentAddTransactionBinding binding;

    Transaction transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTransactionBinding.inflate(inflater,container,false);


        transaction = new Transaction();

        binding.incomeBtn.setOnClickListener(v -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.textColor));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.greenColor));

            transaction.setType(Constants.INCOME);
        });
        binding.expenseBtn.setOnClickListener(v -> {
            binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            binding.expenseBtn.setTextColor(getContext().getColor(R.color.redColor));
            binding.incomeBtn.setTextColor(getContext().getColor(R.color.textColor));

            transaction.setType(Constants.EXPENSE);
        });

        binding.date.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
            datePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, view.getDayOfMonth());
                calendar.set(Calendar.MONTH, view.getMonth());
                calendar.set(Calendar.YEAR, view.getYear());

                //SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, YYYY");
                String dateToShow = Helper.formatDate(calendar.getTime());
                binding.date.setText(dateToShow);

                transaction.setDate(calendar.getTime());
                transaction.setId(calendar.getTime().getTime());

            });
            datePickerDialog.show();
        });

        binding.category.setOnClickListener(v -> {
            ListDialogBinding listDialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
            categoryDialog.setView(listDialogBinding.getRoot());





            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), Constants.categories, category -> {
                binding.category.setText(category.getCategoryName());
                transaction.setCategory(category.getCategoryName());
                categoryDialog.dismiss();
            });
            listDialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            listDialogBinding.recyclerView.setAdapter(categoryAdapter);
            categoryDialog.show();


        });

        binding.account.setOnClickListener(v -> {

            ListDialogBinding listDialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog accountsDialog = new AlertDialog.Builder(getContext()).create();
            accountsDialog.setView(listDialogBinding.getRoot());
            ArrayList<Account> accounts = new ArrayList<>();

            accounts.add(new Account(0,"Cash"));
            accounts.add(new Account(0,"Sonali Bank"));
            accounts.add(new Account(0,"bkash"));
            accounts.add(new Account(0,"Nagad"));
            accounts.add(new Account(0,"Other"));


            AccountAdapter adapter = new AccountAdapter(getContext(),accounts,account -> {
                binding.account.setText(account.getAccountName());
                transaction.setAccount(account.getAccountName());
                accountsDialog.dismiss();
            });
            listDialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            listDialogBinding.recyclerView.setAdapter(adapter);
            accountsDialog.show();

        });

        binding.saveTransactionBtn.setOnClickListener(v -> {
            double amount = Double.parseDouble(binding.amount.getText().toString());
            String note = binding.note.getText().toString();

            if (transaction.getType().equals(Constants.EXPENSE)){
                transaction.setAmount(amount*-1);
            }else {
                transaction.setAmount(amount);
            }
            transaction.setNote(note);

            ((MainActivity)getActivity()).viewModel.addTransactions(transaction);
            ((MainActivity)getActivity()).getTransactions();
            dismiss();




        });



        return binding.getRoot();
    }
}