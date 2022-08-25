package com.megaache.scankitdemo2

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsBuildBitmapOption
import com.huawei.hms.ml.scan.HmsScan
import com.megaache.scankitdemo2.databinding.ActivityGeneratorBinding

class GeneratorActivity : AppCompatActivity() {

    lateinit var binding: ActivityGeneratorBinding
    lateinit var option: HmsBuildBitmapOption

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneratorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initScanKit()
        initUI()
    }

    private fun initScanKit() {
        option = HmsBuildBitmapOption.Creator()
            .setBitmapMargin(3)
            .setBitmapColor(Color.BLACK)
            .setBitmapBackgroundColor(Color.WHITE)
            .create()
    }

    private fun initUI() {
        binding.apply {
            generateBtn.setOnClickListener {
                val value = valueInput.text.toString()
                generateQRCode(value)
            }
        }
    }

    private fun generateQRCode(value: String) {
        //generate bitmap
        val width = 300
        val height = 300
        val type = HmsScan.QRCODE_SCAN_TYPE
        val bitmap = ScanUtil.buildBitmap(value, type, width, height, option)

        //show bitmap
        binding.qrCodeImage.setImageBitmap(bitmap)
    }
}