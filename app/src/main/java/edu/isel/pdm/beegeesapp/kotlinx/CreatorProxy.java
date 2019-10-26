package edu.isel.pdm.beegeesapp.kotlinx;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import edu.isel.pdm.beegeesapp.bgg.search.model.GameInfo;

/**
 * Work around for bug on the Android plugin that is responsible for generating the Parcelize
 * implementation
 *
 * @see [https://youtrack.jetbrains.com/issue/KT-19853]
 */
public class CreatorProxy {

    @NonNull
    @SuppressWarnings("unchecked")
    public static Parcelable.Creator<GameInfo> getChallengeInfoCreator() {
        return GameInfo.CREATOR;
    }
}
