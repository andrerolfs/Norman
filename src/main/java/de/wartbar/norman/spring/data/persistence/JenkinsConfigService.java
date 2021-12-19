package de.wartbar.norman.spring.data.persistence;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Service
public class JenkinsConfigService {

    @Autowired
    private JenkinsConfigRepository jenkinsConfigRepository;

    public void save(JenkinsConfigModel jenkinsConfigModel) {
        jenkinsConfigRepository.save(jenkinsConfigModel);
    }
    public void delete(JenkinsConfigModel jenkinsConfigModel) {
        jenkinsConfigRepository.delete(jenkinsConfigModel);
    }

    public List<JenkinsConfigModel> findAll() {
        return jenkinsConfigRepository.findAll();
    }
}

