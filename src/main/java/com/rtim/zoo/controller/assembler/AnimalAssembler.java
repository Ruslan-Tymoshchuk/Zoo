package com.rtim.zoo.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.rtim.zoo.controller.AnimalRestController;
import com.rtim.zoo.dto.AnimalDto;

@Component
public class AnimalAssembler implements RepresentationModelAssembler<AnimalDto, EntityModel<AnimalDto>> {

    @Override
    public EntityModel<AnimalDto> toModel(AnimalDto animalDto) {
        return EntityModel.of(animalDto,
                linkTo(methodOn(AnimalRestController.class).getAnimalById(animalDto.getId())).withSelfRel());
    }
}