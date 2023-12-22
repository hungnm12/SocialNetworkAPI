package com.example.Social.Network.API.Service.Impl;

import com.example.Social.Network.API.Constant.ResponseCode;
import com.example.Social.Network.API.Constant.ResponseMessage;
import com.example.Social.Network.API.Exception.ResponseException;
import com.example.Social.Network.API.Model.Entity.*;
import com.example.Social.Network.API.Model.ReqDto.PostReqDto.GetListPostsReqDto;
import com.example.Social.Network.API.Model.ReqDto.PostReqDto.GetMarkCommentReqDto;
import com.example.Social.Network.API.Model.ReqDto.PostReqDto.GetPostReqDto;
import com.example.Social.Network.API.Model.ReqDto.PostReqDto.SetMarkCommentReqDto;
import com.example.Social.Network.API.Model.ReqDto.SearchReqRelatedDto.DelSavedSearchReqDto;
import com.example.Social.Network.API.Model.ReqDto.SearchReqRelatedDto.GetSavedSearchReqDto;
import com.example.Social.Network.API.Model.ReqDto.SearchReqRelatedDto.SearchFunctionReqDto;
import com.example.Social.Network.API.Model.ResDto.GeneralResponse;
import com.example.Social.Network.API.Model.ResDto.PostResDto.*;
import com.example.Social.Network.API.Model.ResDto.SearchResDto.GetSavedSearchResDto;
import com.example.Social.Network.API.Model.ResDto.SearchResDto.SearchFunctionResDto;
import com.example.Social.Network.API.Repository.*;
import com.example.Social.Network.API.Service.PostService;
import com.example.Social.Network.API.utils.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static com.example.Social.Network.API.utils.JwtUtils.getUserFromToken;


@Service
@Slf4j
@Transactional
public class PostServiceImpl implements PostService {
@Autowired
private JwtService jwtService;

@Autowired
private PostRepo postRepo;

@Autowired
private ImageRepo imageRepo;

@Autowired
private S3Service s3Service;
@Autowired
private UserRepo userRepo;
@Autowired
private BlockListRepo blockListRepo;
@Autowired
private SearchRepo searchRepo;

@Autowired
private CommentRepo commentRepo;


@Autowired
private MarkRepo markRepo;
@Override
    public GeneralResponse addPost(String token, MultipartFile image, MultipartFile video, String described, String status)
            throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
    User user = getUserFromToken(jwtService,userRepo, token);
    if(user == null)
    {
        return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED);

    }
    if (!jwtService.isTokenValid(token , user)){
        return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"");
    }
    Post post1 = new Post();
    post1.setUser(user);
    post1.setDescribed(described);
    post1.setStatus(status);
    post1.setUrl(generatePostUrl());
    if (user.getCoins() < 4) {
        return new GeneralResponse(ResponseCode.NOT_ENOUGH_COINS, ResponseMessage.NOT_ENOUGH_COINS, "");
    } else {
        chargeOnPost(user, post1);
    }
    if (image != null &&!image.isEmpty() ) {


        try {
            image.getOriginalFilename();
            String urlImg = s3Service.uploadFile(image).get("url");
            Image image1 = new Image();
            if (image.getOriginalFilename() != null && !image.getOriginalFilename().isEmpty()) {
                int imageCount = Math.toIntExact(imageRepo.countByPostId(post1.getId()));
                if (imageCount >= 5) {
                    return new GeneralResponse(ResponseCode.MAX_NUM_OF_IMG, ResponseMessage.MAX_NUM_OF_IMG, "");
                }
            }
            if (isSufficientSize(image)) {
                return new GeneralResponse(ResponseCode.FILE_SIZE_TOO_BIG, ResponseMessage.FILE_SIZE_TOO_BIG, "");
            }
            if (isSufficientSize(video)) {
                return new GeneralResponse(ResponseCode.FILE_SIZE_TOO_BIG, ResponseMessage.FILE_SIZE_TOO_BIG, "");
            }
            image1.setUrlImage(urlImg);
            Video video1 = new Video(urlImg, post1);


        } catch (RuntimeException e) {
            return new GeneralResponse(ResponseCode.EXCEPTION_ERROR, ResponseMessage.EXCEPTION_ERROR, "");

        }

    }
    postRepo.save(post1);
    return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, new PostDto(post1.getId(), post1.getUrl(), user.getCoins()));
    }


    private boolean isSufficientSize(MultipartFile file) {
        if (file == null) {
            return false;
        }
        long fileSize = file.getSize();
        long fileSizeInMb  = fileSize / (1024 * 1024);

        return fileSizeInMb > 10;
    }

    private String generatePostUrl() {
        // Generate a random UUID
        UUID uuid = UUID.randomUUID();

        // Convert UUID to a string
        String uuidString = uuid.toString();

        // Create a URL using the UUID string
        String url = "https://anti-fb.com/posts/" + uuidString;

        return url;
    }


    private void chargeOnPost(User user, Post post) {

        int currentCoin = user.getCoins();

        currentCoin -= 4;

        user.setCoins(currentCoin);

        userRepo.save(user);

    }
// block do violate community standards --> banned, can_mark, can_rate
    @Override
    public GeneralResponse getPost(String token, Long Id) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        User user = getUserFromToken(jwtService, userRepo, token);
        if (user == null) {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED, ResponseMessage.USER_NOT_VALIDATED);

        }
        if (!jwtService.isTokenValid(token, user)) {
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID, "");
        }
        if (!user.isAccountNonLocked()) {
            return new GeneralResponse(ResponseCode.ACTION_BEEN_DONE_PRE, ResponseMessage.ACTION_BEEN_DONE_PRE, "");

        }

        Post post = postRepo.findById(Id).orElseThrow();
try { }
catch (RuntimeException e) {
        return new GeneralResponse(ResponseCode.POST_NOT_EXIST, ResponseMessage.POST_NOT_EXIST, "");
    }

    List<Image> images = post.getImages();

//    for (Image image : images) {
//        ImageResDto imageResDto = new ImageResDto();
//        imageResDto.setUrl(image.getUrlImage());
//        imageResDto.setId(String.valueOf(image.getId()));
//        imageResDtos.add(imageResDto);
//    }

    List<Video> videos = post.getVideos();
//    List<VideoResDto> videoResDtos = new ArrayList<>();
//    for (Video video : videos) {
//        VideoResDto videoResDto = new VideoResDto();
//        videoResDto.setUrl(video.getUrl());
//        videoResDto.setThumb(video.getThumb());
//        videoResDtos.add(videoResDto);
//    }

    Author author = new Author();
    author.setId(String.valueOf(user.getId()));
    author.setName(user.getUsername());
    author.setAvatar(user.getAvatar());
    author.setCoins(String.valueOf(user.getCoins()));
//    author.setListings(user.getListing().toString());
    System.out.println(author);

    Category category = new Category();
    category.setId(category.getId());
    category.setName(category.getName());
    category.setHas_name(category.getHas_name());
    System.out.println(category);
    GetPostResDto getPostResDto = new GetPostResDto();
    getPostResDto.setId(post.getId());
    getPostResDto.setUrl(post.getUrl());
    getPostResDto.setCreated(String.valueOf(post.getCreated()));
    getPostResDto.setModified(String.valueOf(post.getModified()));
    getPostResDto.setDisappointed(String.valueOf(post.getDissapointed()));
    getPostResDto.setKudos(String.valueOf(post.getKudos()));
//    getPostResDto.setFake(String.valueOf(Long.valueOf(post.getFake())));
        getPostResDto.setFake("");
//    getPostResDto.setTrust(String.valueOf(Long.valueOf(post.getTrust())));
        getPostResDto.setTrust("");
    getPostResDto.setIsMarked(String.valueOf(post.isMarked()));
    getPostResDto.setIsRated(String.valueOf(post.isRated()));
    getPostResDto.setImage(images);
    getPostResDto.setVideo(videos);
    getPostResDto.setAuthor(author);
    getPostResDto.setCategory(category);
    System.out.println(getPostResDto);
    return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, getPostResDto);



        }


    @Override
    public GeneralResponse getListPosts(GetListPostsReqDto getListPostsReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        User user = getUserFromToken(jwtService,userRepo, getListPostsReqDto.getToken());
        if(user == null ){
            return  new GeneralResponse(ResponseCode.USER_NOT_VALIDATED,ResponseMessage.USER_NOT_VALIDATED);
        }


        Pageable paging = PageRequest.of(getListPostsReqDto.getIndex(), getListPostsReqDto.getCount());

        List<Post> posts = postRepo.getListPosts(getListPostsReqDto.getId(),paging);



        List<PostResDto> postResDtos = new ArrayList<>();
        for (Post post : posts) {
            PostResDto postResDto = new PostResDto();


            List<Image> images = post.getImages();
            List<ImageResDto> imageResDtos = new ArrayList<>();
            for (Image image : images) {
                ImageResDto imageResDto = new ImageResDto();
                imageResDto.setUrl(image.getUrlImage());
                imageResDtos.add(imageResDto);
            }
            List<Video> videos = post.getVideos();
            List<VideoResDto> videoResDtos = new ArrayList<>();
            for (Video video : videos) {
                VideoResDto videoResDto = new VideoResDto();
                videoResDto.setUrl(video.getUrl());
                videoResDto.setThumb(video.getThumb());
                videoResDtos.add(videoResDto);
            }
            Author author = new Author();
            author.setId(String.valueOf(user.getId()));
            author.setName(user.getUsername());
            author.setAvatar(user.getAvatar());
            author.setCoins(String.valueOf(user.getCoins()));
            author.setListings(user.getListing().toString());


            postResDto.setId(String.valueOf(post.getId()));
            postResDto.setName("");
            postResDto.setDescribed(post.getDescribed());
            postResDto.setCreated(String.valueOf(post.getCreated()));
            postResDto.setFeel("");
            postResDto.setIs_blocked("");
            postResDto.setCan_edit("");
            postResDto.setBanned("");
            postResDto.setStatus(post.getStatus());
            postResDto.setImage((Image) imageResDtos);
            postResDto.setAuthor(author);
            postResDto.setVideo((Video) videoResDtos);

            postResDtos.add(postResDto);
        }
        List<GetListPostsResDto> getListPostsResDtoList = new ArrayList<>();
        getListPostsResDtoList.add((GetListPostsResDto) postResDtos);

        return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE,getListPostsResDtoList);
    }


    // missing banned case, account is locked case, chi? so am

    @Override
    public GeneralResponse editPost(String token, Long Id, String described, String status, MultipartFile image, String image_del, String image_sort, MultipartFile video, String auto_accept) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {



        User user = getUserFromToken(jwtService,userRepo, token);

        if (!jwtService.isTokenValid(token , user)){
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"");
        }
        Post existPost = postRepo.findAllById(Id);
        existPost.setUser(user);

        if (!user.isAccountNonExpired()) {
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED,ResponseMessage.USER_NOT_VALIDATED,"");
        }

        if (user.getCoins() < 4){
            return new GeneralResponse(ResponseCode.NOT_ENOUGH_COINS,ResponseMessage.NOT_ENOUGH_COINS,"");
        }
        else {
            chargeOnPost(user,existPost);
        }


        if (!image_del.isEmpty()) {
            Image imageToDelete = imageRepo.findById(Long.parseLong(image_del)).get();
            existPost.getImages().remove(imageToDelete);
            imageRepo.delete(imageToDelete);
        }

        if (image != null && !image.isEmpty()) {
            String urlImg = s3Service.uploadFile(image).get("url");
            Image image1 = new Image();
            image1.setUrlImage(urlImg);

            existPost.getImages().add(image1);
        }
        else {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"");
        }

        if (!image_sort.isEmpty()) {
            List<Long> imageIds = Arrays.stream(image_sort.split(",")).map(Long::parseLong).collect(Collectors.toList());
            ArrayList<Image> sortedImage = (ArrayList<Image>) existPost
                    .getImages()
                    .stream()
                    .filter(image1 -> imageIds.contains(image1.getId()))
                    .sorted(Comparator.comparingLong(Image::getId))
                    .collect(Collectors.toList());
            existPost.setImages(sortedImage);
        }

        if (existPost.getVideos().isEmpty() || existPost.getImages().isEmpty() ||
                (existPost.getVideos().isEmpty()&&existPost.getImages().isEmpty()) ) {
                if (existPost.getDescribed().equals(described) || existPost.getStatus().equals(status)
                        || (existPost.getDescribed().equals(described))&&existPost.getStatus().equals(status)) {
                    existPost.setDescribed(described);
                    existPost.setStatus(status);

                }
                else {
                    return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"");
                }
        }


        if (!jwtService.isTokenValid(token , user)){
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"");
        }
        existPost.setStatus(status);
        existPost.setDescribed(described);
        List<String> words = Arrays.asList("giet","chem","dam","mau","danh nhau");

        if (existPost.getDescribed().contains(words.toString())){
            existPost.setBanned("1");
            return new GeneralResponse(ResponseCode.ACTION_BEEN_DONE_PRE, ResponseMessage.ACTION_BEEN_DONE_PRE, "");

        }

        if (auto_accept.equals("0")){
            postRepo.save(existPost);

        }
        else if (auto_accept.equals("1")){
            postRepo.save(existPost);

        }
        else {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID);
        }

        postRepo.save(existPost);

        return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, new EditPostResDto(user.getCoins().toString()));


    }




    // Test case ID, test case 3
    @Override
    public GeneralResponse deletePost(String token, Long Id) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {


        User user = getUserFromToken(jwtService,userRepo, token);
        Post existPost = postRepo.findAllById(Id);
        existPost.setUser(user);
        if (!jwtService.isTokenValid(token , user)){
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"");
        }
        if (!user.isActive()){
            return new GeneralResponse(ResponseCode.USER_NOT_VALIDATED,ResponseMessage.USER_NOT_VALIDATED);        }

        if (user.getCoins() < 4){
            return new GeneralResponse(ResponseCode.NOT_ENOUGH_COINS,ResponseMessage.NOT_ENOUGH_COINS,"");
        }
        else {
            chargeOnPost(user,existPost);
        }




        postRepo.delete(existPost);

        return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE,new DeletePostResDto(user.getCoins()));
    }

    @Override
    public GeneralResponse reportPost(String token, Long Id, String subject, String details) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {

        User user = getUserFromToken(jwtService,userRepo, token);
        Post existPost = postRepo.findAllById(Id);
        existPost.setUser(user);
        if (!jwtService.isTokenValid(token , user)){
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"");
        }

      existPost.setSubject(subject);
      existPost.setDetails(details);
      existPost.setReported(true);
      postRepo.save(existPost);



        return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE);
    }


    @Override
    public GeneralResponse feel(String token, Long Id, String type) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {

        User user = getUserFromToken(jwtService,userRepo, token);
        Post existPost = postRepo.findAllById(Id);
        existPost.setUser(user);
        if (!jwtService.isTokenValid(token , user)){
            return new GeneralResponse(ResponseCode.TOKEN_INVALID, ResponseMessage.TOKEN_INVALID,"");
        }

        if (type.equals("1")) {
            existPost.setKudos(existPost.getKudos() + 1);
        } else if (type.equals("0")) {
            existPost.setDissapointed(existPost.getDissapointed() + 1);
        } else {
            return new GeneralResponse (ResponseCode.PARAMETER_TYPE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID);
        }

        postRepo.save(existPost);
        return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE,new FeelResDto(Math.toIntExact(existPost.getKudos()), Math.toIntExact(existPost.getDissapointed())));
    }



    @Override
    public GeneralResponse getMarkComment(GetMarkCommentReqDto getMarkCommentReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {


    // set response data

        // test case wrong id

        //test case ban

        //test case deactive

    return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, "");
    }

    @Override
    public GeneralResponse setMarkComment(SetMarkCommentReqDto setMarkCommentReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
       var user =  JwtUtils.getUserFromToken(jwtService,userRepo, setMarkCommentReqDto.getToken());
        if(user== null ){
            return  new GeneralResponse(ResponseCode.USER_NOT_VALIDATED,ResponseMessage.USER_NOT_VALIDATED);
        }
    if(setMarkCommentReqDto.getToken()==null|| !jwtService.isTokenValid(setMarkCommentReqDto.getToken(),user ))
    {
        return new GeneralResponse(ResponseCode.TOKEN_INVALID,ResponseMessage.TOKEN_INVALID);
    }
    if(!user.isActive())
    {
        return new GeneralResponse(ResponseCode.NOT_ACCESS,ResponseMessage.NOT_ACCESS);

    }
    if(setMarkCommentReqDto.getComment().isEmpty() || setMarkCommentReqDto.getComment().length() > 500)
    {
        return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID);
    }

    if(setMarkCommentReqDto.getId()==null || setMarkCommentReqDto.getIndex() == null|| setMarkCommentReqDto.getIndex() < 0 || setMarkCommentReqDto.getCount() == null || setMarkCommentReqDto.getCount() < 1 )
    {
        return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID);

    }
    var post = postRepo.findById(setMarkCommentReqDto.getId());
    if(post.isEmpty())
    {
        return new GeneralResponse(ResponseCode.POST_NOT_EXIST,ResponseMessage.POST_NOT_EXIST);

    }
    if(post.get().getBanned().equals("1"))
    {
//        do vi phạm tiêu chuẩn cộng đồng hoặc bị hạn chế tại quốc gia
        return new GeneralResponse(ResponseCode.POST_CAN_BE_PUBLISHED,ResponseMessage.POST_CAN_BE_PUBLISHED);

    }
       else if(post.get().getBanned().equals("2"))
        {
            return new GeneralResponse(ResponseCode.LIMITED_ACCESS,ResponseMessage.LIMITED_ACCESS);

        }
       var isBlockByUser = blockListRepo.existsBlockListByUserAndUserIsBlocked(user,post.get().getUser());
       if(!isBlockByUser)
       {
           return new GeneralResponse(ResponseCode.LIMITED_ACCESS,ResponseMessage.LIMITED_ACCESS);

       }
        Pageable pageable = PageRequest.of(setMarkCommentReqDto.getIndex(),setMarkCommentReqDto.getCount(),Sort.by("createdTime").ascending());
       if(setMarkCommentReqDto.getType() == null  || (!setMarkCommentReqDto.getType().equals("0") && !setMarkCommentReqDto.getType().equals("1") )   )
       {

           var comment = Comment.builder()
               .post(post.get())
               .content(setMarkCommentReqDto.getComment())
               .userComment(user)
               .createdTime(new Date(System.currentTimeMillis()))
               .build();

           var coins = user.getCoins();
           user.setCoins(coins-1);
           userRepo.save(user);
           commentRepo.save(comment);
           var listComments = commentRepo.getCommentsByPost(post.get(),pageable);
           return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, listComments);


       }

        var findMark = markRepo.existsByUserMark(user);
        if(!findMark)
        {
            return new GeneralResponse(ResponseCode.EXCEPTION_ERROR, ResponseMessage.EXCEPTION_ERROR);

        }

        var mark = Mark.builder()
                .id(setMarkCommentReqDto.getId())
                .post(post.get())
                .content(setMarkCommentReqDto.getComment())
                .createdTime(new Date(System.currentTimeMillis()))
                .userMark(user)
                .typeOfMark(setMarkCommentReqDto.getType().equals("0") ?"Fake" : "Trust")
                .build();


        var coins = user.getCoins();
        user.setCoins(coins-1);
        userRepo.save(user);
        markRepo.save(mark);
        var listComments = commentRepo.getCommentsByPost(post.get(),pageable);
        return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE, listComments);


    }


    //Test case no covered
    @Override
    public GeneralResponse search(SearchFunctionReqDto searchFunctionReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        log.info("[search] - Start with input: {}", searchFunctionReqDto);

         var user = JwtUtils.getUserFromToken(jwtService,userRepo, searchFunctionReqDto.getToken());
        if(user== null ){
            return  new GeneralResponse(ResponseCode.USER_NOT_VALIDATED,ResponseMessage.USER_NOT_VALIDATED,"The user is not exists");
        }
        if(searchFunctionReqDto.getToken()==null|| !jwtService.isTokenValid(searchFunctionReqDto.getToken(),user ))
        {
            return new GeneralResponse(ResponseCode.TOKEN_INVALID,ResponseMessage.TOKEN_INVALID,"Token is not valid");
        }
        if(!user.isActive())
        {
            return new GeneralResponse(ResponseCode.NOT_ACCESS,ResponseMessage.NOT_ACCESS,"The user is blocked");

        }
        if (!searchFunctionReqDto.getId().equals(user.getId())){
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"");
        }
        if (searchFunctionReqDto.getKeyword().isEmpty()) {
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID,ResponseMessage.PARAMETER_VALUE_NOT_VALID,"");
        }
        if(searchFunctionReqDto.getIndex() ==null|| searchFunctionReqDto.getCount()==null){
            return new GeneralResponse(ResponseCode.PARAMETER_VALUE_NOT_VALID, ResponseMessage.PARAMETER_VALUE_NOT_VALID,"The parameter is not valid");

        }
        Pageable paging = PageRequest.of(searchFunctionReqDto.getIndex(),searchFunctionReqDto.getCount());


        List<Post> posts = postRepo.search(searchFunctionReqDto.getKeyword(),paging);

        List<PostResDto> postResDtos = new ArrayList<>();
        for (Post post : posts) {
            PostResDto postResDto = new PostResDto();


            List<Image> images = post.getImages();
            List<ImageResDto> imageResDtos = new ArrayList<>();
            for (Image image : images) {
                ImageResDto imageResDto = new ImageResDto();
                imageResDto.setUrl(image.getUrlImage());
                imageResDtos.add(imageResDto);
            }
            List<Video> videos = post.getVideos();
            List<VideoResDto> videoResDtos = new ArrayList<>();
            for (Video video : videos) {
                VideoResDto videoResDto = new VideoResDto();
                videoResDto.setUrl(video.getUrl());
                videoResDto.setThumb(video.getThumb());
                videoResDtos.add(videoResDto);
            }
            Author author = new Author();
            author.setId(String.valueOf(user.getId()));
            author.setName(user.getUsername());
            author.setAvatar(user.getAvatar());
            author.setCoins(String.valueOf(user.getCoins()));
            author.setListings(user.getListing().toString());


            postResDto.setId(String.valueOf(post.getId()));
            postResDto.setName("");
            postResDto.setDescribed(post.getDescribed());
            postResDto.setCreated(String.valueOf(post.getCreated()));
            postResDto.setFeel("");
            postResDto.setIs_blocked("");
            postResDto.setCan_edit("");
            postResDto.setBanned("");
            postResDto.setStatus(post.getStatus());
            postResDto.setImage((Image) imageResDtos);
            postResDto.setAuthor(author);
            postResDto.setVideo((Video) videoResDtos);

            postResDtos.add(postResDto);
        }
        List<SearchFunctionResDto> searchFunctionResDtoList = new ArrayList<>();
        searchFunctionResDtoList.add((SearchFunctionResDto) postResDtos);

        Search search = new Search();
        search.setKeyword(searchFunctionReqDto.getKeyword());
        Date currentTime = Date.from(Instant.now());
        search.setCreated(currentTime);

        searchRepo.save(search);

    return new GeneralResponse(ResponseCode.OK_CODE, ResponseMessage.OK_CODE,searchFunctionResDtoList);
    }

    @Override
    public GeneralResponse getSavedSearch(GetSavedSearchReqDto getSavedSearchReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {
        var user =  JwtUtils.getUserFromToken(jwtService,userRepo, getSavedSearchReqDto.getToken());
        if(user== null ){
            return  new GeneralResponse(ResponseCode.USER_NOT_VALIDATED,ResponseMessage.USER_NOT_VALIDATED,"");
        }
        Pageable paging = PageRequest.of(getSavedSearchReqDto.getIndex(),getSavedSearchReqDto.getCount());
        Search search = new Search();
        search.setUser(user);
        GetSavedSearchResDto getSavedSearchResDto = new GetSavedSearchResDto();
        getSavedSearchResDto.setId(String.valueOf(search.getId()));
        getSavedSearchResDto.setKeyword(search.getKeyword());
        getSavedSearchResDto.setCreated(String.valueOf(search.getCreated()));

    return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE, getSavedSearchResDto);
    }

    @Override
    public GeneralResponse delSavedSearch(DelSavedSearchReqDto delSavedSearchReqDto) throws ResponseException, ExecutionException, InterruptedException, TimeoutException {

        var user =  JwtUtils.getUserFromToken(jwtService,userRepo, delSavedSearchReqDto.getToken());
        if(user== null ){
            return  new GeneralResponse(ResponseCode.USER_NOT_VALIDATED,ResponseMessage.USER_NOT_VALIDATED,"");
        }

        Search search = new Search();
        search.setUser(user);

        if (delSavedSearchReqDto.getAll() == "1") {
            searchRepo.deleteAll();
        }
        else if (delSavedSearchReqDto.getAll() =="0") {

            delSavedSearchReqDto.setSearch_id(String.valueOf(search.getId()));

            searchRepo.deleteSearchById(Long.valueOf(delSavedSearchReqDto.getSearch_id()));
        }
        else {
            return new GeneralResponse(ResponseCode.PARAMETER_TYPE_NOT_VALID,ResponseMessage.PARAMETER_TYPE_NOT_VALID);
        }


    return new GeneralResponse(ResponseCode.OK_CODE,ResponseMessage.OK_CODE);
    }


}
