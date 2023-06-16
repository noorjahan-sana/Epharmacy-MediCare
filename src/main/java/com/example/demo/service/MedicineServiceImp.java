package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MedicineDTO;
import com.example.demo.entity.Medicine;
import com.example.demo.exception.EpharmacyException;
import com.example.demo.repository.MedicineRepository;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class MedicineServiceImp implements MedicineService{
	@Autowired
	private MedicineRepository medicineRepository;
	@Override
	public List<MedicineDTO> getAllMedicines() throws EpharmacyException {
		Iterable<Medicine> iterable = medicineRepository.findAll();

		List<MedicineDTO> medicineDtos = new ArrayList<>();
		for (Medicine medicine : iterable) {
			MedicineDTO medicineDto =new MedicineDTO();
			medicineDto.setMedicineId(medicine.getMedicineId());
			medicineDto.setMedicineName(medicine.getMedicineName());
			medicineDto.setCategory(medicine.getCategory());
			medicineDto.setDiscountPercent(medicine.getDiscountPercent());
			medicineDto.setExpiryDate(medicine.getExpiryDate());
			medicineDto.setManufacturer(medicine.getCategory());
			medicineDto.setManufacturingDate(medicine.getExpiryDate());
			medicineDto.setPrice(medicine.getPrice());
			medicineDto.setQuantity(medicine.getQuantity());
			medicineDtos.add(medicineDto);
		}
		return medicineDtos;
	}

	@Override
	public List<MedicineDTO> getMedicinesByCategory(String category) throws EpharmacyException {
		Iterable<Medicine> iterable = medicineRepository.findByCategory(category);
		List<MedicineDTO> medicineDtos = new ArrayList<>();
		for (Medicine medicine : iterable) {
			MedicineDTO medicineDto =new MedicineDTO();
			medicineDto.setMedicineId(medicine.getMedicineId());
			medicineDto.setMedicineName(medicine.getMedicineName());
			medicineDto.setCategory(medicine.getCategory());
			medicineDto.setDiscountPercent(medicine.getDiscountPercent());
			medicineDto.setExpiryDate(medicine.getExpiryDate());
			medicineDto.setManufacturer(medicine.getCategory());
			medicineDto.setManufacturingDate(medicine.getExpiryDate());
			medicineDto.setPrice(medicine.getPrice());
			medicineDto.setQuantity(medicine.getQuantity());
			medicineDtos.add(medicineDto);
		}
		return medicineDtos;
	}

	@Override
	public MedicineDTO getMedicineById(Integer medicineId) throws EpharmacyException {
		Optional<Medicine> optional = medicineRepository.findById(medicineId);
		Medicine medicine = optional.orElseThrow(()->new EpharmacyException("Medine Not Found"));
		MedicineDTO medicineDto = new MedicineDTO();
		medicineDto.setMedicineId(medicine.getMedicineId());
		medicineDto.setMedicineName(medicine.getMedicineName());
		medicineDto.setCategory(medicine.getCategory());
		medicineDto.setDiscountPercent(medicine.getDiscountPercent());
		medicineDto.setExpiryDate(medicine.getExpiryDate());
		medicineDto.setManufacturer(medicine.getCategory());
		medicineDto.setManufacturingDate(medicine.getExpiryDate());
		medicineDto.setPrice(medicine.getPrice());
		medicineDto.setQuantity(medicine.getQuantity());
		
		
		
		return medicineDto;
	}

	@Override
	public String updateMedicineQuantityAfterOrder(Integer medicineId, Integer orderedQuantity)
			throws EpharmacyException {
		Optional<Medicine> optional = medicineRepository.findById(medicineId);
		Medicine medicine = optional.orElseThrow(()->new EpharmacyException("Medine Not Found"));
		
		medicine.setQuantity(medicine.getQuantity()-orderedQuantity);
		
		return "Quantity updated successfully";
		
		
		
	}
	

}
