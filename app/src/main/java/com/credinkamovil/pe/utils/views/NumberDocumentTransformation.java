package com.credinkamovil.pe.utils.views;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

public class NumberDocumentTransformation extends PasswordTransformationMethod {
    public char mChar;
    public int maximunCharacter;
    public int minimunCharacter;

    private class PasswordCharSequence implements CharSequence {
        private CharSequence mSource;

        public PasswordCharSequence(CharSequence source) {
            this.mSource = source;
        }

        public char charAt(int index) {
            if (this.mSource.length() < NumberDocumentTransformation.this.minimunCharacter || this.mSource.length() > NumberDocumentTransformation.this.maximunCharacter) {
                return this.mSource.charAt(index);
            }
            if (index <= this.mSource.length() - 4) {
                return NumberDocumentTransformation.this.mChar;
            }
            return this.mSource.charAt(index);
        }

        public int length() {
            return this.mSource.length();
        }

        public CharSequence subSequence(int start, int end) {
            return this.mSource.subSequence(start, end);
        }
    }

    public CharSequence getTransformation(CharSequence source, View view) {
        return new PasswordCharSequence(source);
    }
}
