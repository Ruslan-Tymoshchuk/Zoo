package com.rtim.zoo.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rtim.zoo.domain.Animal;
import com.rtim.zoo.dto.AnimalDto;
import com.rtim.zoo.dto.MapstractMapper;
import com.rtim.zoo.exception.AnimalNickNameAlredyExistsException;
import com.rtim.zoo.exception.ResourceNotFoundException;
import com.rtim.zoo.repository.AnimalRepository;
import com.rtim.zoo.service.AnimalService;

@Service
@Transactional(readOnly = true)
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final MapstractMapper animalMapper;

    public AnimalServiceImpl(AnimalRepository animalRepository, MapstractMapper animalMapper) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
    }

    @Override
    public List<AnimalDto> findAllAnimals() {
        return animalRepository.findAll().stream().map(animalMapper::animalToAnimalDto).toList();
    }

    @Override
    public List<AnimalDto> findAllAnimalsByUserId(Integer userId) {
        return animalRepository.findByPersonId(userId).stream().map(animalMapper::animalToAnimalDto).toList();
    }

    @Override
    @Transactional
    public AnimalDto createAnimal(AnimalDto animalDto) {
        if (!animalRepository.existsByNickName(animalDto.getNickName())) {
            Animal animal = animalRepository.save(animalMapper.animalDtoToAnimal(animalDto));
            return animalMapper.animalToAnimalDto(animal);
        } else {
            throw new AnimalNickNameAlredyExistsException("Animal with that nickname already exists");
        }
    }

    @Override
    public AnimalDto getAnimalById(Integer id) {
        return animalMapper.animalToAnimalDto(animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find animal with id: ", id)));
    }

    @Override
    @Transactional
    public void updateAnimal(AnimalDto animalDto) {
        if (!animalRepository.existsByNickName(animalDto.getNickName())) {
            Animal animal = animalRepository.save(animalMapper.animalDtoToAnimal(animalDto));
            animalMapper.animalToAnimalDto(animal);
        } else {
            throw new AnimalNickNameAlredyExistsException("Animal with that nickname already exists");
        }
    }

    @Override
    @Transactional
    public void deleteAnimalById(Integer id) {
        if (animalRepository.existsById(id)) {
            animalRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Could not find animal with id: ", id);
        }
    }
}