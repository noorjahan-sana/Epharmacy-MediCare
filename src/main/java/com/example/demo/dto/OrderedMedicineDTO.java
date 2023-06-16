package com.example.demo.dto;

import org.springframework.stereotype.Component;

@Component
public class OrderedMedicineDTO {
	private Integer orderedMedicineId;
	private Integer orderedQuantity;
	private MedicineDTO medicine;
	public Integer getOrderedMedicineId() {
		return orderedMedicineId;
	}
	public void setOrderedMedicineId(Integer orderedMedicineId) {
		this.orderedMedicineId = orderedMedicineId;
	}
	public Integer getOrderedQuantity() {
		return orderedQuantity;
	}
	public void setOrderedQuantity(Integer orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}
	public MedicineDTO getMedicine() {
		return medicine;
	}
	public void setMedicine(MedicineDTO medicine) {
		this.medicine = medicine;
	}

}
