package com.example.jache.receipe.service.impl;

import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.constant.exception.CustomException;
import com.example.jache.receipe.dto.ImgUploadDto;
import com.example.jache.receipe.dto.ReceipeDto;
import com.example.jache.receipe.entity.Ingredient;
import com.example.jache.receipe.entity.Orders;
import com.example.jache.receipe.entity.Receipe;
import com.example.jache.receipe.repository.IngredientRepository;
import com.example.jache.receipe.repository.OrdersRepository;
import com.example.jache.receipe.repository.ReceipeRepository;
import com.example.jache.receipe.service.ReceipeService;
import com.example.jache.s3.service.S3Service;
import com.example.jache.user.entity.Chef;
import com.example.jache.user.repository.ChefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReceipeServiceImpl implements ReceipeService {

    private final ReceipeRepository receipeRepository;
    private final IngredientRepository ingredientRepository;
    private final OrdersRepository ordersRepository;
    private final ChefRepository chefRepository;
    private final S3Service s3Service;
    @Override
    public ReceipeDto.InitialReceipeResDto initialReceipe(String chefName) {
        Chef chef = chefRepository.findByChefName(chefName).orElseThrow(
                () -> new CustomException(CustomResponseStatus.USER_NOT_FOUND)
        );
        Receipe receipe = Receipe.builder()
                .theme("S")
                .title("initial")
                .loveCount(0)
                .chef(chef)
                .receipeImgUrl("https://3rdprojectbucket.s3.ap-northeast-2.amazonaws.com/initial/receipeInitial.jpg")
                .build();
        receipeRepository.save(receipe);
        return ReceipeDto.InitialReceipeResDto.builder()
                .chefName(chefName)
                .receipeId(receipe.getReceipeId())
                .build();
    }

    @Override
    public Long createReceipe(ImgUploadDto uploadDto, ReceipeDto.CreateReceipeReqDto createReceipeReqDto, String chefName) {

        Receipe receipe = getReceipe(createReceipeReqDto.getReceipeId());

        if(!chefName.equals(receipe.getChef().getChefName())){
            throw new CustomException(CustomResponseStatus.UNAUTHORIZED_TOKEN);
        }
        String receipeUrl = "";

        if(uploadDto != null){
            receipeUrl = s3Service.uploadFile(uploadDto.getMultipartFile(),"receipe");
            receipe.modifyReceipeImgUrl(receipeUrl);
        }

        Sort sort = Sort.by(Sort.Direction.ASC,"createDate");
        List<Ingredient> ingredients = ingredientRepository.findIngredientsByReceipe(sort,receipe);
        List<Orders> orders = ordersRepository.findOrdersByReceipe(sort,receipe);

        receipe.createReceipe(ingredients,orders);
        receipe.modifyTitle(createReceipeReqDto.getTitle());
        if(!receipe.getTheme().equals(createReceipeReqDto.getTheme())){
            receipe.modifyTheme(createReceipeReqDto.getTheme());
        }
        receipe.modifyIntroduce(createReceipeReqDto.getIntroduce());


        receipeRepository.save(receipe);

        return receipe.getReceipeId();
    }

    @Override
    public ReceipeDto.ReadReceipeDetailResDto readOneReceipe(Long receipeId) {
        Receipe receipe = receipeRepository.findByReceipeId(receipeId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.RECEIPE_NOT_FOUND)
        );
        ReceipeDto.ReadReceipeDetailResDto details = new ReceipeDto.ReadReceipeDetailResDto(receipe);
        return details;
    }

    @Override
    public List<ReceipeDto.ReadReceipeResDto> readAllReceipesByTheme(String theme) {
        Sort sort = Sort.by(Sort.Direction.DESC,"createDate");
        List<Receipe> receipes = receipeRepository.findAllByTheme(sort, theme);
        return receipes.stream()
                .map(ReceipeDto.ReadReceipeResDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReceipeDto.ReadReceipeResDto> readReceipesByThemeOrderByScrap(String theme) {
        Sort sort = Sort.by(Sort.Direction.DESC,"loveCount");
        List<Receipe> receipes = receipeRepository.findAllByTheme(sort, theme);
        return receipes.stream()
                .map(ReceipeDto.ReadReceipeResDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Long updateReceipe(ImgUploadDto updateImgDto, ReceipeDto.CreateReceipeReqDto updateReceipeReqDto, long receipeId, String chefName) {
        Receipe receipe = getReceipe(receipeId);
        if(!chefName.equals(receipe.getChef().getChefName())){
            throw new CustomException(CustomResponseStatus.UNAUTHORIZED_TOKEN);
        }
        String updateImgUrl = "";
        if(updateImgDto.getMultipartFile().isEmpty()){
            updateImgUrl = "https://3rdprojectbucket.s3.ap-northeast-2.amazonaws.com/initial/receipeInitial.jpg";
        }
        else{
            s3Service.deleteFile(receipe.getReceipeImgUrl());
            updateImgUrl = s3Service.uploadFile(updateImgDto.getMultipartFile(),"receipe");
            receipe.modifyReceipeImgUrl(updateImgUrl);
        }
        if(!receipe.getTitle().equals(updateReceipeReqDto.getTitle())){
            receipe.modifyTitle(updateReceipeReqDto.getTitle());
        }
        if(!receipe.getIntroduce().equals(updateReceipeReqDto.getIntroduce())){
            receipe.modifyIntroduce(updateReceipeReqDto.getIntroduce());
        }
        if(!receipe.getTheme().equals(updateReceipeReqDto.getTheme())){
            receipe.modifyTheme(updateReceipeReqDto.getTheme());
        }
        receipeRepository.save(receipe);

        return receipe.getReceipeId();
    }

    @Override
    public void deleteReceipe(Long receipeId, String chefName) {
        Receipe receipe = receipeRepository.findByReceipeId(receipeId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.RECEIPE_NOT_FOUND)
        );
        if(receipe.getChef().getChefName().equals(chefName)){
            receipeRepository.delete(receipe);
        }
        else{
            throw new CustomException(CustomResponseStatus.UNAUTHORIZED_TOKEN);
        }
    }

    @Override
    public List<ReceipeDto.ReadReceipeResDto> readReceipesByThemeAndChef(String theme, String chefName) {
        Chef chef = getChef(chefName);
        Sort sort = Sort.by(Sort.Direction.DESC,"createDate");
        List<Receipe> my = receipeRepository.findAllByThemeAndChef(sort,theme,chef);
        return my.stream()
                .map(ReceipeDto.ReadReceipeResDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean getIsMyReceipe(String receipeWriter, String chefName) {
        if(receipeWriter.equals(chefName)){
            return true;
        }
        return false;
    }

    public Chef getChef(String chefName){
        return chefRepository.findByChefName(chefName).orElseThrow(
                () -> new CustomException(CustomResponseStatus.USER_NOT_FOUND)
        );
    }

    public Receipe getReceipe(long receipeId){
        return receipeRepository.findByReceipeId(receipeId).orElseThrow(
                () -> new CustomException(CustomResponseStatus.RECEIPE_NOT_FOUND)
        );
    }

}
