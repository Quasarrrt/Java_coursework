package tea_shop.services;

import tea_shop.models.Tea;
import tea_shop.repositories.TeaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeaService {

    @Autowired
    private TeaRepository teaRepository;

    @Transactional(readOnly = true)
    public List<Tea> getBySort(String sort) {
        List<Tea> teas = teaRepository.findAllBySort(sort);
        teas.sort(Comparator.comparing(Tea::getId));
        return teas;
    }

    @Transactional(readOnly = true)
    public Tea getByName(List<Tea> teas, String name){
        for (Tea tea : teas) {
            if (tea.getName().equals(name))
                return tea;
        }
        return null;
    }
}
