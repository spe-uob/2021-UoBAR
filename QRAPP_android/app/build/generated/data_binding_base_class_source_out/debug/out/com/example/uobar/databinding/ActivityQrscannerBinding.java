// Generated by view binder compiler. Do not edit!
package com.example.uobar.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.budiyev.android.codescanner.CodeScannerView;
import com.example.uobar.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityQrscannerBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView scanResult;

  @NonNull
  public final CodeScannerView scanner;

  private ActivityQrscannerBinding(@NonNull ConstraintLayout rootView, @NonNull TextView scanResult,
      @NonNull CodeScannerView scanner) {
    this.rootView = rootView;
    this.scanResult = scanResult;
    this.scanner = scanner;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityQrscannerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityQrscannerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_qrscanner, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityQrscannerBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.scanResult;
      TextView scanResult = ViewBindings.findChildViewById(rootView, id);
      if (scanResult == null) {
        break missingId;
      }

      id = R.id.scanner;
      CodeScannerView scanner = ViewBindings.findChildViewById(rootView, id);
      if (scanner == null) {
        break missingId;
      }

      return new ActivityQrscannerBinding((ConstraintLayout) rootView, scanResult, scanner);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
