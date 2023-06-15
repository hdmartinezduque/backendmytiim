package co.com.template.services;


import co.com.template.Repositories.PeriodRepository;
import co.com.template.Repositories.dto.PeriodDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.Repositories.entities.Period;
import co.com.template.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Transactional
@Service
@RequiredArgsConstructor
public class PeriodService {

    private final PeriodRepository periodRepository;

    public ResponseDTO getPeriod() {
        try {
            List<PeriodDTO> result = new ArrayList<>();
            List<Period> periods = periodRepository.findAll();
            periods.stream().forEach(period -> {
                result.add(new PeriodDTO(period.getPeriodId(), period.getDescribe()));

            });
            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, result);
        } catch (Exception err) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }
    }
}


