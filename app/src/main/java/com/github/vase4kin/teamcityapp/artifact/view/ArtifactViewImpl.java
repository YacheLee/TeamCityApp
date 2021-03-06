/*
 * Copyright 2016 Andrey Tolpeev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.vase4kin.teamcityapp.artifact.view;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cocosw.bottomsheet.BottomSheet;
import com.github.vase4kin.teamcityapp.R;
import com.github.vase4kin.teamcityapp.artifact.api.File;
import com.github.vase4kin.teamcityapp.artifact.data.ArtifactDataModel;
import com.github.vase4kin.teamcityapp.base.list.view.BaseListViewImpl;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;

import tr.xip.errorview.ErrorView;

/**
 * Impl of {@link ArtifactView}
 */
public class ArtifactViewImpl extends BaseListViewImpl<ArtifactDataModel, ArtifactAdapter> implements ArtifactView {

    private MaterialDialog mProgressDialog;
    private Snackbar mSnackBar;
    private OnArtifactPresenterListener mListener;

    public ArtifactViewImpl(View mView,
                            Activity activity,
                            @StringRes int emptyMessage,
                            ArtifactAdapter adapter) {
        super(mView, activity, emptyMessage, adapter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnArtifactPresenterListener(OnArtifactPresenterListener listener) {
        this.mListener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initViews(@NonNull ErrorView.RetryListener retryListener, @NonNull SwipeRefreshLayout.OnRefreshListener refreshListener) {
        super.initViews(retryListener, refreshListener);
        mProgressDialog = new MaterialDialog.Builder(mActivity)
                .title(R.string.download_artifact_dialog_title)
                .content(R.string.progress_dialog_content)
                .progress(true, 0)
                .widgetColor(Color.GRAY)
                .autoDismiss(false)
                .negativeText(R.string.text_cancel_button)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        mListener.unSubscribe();
                        mProgressDialog.dismiss();
                    }
                })
                .build();
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showData(ArtifactDataModel dataModel) {
        mAdapter.setDataModel(dataModel);
        mAdapter.setOnClickListener(mListener);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showRetryDownloadArtifactSnackBar(final OnArtifactPresenterListener listener) {
        mSnackBar = Snackbar.make(
                mView,
                R.string.download_artifact_retry_snack_bar_text,
                Snackbar.LENGTH_LONG)
                .setAction(R.string.download_artifact_retry_snack_bar_retry_button, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.downloadArtifactFile();
                    }
                });
        TextView textView = (TextView) mSnackBar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        mSnackBar.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showFullBottomSheet(File artifactFile) {
        buildBottomSheetDialog(artifactFile).show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showFolderBottomSheet(File artifactFile) {
        BottomSheet bottomSheet = buildBottomSheetDialog(artifactFile);
        bottomSheet.getMenu().findItem(R.id.download).setVisible(false);
        bottomSheet.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showBrowserBottomSheet(File artifactFile) {
        BottomSheet bottomSheet = buildBottomSheetDialog(artifactFile);
        bottomSheet.getMenu().findItem(R.id.open).setVisible(false);
        bottomSheet.getMenu().findItem(R.id.open_in_browser).setVisible(true);
        bottomSheet.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showDefaultBottomSheet(File artifactFile) {
        BottomSheet bottomSheet = buildBottomSheetDialog(artifactFile);
        bottomSheet.getMenu().findItem(R.id.open).setVisible(false);
        bottomSheet.show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int recyclerViewId() {
        return R.id.artifact_recycler_view;
    }

    /**
     * Build bottom sheet
     *
     * @param artifactFile - Artifact file
     * @return Bottom sheet
     */
    private BottomSheet buildBottomSheetDialog(final File artifactFile) {
        BottomSheet bottomSheet = new BottomSheet.Builder(mActivity)
                .title(artifactFile.getName())
                .sheet(R.menu.menu_artifact)
                .listener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.download:
                                mListener.downloadArtifactFile(artifactFile);
                                break;
                            case R.id.open:
                                mListener.openArtifactFile(artifactFile);
                                break;
                            case R.id.open_in_browser:
                                mListener.startBrowser(artifactFile);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                }).build();
        bottomSheet.getMenu().findItem(R.id.download)
                .setIcon(new IconDrawable(mActivity, MaterialIcons.md_file_download));
        bottomSheet.getMenu().findItem(R.id.open)
                .setIcon(new IconDrawable(mActivity, MaterialIcons.md_open_in_new));
        bottomSheet.getMenu().findItem(R.id.open_in_browser)
                .setIcon(new IconDrawable(mActivity, MaterialIcons.md_open_in_browser))
                .setVisible(false);
        return bottomSheet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onArtifactTabChangeEvent() {
        if (mSnackBar != null && mSnackBar.isShown()) {
            mSnackBar.dismiss();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPermissionsDeniedDialog() {
        new MaterialDialog.Builder(mActivity)
                .title(R.string.permissions_dialog_title)
                .content(R.string.permissions_dialog_text_no_permissions)
                .widgetColor(Color.GRAY)
                .positiveText(R.string.dialog_ok_title)
                .show();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showPermissionsInfoDialog(final OnPermissionsDialogListener onPermissionsDialogListener) {
        new MaterialDialog.Builder(mActivity)
                .title(R.string.permissions_dialog_title)
                .content(R.string.permissions_dialog_content)
                .widgetColor(Color.GRAY)
                .positiveText(R.string.dialog_ok_title)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        onPermissionsDialogListener.onAllow();
                    }
                })
                .show();
    }
}
