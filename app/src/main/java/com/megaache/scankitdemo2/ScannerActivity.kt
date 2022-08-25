package com.megaache.scankitdemo2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.megaache.scankitdemo2.databinding.ActivityScannerBinding

const val REQ_CODE = 1
const val PERMISSION_REQ_CODE = 2

class ScannerActivity : AppCompatActivity() {

    lateinit var binding: ActivityScannerBinding
    lateinit var options: HmsScanAnalyzerOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initScanKit()
        initUI()
        requestCameraPermission()
    }

    private fun requestCameraPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQ_CODE
        )
    }


    private fun initUI() {
        binding.apply {
            scanBtn.setOnClickListener {
                launchScanScreen()
            }
            goToGeneratorActivity.setOnClickListener {
                startActivity(Intent(this@ScannerActivity,GeneratorActivity::class.java))
            }
        }
    }

    private fun initScanKit() {
        options = HmsScanAnalyzerOptions.Creator()
            .setHmsScanTypes(
                HmsScan.QRCODE_SCAN_TYPE,
                HmsScan.UPCCODE_E_SCAN_TYPE,
                HmsScan.UPCCODE_A_SCAN_TYPE
            )
            .create()
    }

    private fun launchScanScreen() {
        ScanUtil.startScan(this, REQ_CODE, options)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE) {
            val result = data?.getParcelableExtra<HmsScan>(ScanUtil.RESULT)
            result?.let {
                updateUI(it)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(hmsScan: HmsScan) {
        binding.apply {
            type.text = "Code Type: " + when (hmsScan.scanType) {
                HmsScan.QRCODE_SCAN_TYPE -> "QR Code"
                HmsScan.UPCCODE_A_SCAN_TYPE -> "UPC-A Code"
                HmsScan.UPCCODE_E_SCAN_TYPE -> "UPC-E Code"
                else -> "Unknown!"
            }
            orignalValue.text = "Original: ${hmsScan.originalValue}"
            displayValue.text = "Display: ${hmsScan.showResult}"
        }
    }
}