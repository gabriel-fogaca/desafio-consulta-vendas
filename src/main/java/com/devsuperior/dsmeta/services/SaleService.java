package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.dto.SellerSalesReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import java.time.format.DateTimeFormatter;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<SellerMinDTO> findSalesSummary(
			String minDate,
			String maxDate) {
		LocalDate endDate = (maxDate == null) ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()) : LocalDate.parse(maxDate);
		LocalDate startDate = (minDate == null) ? endDate.minusYears(1L) : LocalDate.parse(minDate);
		return repository.findSalesSummary(startDate, endDate);
	}

	public Page<SellerSalesReportDTO> findSalesReport(
			String minDate,
			String maxDate,
			String name,
			Pageable pageable) {
		LocalDate endDate = (maxDate == null) ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()) : LocalDate.parse(maxDate);
		LocalDate startDate = (minDate == null) ? endDate.minusYears(1L) : LocalDate.parse(minDate);
		Page<SellerSalesReportDTO> result = repository.findSalesReport(startDate, endDate, name, pageable);
		return result;
	}
}
