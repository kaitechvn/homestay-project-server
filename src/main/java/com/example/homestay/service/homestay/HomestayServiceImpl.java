package com.example.homestay.service.homestay;

import com.example.homestay.dto.reponse.HomestayResponse;
import com.example.homestay.dto.reponse.PagingResponse;
import com.example.homestay.dto.request.HomestayRequest;
import com.example.homestay.dto.request.HomestayUpdateRequest;
import com.example.homestay.dto.request.PagingRequest;
import com.example.homestay.exception.ErrorCode;
import com.example.homestay.exception.InputException;
import com.example.homestay.exception.NotFoundException;
import com.example.homestay.mapper.HomestayMapper;
import com.example.homestay.model.Homestay;
import com.example.homestay.repository.HomestayRepository;
import com.example.homestay.repository.specification.HomestaySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.homestay.exception.ErrorCode.HOMESTAY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class HomestayServiceImpl implements HomestayService {

    private final HomestayRepository homestayRepository;
    private final HomestayMapper homestayMapper;

    @Override
    public PagingResponse<HomestayResponse> list(PagingRequest pageRequest) {
        Pageable pageable = PageRequest.of(pageRequest.getPage() - 1, pageRequest.getSize());
        Page<Homestay> pageResult = homestayRepository.findAll(pageable);

        return PagingResponse.from(
                pageRequest.getPage(),
                pageRequest.getSize(),
                pageResult.getTotalElements(),
                pageResult.stream()
                        .map(homestayMapper::toHomestayResponse)
                        .toList());
    }

    @Override
    public HomestayResponse get(Integer id) {
        Homestay homestay = homestayRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(HOMESTAY_NOT_FOUND));

        return homestayMapper.toHomestayResponse(homestay);
    }

    @Override
    public HomestayResponse create(HomestayRequest homestayRequest) {

        Homestay homestay = homestayMapper.toHomestay(homestayRequest);
        homestay = homestayRepository.save(homestay);

        return homestayMapper.toHomestayResponse(homestay);
    }

    @Override
    public void update(HomestayUpdateRequest updateRequest, Integer id) {
        Homestay existingHomestay = homestayRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(HOMESTAY_NOT_FOUND));

        homestayMapper.updateHomestay(updateRequest, existingHomestay);
        homestayRepository.save(existingHomestay);
    }

    @Override
    public void delete(Integer id) {
        homestayRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(HOMESTAY_NOT_FOUND));

        homestayRepository.deleteById(id);
    }

    @Override
    public PagingResponse<HomestayResponse> filter(
            PagingRequest pageRequest,
            Integer districtId,
            Integer minPrice,
            Integer maxPrice,
            Integer guests,
            LocalDate checkIn,
            LocalDate checkOut
    ) {

        if (checkIn != null && checkOut != null) {

            // Ensure that check-in is after today
            if (!checkIn.isAfter(LocalDate.now())) {
                throw new InputException(ErrorCode.DATE_INVALID);
            }

            // Ensure check-out is after check-in
            if (!checkOut.isAfter(checkIn)) {
                throw new InputException(ErrorCode.DATE_INVALID);
            }
        }

        Pageable pageable = PageRequest.of(pageRequest.getPage() - 1, pageRequest.getSize());

        Specification<Homestay> spec = HomestaySpecification.byFilters(districtId, minPrice, maxPrice, guests, checkIn, checkOut);

        Page<Homestay> pageResult = homestayRepository.findAll(spec, pageable);

        // Map results to response
        return PagingResponse.from(
                pageRequest.getPage(),
                pageRequest.getSize(),
                pageResult.getTotalElements(),
                pageResult.stream()
                        .map(homestayMapper::toHomestayResponse)
                        .toList()
        );
    }

    public List<HomestayResponse> getTopRatedHomestays() {
        List<Homestay> homestays = homestayRepository.findAll();

        return homestays.stream()
                .sorted(Comparator.comparingDouble(Homestay::getRating)
                        .reversed()
                        .thenComparing(Homestay::getReviewCount, Comparator.reverseOrder()))
                .map(homestayMapper::toHomestayResponse)
                .collect(Collectors.toList());


    }

}