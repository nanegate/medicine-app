package com.nane.bilinliila

import android.app.AlertDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nane.bilinliila.databinding.ActivityMainBinding
import com.nane.bilinliila.databinding.DialogAddMedicineBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MedicineAdapter
    private lateinit var medicineList: MutableList<Medicine>
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("medicine_data", MODE_PRIVATE)
        medicineList = loadMedicines()
        adapter = MedicineAdapter(medicineList)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.fabAddMedicine.setOnClickListener { showAddMedicineDialog() }
    }

    private fun showAddMedicineDialog() {
        val dialogBinding = DialogAddMedicineBinding.inflate(LayoutInflater.from(this))

        val builder = AlertDialog.Builder(this)
            .setView(dialogBinding.root) // dialogBinding.root kullanarak View'ı ayarlayın
            .setPositiveButton("Ekle") { _, _ ->
                val name = dialogBinding.etMedicineName.text.toString()
                val serialNumber = dialogBinding.etSerialNumber.text.toString()
                val expiryDate = dialogBinding.etExpiryDate.text.toString()

                if (name.isEmpty() || serialNumber.isEmpty() || expiryDate.isEmpty()) {
                    Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show()
                } else {
                    val medicine = Medicine(name, serialNumber, expiryDate)
                    medicineList.add(medicine)
                    adapter.notifyItemInserted(medicineList.size - 1)
                    saveMedicines()
                }
            }
            .setNegativeButton("İptal", null)
        builder.show()
    }

    private fun saveMedicines() {
        val gson = Gson()
        val json = gson.toJson(medicineList)
        sharedPreferences.edit().putString("medicines", json).apply()
    }

    private fun loadMedicines(): MutableList<Medicine> {
        val json = sharedPreferences.getString("medicines", null)
        if(json != null) {
           // binding.textbn.visibility = View.GONE
        }
        val type = object : TypeToken<List<Medicine>>() {}.type
        return Gson().fromJson(json, type) ?: mutableListOf()
    }
}