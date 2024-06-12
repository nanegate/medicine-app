package com.nane.bilinliila


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nane.bilinliila.databinding.ItemMedicineBinding

class MedicineAdapter(private var medicineList: MutableList<Medicine>) :
    RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    class MedicineViewHolder(val binding: ItemMedicineBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding = ItemMedicineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = medicineList[position]
        holder.binding.tvMedicineName.text = medicine.name
        holder.binding.tvSerialNumber.text = "Seri No: ${medicine.serialNumber}"
        holder.binding.tvExpiryDate.text = "Son Kullanma Tarihi: ${medicine.expiryDate}"
    }

    override fun getItemCount(): Int = medicineList.size

    fun setMedicines(medicines: List<Medicine>) {
        this.medicineList = medicines.toMutableList()
        notifyDataSetChanged()
    }
}