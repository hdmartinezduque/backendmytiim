package co.com.template.services;


import co.com.template.Repositories.PeriodRepository;
import co.com.template.Repositories.dto.PeriodDTO;
import co.com.template.Repositories.dto.ResponseDTO;
import co.com.template.Repositories.entities.Period;
import co.com.template.utils.Constants;
import co.com.template.utils.StatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Transactional
@Service
@RequiredArgsConstructor
public class PeriodService {

    private final PeriodRepository periodRepository;

    public ResponseDTO getPeriod(Integer active) {
        try {
            List<PeriodDTO> result = new ArrayList<>();
            List<Period> periods;
            periods = active.equals(Constants.ONE_VALUE) ?
                    periodRepository.findByStatusStatusIdOrderByStartPeriodAsc(StatusEnum.ACTIVE_PERIOD.getId()) : periodRepository.findAll();
            periods.stream().forEach(period -> {
                result.add(new PeriodDTO(period.getPeriodId(), period.getDescribe()));

            });
            return new ResponseDTO(HttpStatus.OK, Constants.EMPTY_MESSAGE, result);
        } catch (Exception err) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST, err.getMessage(), null);
        }
    }

    public Period getActualPeriod() {
        Period period = null;
        try {
            LocalDate today =LocalDate.now();
            period = periodRepository.findByEndPeriodGreaterThanEqualAndStartPeriodLessThanEqual(today,today);
        } catch (Exception err) {
            log.error(err.getMessage(), err);
        }
        return period;
    }
    public Period getPreviousPeriod() {
        Period period = null;
        try {
            LocalDate today =LocalDate.now();
            period = periodRepository.findByEndPollGreaterThanEqualAndStartPollLessThanEqual(today,today);
        } catch (Exception err) {
            log.error(err.getMessage(), err);
        }
        return period;
    }
}


