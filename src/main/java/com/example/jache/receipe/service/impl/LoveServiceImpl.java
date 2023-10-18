package com.example.jache.receipe.service.impl;

import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.constant.exception.CustomException;
import com.example.jache.receipe.dto.ReceipeDto;
import com.example.jache.receipe.dto.ReceipeLoveDto;
import com.example.jache.receipe.entity.Love;
import com.example.jache.receipe.entity.Receipe;
import com.example.jache.receipe.repository.LoveRepository;
import com.example.jache.receipe.repository.ReceipeRepository;
import com.example.jache.receipe.service.LoveService;
import com.example.jache.user.entity.Chef;
import com.example.jache.user.repository.ChefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoveServiceImpl implements LoveService {
    private final LoveRepository loveRepository;
    private final ChefRepository chefRepository;
    private final ReceipeRepository receipeRepository;

    @Override
    public Love getLove(Long receipeId, String chefName) {

        Receipe receipe = receipeRepository.findByReceipeId(receipeId).orElseThrow(
                ()-> new CustomException(CustomResponseStatus.RECEIPE_NOT_FOUND)
        );
        Chef chef = chefRepository.findByChefName(chefName).orElseThrow(
                () -> new CustomException(CustomResponseStatus.USER_NOT_FOUND)
        );
        if(loveRepository.findLoveByChefAndReceipe(chef,receipe).isEmpty()){
            Love newLove = Love.builder()
                    .receipe(receipe)
                    .chef(chef)
                    .status("y")
                    .build();
            loveRepository.save(newLove);
            receipe.addCount();
            return newLove;
        }
        else{
            return null;
        }
    }

    @Override
    public void love(Long receipeId, String chefName) {
        if(getLove(receipeId,chefName) == null){
            throw new CustomException(CustomResponseStatus.ALREADY_LOVE);
        }
    }

    @Override
    public void unLove(Long receipeId, String chefName) {
        Love delLove = getLove(receipeId,chefName);
        loveRepository.delete(delLove);
        Receipe receipe = receipeRepository.findByReceipeId(receipeId).orElseThrow();
        receipe.subCount();
    }

    @Override
    public ReceipeLoveDto.LoveStatusResDto getStatus(Long receipeId, String chefName) {
        String status = "";
        Love love = getLove(receipeId,chefName);
        if(love == null){
            status = "N";
        }
        else{
            status = love.getStatus();
        }
        return ReceipeLoveDto.LoveStatusResDto.builder()
                .status(status)
                .build();
    }

    @Override
    public List<ReceipeDto.ReadReceipeResDto> getScrapReceipe(String chefName) {

        List<Receipe> receipes = loveRepository.findReceipesByScrap(chefName);
        return receipes.stream()
                .map(ReceipeDto.ReadReceipeResDto::new)
                .collect(Collectors.toList());
    }
}
