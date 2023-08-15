package dev.george.androidtask.data_binding_adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import dev.george.androidtask.R
import dev.george.androidtask.model.domain.CompetitionDomain
import dev.george.androidtask.model.domain.MatchStatusDomain


@SuppressLint("SetTextI18n")
@BindingAdapter("score")
fun TextView.score(competitionDomain: CompetitionDomain) {
    text = "${competitionDomain.score.homeScore ?: ' '} - ${competitionDomain.score.awayScore ?: ' '}"
}

@BindingAdapter("startAtStyle")
fun TextView.startAtStyle(competitionDomain: CompetitionDomain) {
    if (competitionDomain.status == MatchStatusDomain.CANCELLED) {
        paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        background = ResourcesCompat.getDrawable(resources, R.drawable.line_over_text, null)
    }
}

@BindingAdapter("matchStatusText")
fun TextView.matchStatusText(competitionDomain: CompetitionDomain?) {
    text = competitionDomain?.status?.status
}

@BindingAdapter("matchStatusBackground")
fun TextView.matchStatusBackground(competitionDomain: CompetitionDomain?) {
    competitionDomain?.let {
        background = ResourcesCompat.getDrawable(resources, it.status.background, null)
    }
}

@BindingAdapter("visibility")
fun View.visibility(visibility: Boolean? = null) {
    this.visibility = visibility?.let {
        if (it) View.VISIBLE else View.GONE
    } ?: View.GONE
}