package co.com.template.services;

import co.com.template.Repositories.ConfigurationSystemRepository;
import co.com.template.Repositories.entities.ConfigurationSystem;
import co.com.template.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class ConfigurationSystemService {

    private final ConfigurationSystemRepository repository;

    public List<ConfigurationSystem> getAllConfigs(){
        return repository.findAll();
    }
    public String getConfigValue(Long id){
        ConfigurationSystem  config = repository.findBySystemId(id);
        return Objects.isNull(config) ? null : config.getValue();
    }


}
