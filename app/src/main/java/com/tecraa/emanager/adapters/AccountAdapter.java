package com.tecraa.emanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tecraa.emanager.R;
import com.tecraa.emanager.databinding.RowAccountBinding;
import com.tecraa.emanager.models.Account;
import com.tecraa.emanager.models.Category;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    Context context;
    ArrayList<Account> accountArrayList;

    public interface AccountClickListener{
        void onAccountClick(Account account);

    }

    AccountClickListener accountClickListener;

    public AccountAdapter(Context context, ArrayList<Account> accountArrayList, AccountClickListener accountClickListener) {
        this.context = context;
        this.accountArrayList = accountArrayList;
        this.accountClickListener = accountClickListener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(context).inflate(R.layout.row_account,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = accountArrayList.get(position);

        holder.binding.accounName.setText(account.getAccountName());
        holder.itemView.setOnClickListener(v -> {
            accountClickListener.onAccountClick(account);
        });

    }

    @Override
    public int getItemCount() {
        return accountArrayList.size();
    }

    public class  AccountViewHolder extends RecyclerView.ViewHolder{
        RowAccountBinding binding;
        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowAccountBinding.bind(itemView);
        }
    }
}
