package com.example.jache.receipe.service.impl;

import com.example.jache.constant.enums.CustomResponseStatus;
import com.example.jache.constant.exception.CustomException;
import com.example.jache.receipe.dto.ReceipeDto;
import com.example.jache.receipe.dto.ReceipeImgUploadDto;
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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceipeServiceImpl implements ReceipeService {

    private final ReceipeRepository receipeRepository;
    private final IngredientRepository ingredientRepository;
    private final OrdersRepository ordersRepository;
    private final ChefRepository chefRepository;
    private final S3Service s3Service;
    @Override
    public ReceipeDto.InitialReceipeResDto initialReceipe(ReceipeDto.InitialReceipeReqDto initial, String chefName) {
        Chef chef = chefRepository.findByChefName(chefName).orElseThrow(
                () -> new CustomException(CustomResponseStatus.USER_NOT_FOUND)
        );
        Receipe receipe = Receipe.builder()
                .theme(initial.getTheme())
                .title("initial")
                .loveCount(0)
                .chef(chef)
                .build();
        receipeRepository.save(receipe);
        return ReceipeDto.InitialReceipeResDto.builder()
                .chefName(chefName)
                .receipeId(receipe.getReceipeId())
                .build();
    }

    @Override
    public Long createReceipe(ReceipeImgUploadDto uploadDto,ReceipeDto.CreateReceipeReqDto createReceipeReqDto, String chefName) {
        Chef chef = chefRepository.findByChefName(chefName).orElseThrow(
                ()-> new CustomException(CustomResponseStatus.USER_NOT_FOUND)
        );

        Receipe receipe = receipeRepository.findReceipeByChef(chef).orElseThrow(
                ()-> new CustomException(CustomResponseStatus.RECEIPE_NOT_FOUND)
        );

        if(!chefName.equals(receipe.getChef().getChefName())){
            throw new CustomException(CustomResponseStatus.UNAUTHORIZED_TOKEN);
        }
        String receipeUrl = "";
        if(uploadDto.getMultipartFile() == null){
            receipeUrl = "https://3rdprojectbucket.s3.ap-northeast-2.amazonaws.com/initial/receipeInitial.jpg";
        }
        else{
            receipeUrl = s3Service.uploadFile(uploadDto.getMultipartFile(),"receipe");
        }

        List<Ingredient> ingredients = ingredientRepository.findIngredientsByReceipe(receipe);
        List<Orders> orders = ordersRepository.findOrdersByReceipe(receipe);

        receipe.createReceipe(ingredients,orders);
        receipe.modifyTitle(createReceipeReqDto.getTitle());
        receipe.modifyIntroduce(createReceipeReqDto.getIntroduce());
        receipe.modifyReceipeImgUrl(receipeUrl);

        return receipe.getReceipeId();
    }

    @Override
    public ReceipeDto.ReadReceipeDetailResDto readOneReceipe(Long receipeId) {
        return null;
    }

    @Override
    public List<ReceipeDto.ReadReceipeResDto> readReceipesTheme(String theme) {
        return null;
    }

    @Override
    public ReceipeDto.ReadReceipeDetailResDto updateReceipe(ReceipeDto.CreateReceipeReqDto createReceipeReqDto) {
        return null;
    }

    @Override
    public void deleteReceipe(Long receipeId) {

    }
}
