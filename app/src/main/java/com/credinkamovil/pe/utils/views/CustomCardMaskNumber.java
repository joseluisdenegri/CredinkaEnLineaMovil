package com.credinkamovil.pe.utils.views;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

public class CustomCardMaskNumber {
    private EditText mEditText;
    private TextWatcherExtendedListener mWatcherExtendedListener;
    public boolean hasFormat;
    private CharSequence mMaskSecuence = "####  ####  ####  ####";
    public int lengthSize;
    private int sizeEditable;
    public boolean spaceDeleted;

    public CustomCardMaskNumber(EditText editText, TextWatcherExtendedListener mInterfaceEditable) {
        mEditText = editText;
        mWatcherExtendedListener = mInterfaceEditable;
        initCharSecuence();
    }

    public void initCharSecuence() {
        changedListenerTextWatcher(this.mMaskSecuence);
    }

    private void changedListenerTextWatcher(CharSequence charSequence) {
        mMaskSecuence = charSequence;
        if (validateEmpty()) {
            setFiltersEditText(charSequence.length());
            mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                    if (!spaceDeleted && validateEmpty()) {
                        lengthSize = charSequence.length();
                        mWatcherExtendedListener.beforeTextChanged(charSequence, start, count, after);
                    }
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!spaceDeleted && validateEmpty()) {
                        mWatcherExtendedListener.onTextChanged(charSequence, i, i1, i2);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (!spaceDeleted && validateEmpty()) {
                        spaceDeleted = true;
                        formatMaskEditText(editable);
                        mWatcherExtendedListener.afterTextChanged(editable);
                        spaceDeleted = false;
                    }
                }
            });
        }
    }

    public void formatMaskEditText(Editable editable) {
        if (!TextUtils.isEmpty(editable) && validateEmpty()) {
            int selectionStart = mEditText.getSelectionStart();
            InputFilter[] filters = editable.getFilters();
            editable.setFilters(new InputFilter[0]);
            StringBuilder sb = new StringBuilder(separarEspaciosTexto(editable.toString()));
            editable.clear();
            int length = this.mMaskSecuence.length();
            for (int i = 0; i < length && sb.length() > 0; i++) {
                char charAt = this.mMaskSecuence.charAt(i);
                char charAt2 = sb.charAt(0);
                if (charAt == '#') {
                    editable.append(charAt2);
                    sb.deleteCharAt(0);
                } else {
                    editable.append(charAt);
                }
            }
            editable.setFilters(filters);
            this.sizeEditable = editable.length();
            if (selectionStart < this.sizeEditable) {
                if (editable.toString().charAt(selectionStart) == ' ' && this.lengthSize < this.sizeEditable) {
                    selectionStart = selectionStart + 1 + 1;
                }
                if (this.lengthSize == 0 && this.lengthSize <= this.sizeEditable) {
                    selectionStart = this.sizeEditable - 1;
                }
            }
            if (selectionStart + 1 < this.sizeEditable) {
                this.mEditText.setSelection(selectionStart);
            }
        }
    }

    private String separarEspaciosTexto(String str) {
        if (TextUtils.isEmpty(str) || !validateEmpty()) {
            return str;
        }
        int length = this.mMaskSecuence.length();
        int length2 = str.length();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < length && i < length2) {
            char charAt = this.mMaskSecuence.charAt(i);
            char charAt2 = str.charAt(i);
            if (!(charAt2 == ' ' || charAt2 == charAt)) {
                sb.append(charAt2);
            }
            i++;
        }
        return sb.toString();
    }

    public boolean validateEmpty() {
        return !TextUtils.isEmpty(this.mMaskSecuence);
    }

    public void setFiltersEditText(int size) {
        this.mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(size)});
    }
}
