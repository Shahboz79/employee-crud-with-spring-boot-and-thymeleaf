package uz.pdp.springbootexample.service;

import org.springframework.stereotype.Service;
import uz.pdp.springbootexample.entity.Position;
import uz.pdp.springbootexample.repository.PositionRepository;

import java.util.List;

@Service
public class PositionService {

   final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public  Position findById(Integer id) {
        return positionRepository.getOne(id);
    }


    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    public void savePosition(Position position) {
        positionRepository.save(position);
    }
}
