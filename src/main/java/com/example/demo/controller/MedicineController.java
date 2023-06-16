package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MedicineDTO;
import com.example.demo.exception.EpharmacyException;
import com.example.demo.service.MedicineService;
import com.example.demo.service.MedicineServiceImp;


@RestController
@RequestMapping(value="/medicine")
public class MedicineController {
	
	@Autowired
	private MedicineService medicineService;
	
	
	@GetMapping(value = "/getall")
	public ResponseEntity<List<MedicineDTO>> getAllMedicines() throws EpharmacyException{
		List<MedicineDTO> medicineDto = medicineService.getAllMedicines();
		return new ResponseEntity<List<MedicineDTO>>(medicineDto,HttpStatus.OK);
	}

	@GetMapping(value = "/getbycategory/{category}")
	public ResponseEntity<List<MedicineDTO>> getMedicinesByCategory(@PathVariable String category)throws EpharmacyException{
		List<MedicineDTO> medicineDto = medicineService.getMedicinesByCategory(category);
		return new ResponseEntity<List<MedicineDTO>>(medicineDto,HttpStatus.OK);
	}

	@GetMapping(value = "/getbyid/{medicineId}")
	public ResponseEntity<MedicineDTO> getMedicineById(@PathVariable Integer medicineId) throws EpharmacyException{
		MedicineDTO medicineDto = medicineService.getMedicineById(medicineId);
		return new ResponseEntity<MedicineDTO>(medicineDto,HttpStatus.OK);
	}

	@PutMapping(value = "/updatequantity/{medicineId}/{orderedQuantity}" )
	public ResponseEntity<String> updateMedicineQuantityAfterOrder(@PathVariable Integer medicineId,@PathVariable Integer orderedQuantity) throws EpharmacyException{
		String str = medicineService.updateMedicineQuantityAfterOrder(medicineId, orderedQuantity);
		return new ResponseEntity<String>(str,HttpStatus.OK);
		
	}

}