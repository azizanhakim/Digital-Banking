package mlpt.siemo.digitalbanking

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import mlpt.siemo.digitalbanking.base.MainItem
import mlpt.siemo.digitalbanking.module.otp.OTPActivity
import mlpt.siemo.digitalbanking.module.fingerprint.FingerPrintActivity
import java.security.AccessController.getContext

class MainActivity : AppCompatActivity() {

    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupData()
        setupView()
        Log.e("SimStateReceiver", ">>>>" +SPManager.getString(this, "SimStateReceiver"))

        val a = Settings.Secure.getString(contentResolver, Settings.Secure.DEVICE_PROVISIONED)
        Log.e("SimStateReceiver", a)
    }

    private fun setupData() {
        mainAdapter = MainAdapter {
            when (it.icon) {
                0,1 -> startActivity(Intent(this, OTPActivity::class.java))
                2 -> startActivity(Intent(this, FingerPrintActivity::class.java))
            }
        }

        mainAdapter.addItem(MainItem(0, "OTP", ""))
        mainAdapter.addItem(MainItem(1, "PIN", ""))
        mainAdapter.addItem(MainItem(2, "Finger Print", ""))
        mainAdapter.addItem(MainItem(3, "Face ID", ""))
        mainAdapter.addItem(MainItem(4, "NFC", ""))
        mainAdapter.addItem(MainItem(4, "QRIS", ""))
        mainAdapter.addItem(MainItem(4, "Payment", ""))
    }

    private fun setupView() {
        recyclerview.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity)
            this.overScrollMode = View.OVER_SCROLL_NEVER
            this.adapter = mainAdapter
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ContextWrapper(newBase))
    }
}
