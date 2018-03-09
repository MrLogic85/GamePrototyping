package se.sleepyduckstudio.gameprototyping

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.google.android.gms.ads.AdRequest
import com.google.firebase.analytics.FirebaseAnalytics
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private val text
        get() = "${getString(R.string.make_a)} ${theme_spinner.text} " +
                "${perspective_spinner.text} ${type_spinner.text} " +
                "${getString(R.string.game)}. ${getString(R.string.set)} " +
                "${setting_spinner.text}. ${getString(R.string.featuring)} " +
                "${feature_1_spinner.text} ${getString(R.string.and)} " +
                "${feature_2_spinner.text}."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        FirebaseAnalytics.getInstance(this)
        ad_view.loadAd(AdRequest.Builder().build())

        perspective_spinner.setFadingEdgeLength(0)

        refresh_button.setOnClickListener { randomize() }
        copy_button.setOnClickListener { copyToClipboard() }
        share_button.setOnClickListener { share() }

        randomize()
    }

    private fun copyToClipboard() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        clipboard?.primaryClip = ClipData.newPlainText(text, text)
        Toast.makeText(this, "Copied $text to clipboard", Toast.LENGTH_LONG).show()
    }

    private fun share() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, text)
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }

    private fun randomize() {
        val features = getRandomPropertySet(Property.Feature, 6)

        randomize(theme_spinner, Property.Theme)
        randomize(perspective_spinner, Property.Perspective)
        randomize(type_spinner, Property.Type)
        randomize(setting_spinner, Property.Setting)
        randomize(feature_1_spinner, features.subList(0, 3))
        randomize(feature_2_spinner, features.subList(3, 6))
    }

    private fun randomize(spinner: MaterialBetterSpinner, property: Property) {
        randomize(spinner, getRandomPropertySet(property, 3))
    }

    private fun randomize(spinner: MaterialBetterSpinner, values: List<String>) {
        spinner.setAdapter(ArrayAdapter(this, R.layout.list_item, values))
        spinner.setText(spinner.adapter.getItem(0) as String?)
        spinner.dismissDropDown()
    }
}