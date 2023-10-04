package com.credinkamovil.pe.utils.views;

import android.text.Editable;

public interface TextWatcherExtendedListener {
    void afterTextChanged(Editable editable);

    void beforeTextChanged(CharSequence charSequence, int start, int count, int after);

    void onTextChanged(CharSequence charSequence, int start, int before, int count);
}
