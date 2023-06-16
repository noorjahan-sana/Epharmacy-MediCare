package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.MedicineDTO;
import com.example.demo.exception.EpharmacyException;

public interface MedicineService {
	List<MedicineDTO> getAllMedicines() throws EpharmacyException;
	List<MedicineDTO> getMedicinesByCategory(String category)throws EpharmacyException;

	MedicineDTO getMedicineById(Integer medicineId) throws EpharmacyException;

	String updateMedicineQuantityAfterOrder(Integer medicineId, Integer orderedQuantity) throws EpharmacyException;

}
